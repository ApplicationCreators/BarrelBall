package com.appreators.game.barrelball.controller;

import java.util.ArrayList;




import com.appreators.game.barrelball.BarrelBallActivity;
import com.appreators.game.barrelball.Global;
import com.appreators.game.barrelball.model.Ball;
import com.appreators.game.barrelball.model.Barrel;
import com.appreators.game.barrelball.model.Rail;
import com.appreators.game.barrelball.model.Screen;

public class GameController {
	public static float LENGTH_BETWEEN_RAILS = 1.0f;
	public static float BOTTOM_RAIL_Y = -1.0f;
	
	private BarrelBallActivity context;

	private Screen screen;
	
	private float barrel_radius = 0.15f;
	
	public GameController() {
		screen = new Screen();
		Ball ball = new Ball(0, 0, 0.1f);
		Barrel zero_barrel = new Barrel(0, -2.0f, 0, 0, 0, 0);
		Barrel barrel = new Barrel(0.2f, -1.0f, barrel_radius, 0.0f, 1.6f, 0.075f);
		Barrel middle_barrel = new Barrel(0.0f, 0.0f, barrel_radius, 0.0f, 1.6f, 0.075f);
		Barrel top_barrel = new Barrel(0.0f, 1.0f, barrel_radius, 0.0f, 1.6f, 0.075f);
		
		
		float[] speed = new float[2];
		speed[0] = 0.03f;
		speed[1] = 0.0f;
		barrel.setSpeed(speed);
		float[] middle_speed = new float[2];
		middle_speed[0] = 0.03f;
		middle_barrel.setSpeed(middle_speed);
		float[] top_speed = new float[2];
		top_speed[0] = 0.03f;
		top_barrel.setSpeed(top_speed);
		
		screen.setBall(ball);
		screen.addBarrel(zero_barrel);
		screen.addBarrel(barrel);
		screen.addBarrel(middle_barrel);
		screen.addBarrel(top_barrel);
	}
	
	public void shot(){
		ArrayList<Barrel> barrels = screen.getBarrels();
		// 動いていたらshotできない
		if(barrels.get(0).isStopY()){
			// 新しいタルを追加する
			Barrel top_barrel = barrels.get(barrels.size()-1);
			Barrel new_barrel = new Barrel((Global.rand.nextFloat()+0.2f)/2.0f, top_barrel.getPosition()[1]+LENGTH_BETWEEN_RAILS , barrel_radius, 0.0f, 1.6f, 0.075f);
			float[] top_speed = new float[2];
			top_speed[0] = 0.03f;
			new_barrel.setSpeed(top_speed);
			screen.addBarrel(new_barrel);
			screen.shot();
			barrels.remove(0);
		}
	}
	
	/** 死亡判定する */
	public int judge(){
		return screen.judge();
	}
	
	public Screen getScreen() {
		return screen;
	}
}
