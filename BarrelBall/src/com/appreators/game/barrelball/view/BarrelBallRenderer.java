package com.appreators.game.barrelball.view;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.appreators.game.barrelball.Global;
import com.appreators.game.barrelball.controller.GameController;
import com.appreators.game.barrelball.model.Ball;
import com.appreators.game.barrelball.model.Barrel;
import com.appreators.game.barrelball.model.Rail;
import com.appreators.game.barrelball.model.Screen;
import com.appreators.game.barrelball.utils.GraphicUtil;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class BarrelBallRenderer implements GLSurfaceView.Renderer{
	private Context context;
	
	private int width;
	private int height;
	
	private GameController controller;
	
	private long start_time;
	private boolean game_over_flag;
	
	public BarrelBallRenderer(Context context) {
		this.context = context;
//		this.mParticleSystem = new ParticleSystem(300, 30);//生成します
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
		// Ballの処理(BallがBarrelの裏にあることもあるので、Ballを先に描画する)
		drawBall(gl,screen.getBall());
		// Barrelの処理
		ArrayList<Barrel> barrels = screen.getBarrels();
		for(Barrel barrel : barrels)
			drawBarrel(gl,barrel);
		// Railの処理
		ArrayList<Rail> rails = screen.getScreen();
		for(Rail rail : rails)
			drawRail(gl,rail);
	}
	public void drawBall(GL10 gl, Ball ball){
		GraphicUtil.drawCircle(gl, ball.getPosition()[0], ball.getPosition()[1], 60, ball.getRadius(), 1.0f, 1.0f, 0.0f, 1.0f);
	}
	public void drawBarrel(GL10 gl, Barrel barrel){
		GraphicUtil.drawCircle(gl, barrel.getPosition()[0], barrel.getPosition()[1], 60, barrel.getRadius(), 0.0f, 1.0f, 1.0f, 1.0f);
	}
	public void drawRail(GL10 gl, Rail rail){
		GraphicUtil.drawRectangle(gl, rail.getPosition()[0], rail.getPosition()[1], rail.getWidth(), rail.getHeight(), 1.0f, 0.0f, 1.0f, 1.0f);
	}
	
	//テクスチャを読み込むメソッド
	private void loadTextures(GL10 gl) {
//		Resources res = mContext.getResources();
//		this.mBgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.circuit);
//		if (mBgTexture == 0) {
//			Log.e(getClass().toString(), "load texture error! circuit");
//		}
//		this.mTargetTexture = GraphicUtil.loadTexture(gl, res, R.drawable.fly);
//		if (mTargetTexture == 0) {
//			Log.e(getClass().toString(), "load texture error! fly");
//		}
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
	}
}
