package com.appreators.game.barrelball.model;

import com.appreators.game.barrelball.model.supers.Circle;

public class Ball extends Circle{
	private boolean isShot;
	
	public Ball(float x, float y, float radius) {
		super(x, y, radius);
		isShot = false;
	}

	@Override
	public void move() {
		position[0] += speed[0];
		position[1] += speed[1];
	}
	
	public void setShot(boolean shot){
		this.isShot = shot;
	}
	public boolean isShot() {
		return isShot;
	}
}
