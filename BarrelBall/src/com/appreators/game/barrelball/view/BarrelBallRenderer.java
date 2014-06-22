package com.appreators.game.barrelball.view;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.appreators.game.barrelball.Global;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class BarrelBallRenderer implements GLSurfaceView.Renderer{
	private Context context;
	
	private int width;
	private int height;
	
	private long start_time;
	private boolean game_over_flag;
	
	public BarrelBallRenderer(Context context) {
		this.context = context;
//		this.mParticleSystem = new ParticleSystem(300, 30);//生成します
		startNewGame();
	}
	
	public void startNewGame() {
		Random rand = Global.rand;
		this.start_time = System.currentTimeMillis();//開始時間を保持します
		this.game_over_flag = false;//ゲームオーバー状態ではない
	}
	
	//描画を行う部分を記述するメソッドを追加する
	public void renderMain(GL10 gl) {
		
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
//		loadTextures(gl);
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
