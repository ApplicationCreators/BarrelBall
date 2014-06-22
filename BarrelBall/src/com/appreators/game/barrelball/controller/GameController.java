package com.appreators.game.barrelball.controller;

import com.appreators.game.barrelball.model.Screen;

public class GameController {
	private Screen screen;
	
	public GameController() {
		screen = new Screen();
	}
	
	public Screen getScreen() {
		return screen;
	}
}
