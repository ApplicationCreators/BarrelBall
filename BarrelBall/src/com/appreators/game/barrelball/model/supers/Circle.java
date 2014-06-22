package com.appreators.game.barrelball.model.supers;

public abstract class Circle implements BarrelBallObject{

	float[] position;
	float radius;
	float[] speed;
	
	public Circle(float x,float y,float radius) {
		// TODO Auto-generated constructor stub	
		this.position[0] = x;
		this.position[1] = y;
		this.radius = radius;
		this.speed[0]  = 0;
		this.speed[1] = 0;
	}

}
