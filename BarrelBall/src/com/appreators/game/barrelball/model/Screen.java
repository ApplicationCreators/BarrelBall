package com.appreators.game.barrelball.model;

import java.util.ArrayList;

public class Screen {
	
	ArrayList<Barrel> barrels;
	Ball ball;
	
	public Screen() {
		barrels = new ArrayList<Barrel>();
	}
	

	public void move(){
		// Barrelを動かす
		for(Barrel barrel : barrels)
			barrel.move();
		// Ballを動かす
		ball.move();
	}
	
	
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public ArrayList<Barrel> getBarrels() {
		return barrels;
	}
	
	public void addBarrel(Barrel barrel){
		barrels.add(barrel);
	}
	public void removeBarrel(Barrel barrel){
		barrels.remove(barrel);
	}
}
