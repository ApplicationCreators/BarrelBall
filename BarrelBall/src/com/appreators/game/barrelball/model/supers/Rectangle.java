package com.appreators.game.barrelball.model.supers;

public abstract class Rectangle implements BarrelBallObject{
	float[] position;
	float width;
	float height;
	float[] speed;
	
	public Rectangle(float x,float y,float width,float height) {
		// TODO Auto-generated constructor stub
		this.position[0] = x;
		this.position[1] = y;
		this.width = width;
		this.height = height;
		this.speed[0] = 0;
		this.speed[1] = 0;
	}

}
