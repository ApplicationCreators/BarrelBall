package com.appreators.game.barrelball.model.supers;

public abstract class Circle implements BarrelBallObject{

	float[] position;
	float radius;
	float[] speed;
	
	public Circle(float x,float y,float radius) {
		this.position[0] = x;
		this.position[1] = y;
		this.radius = radius;
		this.speed[0]  = 0;
		this.speed[1] = 0;
	}

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float[] getSpeed() {
		return speed;
	}

	public void setSpeed(float[] speed) {
		this.speed = speed;
	}
	
	

}
