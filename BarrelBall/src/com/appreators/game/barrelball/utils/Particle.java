package com.appreators.game.barrelball.utils;

import javax.microedition.khronos.opengles.GL10;

public class Particle {

	public float mX;
	public float mY;
	public float mSize;
	
	public boolean mIsActive;
	
	public Particle(){
		this.mX = 0.0f;
		this.mY = 0.0f;
		this.mSize = 1.0f;
		
		this.mIsActive = false;
	}
	
	public void draw(GL10 gl ,int texture){
		if(mIsActive){
			GraphicUtil.drawTexture(gl, mX, mY, mSize, mSize, texture, 1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
	

}
