package com.appreators.game.barrelball.utils;

import java.util.ArrayList;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SEPlayer {
	public static float EXTRA_LARGE = 1.0f;
	public static float LARGE = 0.80f;
	public static float NORMAL = 0.70f;
	public static float SMALL = 0.60f;
	public static float EXTRA_SMALL = 0.50f;
	
	private SoundPool mSoundPool;	// SoundPool
	private Context context;
	private ArrayList<Integer> se_sounds;	// 読み込んだ効果音オブジェクト
	
	public SEPlayer(Context context) {
		// SoundPoolを生成
		this.mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		this.context = context;
		this.se_sounds = new ArrayList<Integer>();
	}
	
	public int registerSE(int resid){
		se_sounds.add(mSoundPool.load(context, resid, 1));
		return se_sounds.size() - 1;
	}
	
	public void play(int index, float volume){
		Log.d("SEPlayer", "index:"+index);
		while(true){
			if(mSoundPool.play(se_sounds.get(index), volume, volume, 1, 0, 1.0f) != 0)
				break;
			;
		}
	}
	public void release(){
		for(int id : se_sounds){
			mSoundPool.unload(id);
		}
		mSoundPool.release();
	}
}
