package com.appreators.game.barrelball.utils;

import javax.microedition.khronos.opengles.GL10;

public class Particle {

	public float x;
	public float y;
	public float size;
	public float move_x;
	public float move_y;
	
	public boolean is_active;
	
	public int frame_number;
	public int life_span;
	
	public Particle(){
		this.x = 0.0f;
		this.y = 0.0f;
		this.size = 1.0f;
		this.move_x = 0.0f;
		this.move_y = 0.0f;
		
		this.is_active = false;
		
		this.frame_number = 0;
		this.life_span = 0;
	}
	
	public void draw(GL10 gl ,int texture){
		// 現在のフレームが寿命の間のどの位置にあるのかを計算する
		float lifePercentage = (float) frame_number / life_span;
		float alpha = 1.0f;
		if(lifePercentage <= 0.5f)
			alpha = lifePercentage * 2.0f;
		else
			alpha = 1.0f - (lifePercentage - 0.5f) * 2.0f;
		GraphicUtil.drawTexture(gl, x, y, size, size, texture, 1.0f, 1.0f, 1.0f, alpha);
	}
	
	public void update(){
		frame_number++;
		if(frame_number >= life_span)
			is_active = false;
		x += move_x;
		y += move_y;
	}
	

}
