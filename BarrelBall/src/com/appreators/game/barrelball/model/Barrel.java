package com.appreators.game.barrelball.model;

import com.appreators.game.barrelball.model.supers.Circle;

public class Barrel extends Circle{
	// 樽が乗っているレールの情報
	float[] rail_position;
	float rail_width;
	float rail_height;
	
	public Barrel(float x, float y, float radius, float rail_width, float rail_height) {
		super(x, y, radius);
		this.rail_position[0] = x;
		this.rail_position[1] = y;
		this.rail_width = rail_width;
		this.rail_height = rail_height;
	}
	
	public float[] getRail_position() {
		return rail_position;
	}

	public void setRail_position(float[] rail_position) {
		this.rail_position = rail_position;
	}
	
	public float getRail_width() {
		return rail_width;
	}

	public void setRail_width(float rail_width) {
		this.rail_width = rail_width;
	}

	public float getRail_height() {
		return rail_height;
	}

	public void setRail_height(float rail_height) {
		this.rail_height = rail_height;
	}

	@Override
	public void move() {
		
	}
}
