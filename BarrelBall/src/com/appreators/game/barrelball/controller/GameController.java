package com.appreators.game.barrelball.controller;

import java.util.ArrayList;

import android.os.Handler;

import com.appreators.game.barrelball.Global;
import com.appreators.game.barrelball.R;
import com.appreators.game.barrelball.model.Ball;
import com.appreators.game.barrelball.model.Barrel;
import com.appreators.game.barrelball.model.Screen;

public class GameController {
	/** BGMが変わるまでに超えなければならない数 */
	public static int BGM_CHANGE_LENGTH = 10;
	/** BGM */
	public static int[] BGMs = {R.raw.sound2,R.raw.sound3,R.raw.sound4,R.raw.sound5};
	public static float LENGTH_BETWEEN_RAILS = 1.0f;
	public static float BOTTOM_RAIL_Y = -1.0f;
	
	private Screen screen;
	
	private float barrel_radius = 0.15f;
	
	private Handler handler;
	
	private int point;
	private int current_bgm_zone;
	
	public GameController() {
		screen = new Screen();
		handler = new Handler();
		reset();
		Ball ball = new Ball(0, 0, 0.1f);
		screen.setBall(ball);
		for(int i=0;i<4;i++){
			screen.addBarrel(getNewBarrel(i-2.0f));
		}
	}
	
	// 新しいバレルを得る
	public Barrel getNewBarrel(float position_y){
		// -1 ~ 1のポジションを返す
		float position_x = (Global.rand.nextFloat() < 0.5) ? Global.rand.nextFloat() : - Global.rand.nextFloat();
		Barrel barrel = new Barrel(position_x, position_y, barrel_radius, 0.0f, 1.6f, 0.075f);
		float[] speed = new float[2];
		speed[0] = 0.03f;
		barrel.setSpeed(speed);
		return barrel;
	}
	
	/** ゲーム開始、及びリセット */
	public void reset(){
		point = 0;
		current_bgm_zone = 0;
		handler.post(new Runnable(){
			@Override
			public void run(){
				Global.mainActivity.setPoint(point);
				Global.mainActivity.setBGM(BGMs[0]);
				Global.mainActivity.startBGM();
				Global.mainActivity.startGame();
			}
		});
	}
	
	public void shot(){
		ArrayList<Barrel> barrels = screen.getBarrels();
		// 動いていたらshotできない
		if(barrels.get(0).isStopY()){
			// 新しいタルを追加する
			Barrel top_barrel = barrels.get(barrels.size()-1);
//			Barrel new_barrel = new Barrel((Global.rand.nextFloat()+0.2f)/2.0f, top_barrel.getPosition()[1]+LENGTH_BETWEEN_RAILS , barrel_radius, 0.0f, 1.6f, 0.075f);
			Barrel new_barrel = getNewBarrel(2.0f);
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
		int judge = screen.judge();
		if(judge == -1){	// GameOver
			//Global.mainActivity.showRetryButton()をUIスレッドで実行する
			handler.post(new Runnable(){
				@Override
				public void run(){
					Global.mainActivity.setBestRecord(point);
					Global.mainActivity.gameOver();
				}
			});			
		}
		else if(judge == 1){	// ポイント加算
			point++;
			//Global.mainActivity.showRetryButton()をUIスレッドで実行する
			handler.post(new Runnable(){
				@Override
				public void run(){
					Global.mainActivity.setPoint(point);
					// current_bgm_zoneに次がある状態
					// かつポイントが次の段階に入った時にはBGMを変更する
					if(current_bgm_zone+1 < BGMs.length && point >= (current_bgm_zone+1)*BGM_CHANGE_LENGTH){
						current_bgm_zone++;
						Global.mainActivity.setBGM(BGMs[current_bgm_zone]);
						Global.mainActivity.startBGM();
					}
				}
			});
		}
		return judge;
	}
	
	public Screen getScreen() {
		return screen;
	}
}
