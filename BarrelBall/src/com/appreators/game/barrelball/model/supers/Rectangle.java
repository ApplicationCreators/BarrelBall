package com.appreators.game.barrelball.model.supers;

public abstract class Rectangle implements BarrelBallObject{
	protected float[] position;
	protected float width;
	protected float height;
	protected float[] speed;
	
	public Rectangle(float x,float y,float width,float height) {
		this.position = new float[2];
		this.position[0] = x;
		this.position[1] = y;
		this.width = width;
		this.height = height;
		this.speed = new float[2];
		this.speed[0] = 0;
		this.speed[1] = 0;
	}

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float[] getSpeed() {
		return speed;
	}

	public void setSpeed(float[] speed) {
		this.speed = speed;
	}

	
}
