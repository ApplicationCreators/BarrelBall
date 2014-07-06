package com.appreators.game.barrelball.view;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.appreators.game.barrelball.BarrelBallActivity;
import com.appreators.game.barrelball.Global;
import com.appreators.game.barrelball.R;
import com.appreators.game.barrelball.controller.GameController;
import com.appreators.game.barrelball.model.Ball;
import com.appreators.game.barrelball.model.Barrel;
import com.appreators.game.barrelball.model.Rail;
import com.appreators.game.barrelball.model.Screen;
import com.appreators.game.barrelball.utils.GraphicUtil;
import com.appreators.game.barrelball.utils.Particle;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;

public class BarrelBallRenderer implements GLSurfaceView.Renderer{
	private BarrelBallActivity context;
	// 画面のサイズ
	private int width;
	private int height;
	// ゲームコントローラー
	private GameController controller;
	
	// テクスチャ
	private int textureBall;
	private int textureBarrel;
	private int textureBackGround;
	
	private long start_time;
	private boolean game_over_flag;
	

	//Particle
	public static final int PARTICLE_COUNT = 100;//パーティ来る100個生成
	private Particle[] mParticles;
	private int mParticleTexture;
	
	private Handler mHandler = new Handler();
	
	public BarrelBallRenderer(BarrelBallActivity context) {
		this.context = context;
//		this.mParticleSystem = new ParticleSystem(300, 30);//生成します
		//パーティクルをランダムな位置に生成する		
		mParticles = new Particle[PARTICLE_COUNT];
		Random rand = Global.rand;
		Particle[] particles = mParticles;
		for(int i = 0; i < PARTICLE_COUNT;i++){
			particles[i] = new Particle();
			particles[i].mX = rand.nextFloat() - 0.5f;
			particles[i].mY = rand.nextFloat() - 0.5f;
			particles[i].mSize = rand.nextFloat() * 0.5f;
		}
		startNewGame();
	}
	
	public void startNewGame() {
		this.start_time = System.currentTimeMillis();//開始時間を保持します
		this.game_over_flag = false;//ゲームオーバー状態ではない
		this.controller = new GameController();
	}
	
	//描画を行う部分を記述するメソッドを追加する
	public void renderMain(GL10 gl) {
		Screen screen = controller.getScreen();
		synchronized (controller) {
			//////////////////// 次の位置へ移動
			screen.move();
			
			//////////////////// 描画
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			// Ballの処理(BallがBarrelの裏にあることもあるので、Ballを先に描画する)
			Ball ball = screen.getBall();
			if(ball.isShot())
				ball.draw(gl, textureBall);
			// Barrelの処理
			ArrayList<Barrel> barrels = screen.getBarrels();
			for(Barrel barrel : barrels)
				barrel.draw(gl, textureBarrel);

			// パーティクルを描画する
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			Random rand = Global.rand;
			Particle[] particles = mParticles;
			int texture = mParticleTexture;
			for(int i = 0; i < PARTICLE_COUNT; i++){
				if(!particles[i].mIsActive){
					particles[i].mX = rand.nextFloat() - 0.5f;
					particles[i].mY = rand.nextFloat() - 0.5f;
					particles[i].mSize = rand.nextFloat() - 0.5f;
					break;
				}
				particles[i].draw(gl, texture);	
			}
			
			gl.glDisable(GL10.GL_BLEND);

			// 判定を行う
			controller.judge();
		}
	}
	
	//テクスチャを読み込むメソッド
	private void loadTextures(GL10 gl) {
		Resources res = context.getResources();
		// 
		this.textureBall = GraphicUtil.loadTexture(gl, res, R.drawable.ball);
		if (textureBall == 0) {
			Log.e(getClass().toString(), "load texture error! ball");
		}
		// 
		this.textureBarrel = GraphicUtil.loadTexture(gl, res, R.drawable.barrel);
		if (textureBarrel == 0) {
			Log.e(getClass().toString(), "load texture error! fly");
		}
//		this.mNumberTexture = GraphicUtil.loadTexture(gl, res, R.drawable.number_texture);
//		if (mNumberTexture == 0) {
//			Log.e(getClass().toString(), "load texture error! number_texture");
//		}
//		this.mGameOverTexture = GraphicUtil.loadTexture(gl, res, R.drawable.game_over);
//		if (mGameOverTexture == 0) {
//			Log.e(getClass().toString(), "load texture error! game_over");
//		}
//		this.mParticleTexture = GraphicUtil.loadTexture(gl, res, R.drawable.particle_blue);
//		if (mParticleTexture == 0) {
//			Log.e(getClass().toString(), "load texture error! particle_blue");
//		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = width;
		this.height = height;
		
		Global.gl = gl;//GLコンテキストを保持する
		
		//テクスチャをロードする
		loadTextures(gl);
		this.mParticleTexture = GraphicUtil.loadTexture(gl, context.getResources(), R.drawable.burst);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(-1.0f, 1.0f, -1.5f, 1.5f, 0.5f, -0.5f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderMain(gl);
	}
	
	//画面がタッチされたときに呼ばれるメソッド
	public void touched(float x, float y){
		Log.d("BarrelBallRender", "x : "+x+", y : "+y);
		synchronized (controller) {
			controller.shot();
		}
	}
}
