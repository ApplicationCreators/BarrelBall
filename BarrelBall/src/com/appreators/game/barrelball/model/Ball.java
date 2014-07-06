package com.appreators.game.barrelball.model;

import javax.microedition.khronos.opengles.GL10;

import android.text.InputFilter.LengthFilter;

import com.appreators.game.barrelball.model.supers.Circle;
import com.appreators.game.barrelball.utils.GraphicUtil;

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

	/**
	 * 接触判定を行う
	 * 0 : 無接触
	 * 1 : Barrelと接触
	 * -1 : Railsと接触
	 * @param barrel
	 * @return
	 */
	public int judge(Barrel barrel){
		float[] barrel_position = barrel.getPosition();
		double length = Math.sqrt(Math.pow((position[0] - barrel_position[0]), 2) + Math.pow((position[1] - barrel_position[1]), 2));
		if(length < radius + barrel.getRadius())
			return 1;
		else if(Math.abs(position[1] - barrel_position[1]) < radius)
			return -1;
		else
			return 0;
	}
	
	//標的を描画します
	public void draw(GL10 gl, int texture) {
		gl.glPushMatrix();
		{
			gl.glTranslatef(position[0], position[1], 0.0f);
//			gl.glRotatef(mAngle, 0.0f, 0.0f, 1.0f);
//			gl.glScalef(mSize, mSize, 1.0f);
			GraphicUtil.drawTexture(gl, 0.0f, 0.0f, radius*2.0f, radius*2.0f, texture, 1.0f, 1.0f, 1.0f, 1.0f);
		}
		gl.glPopMatrix();
	}
}
