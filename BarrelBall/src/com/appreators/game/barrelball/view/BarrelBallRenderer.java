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
import com.appreators.game.barrelball.utils.ParticleSystem;

import android.R.integer;
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
	
	private int[] texture_bgs;
	
	private long start_time;
	private boolean game_over_flag;
	

	//Particle
	private ParticleSystem particleSystem;
	private int textureParticle;
	
	private Handler mHandler = new Handler();
	
	public BarrelBallRenderer(BarrelBallActivity context) {
		this.context = context;
//		this.mParticleSystem = new ParticleSystem(300, 30);//生成します
		startNewGame();
	}
	
	public void startNewGame() {
		this.start_time = System.currentTimeMillis();//開始時間を保持します
		this.game_over_flag = false;//ゲームオーバー状態ではない
		this.controller = new GameController();
		this.particleSystem = new ParticleSystem(100, 30);
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
			// 背景を描画する
			GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, textureBackGround, 1.0f, 1.0f, 1.0f, 1.0f);
			// Ballの処理(BallがBarrelの裏にあることもあるので、Ballを先に描画する)
			Ball ball = screen.getBall();
			if(ball.isShot() && !game_over_flag)
				ball.draw(gl, textureBall);
			// Barrelの処理
			ArrayList<Barrel> barrels = screen.getBarrels();
			for(Barrel barrel : barrels)
				barrel.draw(gl, textureBarrel);
			gl.glDisable(GL10.GL_BLEND);
			
			// 判定を行う
			if(controller.judge() == -1){
				// ゲームオーバーフラグを建てる
				game_over_flag = true;
				// パーティクルを放出する
				Random rand = Global.rand;
				for(int j=0;j<40;j++){
					float move_x = (rand.nextFloat() - 0.5f) * 0.05f;
					float move_y = (rand.nextFloat() - 0.5f) * 0.05f;
					particleSystem.add(ball.getPosition()[0], ball.getPosition()[1], 0.2f, move_x, move_y);
				}
			}
			
			// パーティクルを描画する
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			particleSystem.update();
			particleSystem.draw(gl, textureParticle);
			gl.glDisable(GL10.GL_BLEND);
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
		this.textureParticle = GraphicUtil.loadTexture(gl, res, R.drawable.burst);
		if (textureParticle == 0){
			Log.e(getClass().toString(), "load texture error! burst");
		}
		
		// バックグラウンドの処理
		this.texture_bgs = new int[4];
		this.texture_bgs[0] = GraphicUtil.loadTexture(gl, res, R.drawable.stage1);
		if (this.texture_bgs[0] == 0){
			Log.e(getClass().toString(), "load texture error! texture_bgs[0]");
		}
		this.texture_bgs[1] = GraphicUtil.loadTexture(gl, res, R.drawable.stage2);
		if (this.texture_bgs[1] == 0){
			Log.e(getClass().toString(), "load texture error! texture_bgs[1]");
		}
		this.texture_bgs[2] = GraphicUtil.loadTexture(gl, res, R.drawable.stage3);
		if (this.texture_bgs[2] == 0){
			Log.e(getClass().toString(), "load texture error! texture_bgs[2]");
		}
		this.texture_bgs[3] = GraphicUtil.loadTexture(gl, res, R.drawable.stage4);
		if (this.texture_bgs[3] == 0){
			Log.e(getClass().toString(), "load texture error! texture_bgs[3]");
		}
		this.textureBackGround = this.texture_bgs[0];
		
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
		this.textureParticle = GraphicUtil.loadTexture(gl, context.getResources(), R.drawable.burst);
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
	
	// 背景を変更する
	public void changeBG(int index){
		textureBackGround = texture_bgs[index];
	}
}
