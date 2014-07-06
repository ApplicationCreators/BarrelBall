package com.appreators.game.barrelball.model;

import java.util.ArrayList;

import com.appreators.game.barrelball.controller.GameController;

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

	public void shot(){
		for(Barrel barrel : barrels){
			barrel.goDown(barrel.getPosition()[1] - GameController.LENGTH_BETWEEN_RAILS);
		}
		float[] ball_position = new float[2];
		ball_position[0] = barrels.get(1).getPosition()[0];
		ball_position[1] = barrels.get(1).getPosition()[1];
		ball.setPosition(ball_position);
		ball.setShot(true);
	}
	
	/**
	 * 接触判定を行う
	 * 0 : 無接触
	 * 1 : Barrelと接触
	 * -1 : Railsと接触
	 * @return
	 */
	public int judge(){
		if(ball.isShot()){
			// ここで接触判定を行う
			int judge = ball.judge(barrels.get(1));
			if(judge == 1){
				ball.setShot(false);
			}
			else if (judge == -1){
				return -1;
			}
			return 1;
		}
		else{
			return 1;
		}
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
