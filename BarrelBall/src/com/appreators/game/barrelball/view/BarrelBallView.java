package com.appreators.game.barrelball.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class BarrelBallView extends GLSurfaceView {
	
	// 画面サイズ
	private float mWidth;
	private float mHeight;
	
	// MyRendererを保持する
	BarrelBallRenderer renderer;
	
	private boolean game_over_flag;
	
	public BarrelBallView(Context context) {
		super(context);
		setFocusable(true); // タッチイベントが取得できるようにする
		game_over_flag = false;
	}
	
	@Override
	public void setRenderer(Renderer renderer){
		super.setRenderer(renderer);
		this.renderer = (BarrelBallRenderer) renderer;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		super.surfaceChanged(holder, format, w, h);
		this.mWidth = w;
		this.mHeight = h;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(!game_over_flag){
			float x = (event.getX() / (float)mWidth) * 2.0f - 1.0f;
			float y = (event.getY() / (float)mHeight) * -3.0f + 1.5f;
			renderer.touched(x, y);
		}
		return false;
	}
	
	public void gameOver(){
		game_over_flag = true;
	}
	public void startGame(){
		game_over_flag = false;
	}
}
