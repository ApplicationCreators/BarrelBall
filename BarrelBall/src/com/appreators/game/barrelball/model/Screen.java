package com.appreators.game.barrelball.model;

import java.util.ArrayList;

public class Screen {
	
	ArrayList<Barrel> barrels;
	Ball ball;
	ArrayList<Rail> rails;
	
	public Screen() {
		barrels = new ArrayList<Barrel>();
		rails = new ArrayList<>();
	}
	
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public void addBarrel(Barrel barrel){
		barrels.add(barrel);
	}
	public void removeBarrel(Barrel barrel){
		barrels.remove(barrel);
	}
	
	public void addRail(Rail rail){
		rails.add(rail);
	}
	
	public void removeRail(Rail rail){
		rails.remove(rail);
	}
}
