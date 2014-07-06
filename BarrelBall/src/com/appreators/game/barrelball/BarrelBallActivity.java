package com.appreators.game.barrelball;

import com.appreators.game.barrelball.utils.BGMPlayer;
import com.appreators.game.barrelball.utils.SEPlayer;
import com.appreators.game.barrelball.view.BarrelBallRenderer;
import com.appreators.game.barrelball.view.BarrelBallView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BarrelBallActivity extends Activity {
	public static final String TAG = Global.APP_TAG+"BarrelBallActivity";
	public static final String BEST_RECORD_KEY = "com.appreators.game.barrelball.BarrelBallActivity.best_record";
	public static final int SE_BURST = 0;
	
	BarrelBallView glSurfaceView;
	BarrelBallRenderer renderer;
	
	private Button mRetryButton;		// リトライボタン
	private TextView pointTextView;		// ポイントのビュー
	private TextView best_record_view;	// ベストレコードのビュー
	private BGMPlayer player;
	private SEPlayer se_player;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// フルスクリーン、タイトルバーの非表示
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		Global.mainActivity = this;						//インスタンスを保持させる
		player = new BGMPlayer(this, R.raw.sound1);
		se_player = new SEPlayer(this);
		se_player.registerSE(R.raw.burst);
		
		// ベストレコードの取得
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		int best_record = prefs.getInt(BEST_RECORD_KEY, 0);		

		// デバックモードであるか判定する
		try {
			PackageManager pm = getPackageManager();
			ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
			Global.isDebuggable = (ApplicationInfo.FLAG_DEBUGGABLE == (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		// 画面の設定
		this.renderer = new BarrelBallRenderer(this);
		glSurfaceView = new BarrelBallView(this);// MyGLSurfaceViewの生成
		glSurfaceView.setRenderer(renderer);

		setContentView(R.layout.activity_barrel_ball);
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainlayout);
		mainLayout.addView(glSurfaceView);

		//		setContentView(glSurfaceView);
		
		// ポイント関係の初期化
		pointTextView = (TextView) findViewById(R.id.counter);
		// ベストレコード
		best_record_view = (TextView) findViewById(R.id.best_record);
		best_record_view.setText(String.valueOf(best_record));

		//ボタンのレイアウト
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		params.setMargins(0, 150, 0, 0);
		//ボタンの作成
		this.mRetryButton = new Button(this);
		this.mRetryButton.setText("Retry");
		hideRetryButton();
		addContentView(mRetryButton, params);
		//イベントの追加
		this.mRetryButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				hideRetryButton();
				renderer.startNewGame();
			}
		});
	}

	//リトライボタンを表示する
	public void showRetryButton(){
		Log.d(TAG, "showRetryButton");
		mRetryButton.setVisibility(View.VISIBLE);    	
	}
	//リトライボタンを非表示にする
	public void hideRetryButton(){
		mRetryButton.setVisibility(View.GONE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
		player.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
		player.start();
	}
	
	public void setBestRecord(int point){
		// ベストレコードの取得
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		int best_record = prefs.getInt(BEST_RECORD_KEY, 0);
		if(point > best_record){
			Log.i(TAG, "New Record:"+point);
			prefs.edit().putInt(BEST_RECORD_KEY, point).commit();
			best_record_view.setText(String.valueOf(point));
		}
	}

	public void gameOver(){
		showRetryButton();
		glSurfaceView.gameOver();
//		glSurfaceView.onPause();
	}
	
	public void startGame(){
		glSurfaceView.startGame();
	}

	public void setPoint(int point){
		pointTextView.setText(String.valueOf(point));
	}
	
	public void setBGM(int resid){
		player.stop();
		player = new BGMPlayer(this, resid);
	}
	public void startBGM(){
		player.start();
	}
	public void stopBGM(){
		player.stop();
	}
	public void startSE(int index){
		se_player.play(index);
	}
}
