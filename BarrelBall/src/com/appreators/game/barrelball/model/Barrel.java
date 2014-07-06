package com.appreators.game.barrelball.model;

import com.appreators.game.barrelball.Global;
import com.appreators.game.barrelball.model.supers.Circle;

public class Barrel extends Circle{
	// 定数
	public static float[] SPEED_CONSTANT = {1.5f,1.0f,0.75f};
	// 樽が乗っているレールの情報
	float[] rail_position;
	float rail_width;
	float rail_height;
	float acceleration;

	// このyよりも下には行かない
	float next_position_y;

	public Barrel(float x, float y, float radius, float rail_x, float rail_width, float rail_height) {
		super(x, y, radius);
		this.rail_position = new float[2];
		this.rail_position[0] = rail_x;
		this.rail_position[1] = y;
		this.rail_width = rail_width;
		this.rail_height = rail_height;
		this.next_position_y = y;
		this.acceleration = SPEED_CONSTANT[Global.rand.nextInt(2)];
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

	public void goDown(float next_y){
		speed[1] = -0.05f;
		next_position_y = next_y;
	}
	
	public boolean isStopY(){
		return speed[1] == 0;
	}

	@Override
	public void move() {
		position[0] += speed[0] * acceleration;
		// Y方向に動くときはrailも一緒に動く
		if(speed[1] != 0){
			position[1] += speed[1];
			rail_position[1] += speed[1];
		}
		// もしBarrelがRailを超えている場合は、x方向を逆方向に変更
		if( (speed[0] > 0 && position[0] + radius > rail_position[0] + rail_width/2)
				|| (speed[0] < 0 && position[0] - radius < rail_position[0] - rail_width/2))
			speed[0] *= -1;
		// もしBarrelがnext_position_yよりも下に行った場合は、position[1]はnext_position_yにし、speed[1]を0にする
		if(position[1] < next_position_y){
			position[1] = next_position_y;
			rail_position[1] = next_position_y;
			speed[1] = 0;
		}
	}
}
