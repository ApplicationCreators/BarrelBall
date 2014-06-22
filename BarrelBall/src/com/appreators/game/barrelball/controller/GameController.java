package com.appreators.game.barrelball.controller;

import com.appreators.game.barrelball.model.Ball;
import com.appreators.game.barrelball.model.Barrel;
import com.appreators.game.barrelball.model.Rail;
import com.appreators.game.barrelball.model.Screen;

public class GameController {
	private Screen screen;
	
	public GameController() {
		screen = new Screen();
		Ball ball = new Ball(0, 0, 0.1f);
		Barrel barrel = new Barrel(0.2f, 0.2f, 0.2f);
		
		Rail rail = new Rail(0.0f, -0.8f, 1.0f, 0.1f);
		
		screen.setBall(ball);
		screen.addBarrel(barrel);
		screen.addRail(rail);
	}
	
	public Screen getScreen() {
		return screen;
	}
}
