package com.appreators.game.barrelball.model;

import java.util.ArrayList;

public class Screen {
	
	ArrayList<Barrel> barrels;
	Ball ball;
	ArrayList<Rail> rails;
	
	public Screen() {
		barrels = new ArrayList<Barrel>();
		rails = new ArrayList<Rail>();
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

	public ArrayList<Rail> getRails() {
		return rails;
	}
	
	public void addRail(Rail rail){
		rails.add(rail);
	}
	
	public void removeRail(Rail rail){
		rails.remove(rail);
	}

	public void move(){
		
	}
	
}
