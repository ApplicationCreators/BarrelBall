package com.appreators.game.barrelball;

import com.appreators.game.barrelball.view.BarrelBallRenderer;
import com.appreators.game.barrelball.view.BarrelBallView;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class BarrelBallActivity extends Activity {
	
	BarrelBallView glSurfaceView;
    BarrelBallRenderer renderer;
    
	private Button mRetryButton;//リトライボタン

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
		// フルスクリーン、タイトルバーの非表示
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		Global.mainActivity = this;//インスタンスを保持させる
		
		// デバックモードであるか判定する
		try {
			PackageManager pm = getPackageManager();
			ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
			Global.isDebuggable = (ApplicationInfo.FLAG_DEBUGGABLE == (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		//インスタンスを保持させる
		Global.mainActivity = this;
		
		this.renderer = new BarrelBallRenderer(this);
		glSurfaceView = new BarrelBallView(this);// MyGLSurfaceViewの生成
		glSurfaceView.setRenderer(renderer);
		
		setContentView(glSurfaceView);
		
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
		this.mRetryButton.setOnClickListener(
				new Button.OnClickListener(){
					
					@Override
					public void onClick(View v){
						hideRetryButton();
						renderer.startNewGame();
					}
				}
		);
    }
    
    //リトライボタンを表示する
    public void showRetryButton(){
    	mRetryButton.setVisibility(View.VISIBLE);    	
    }
    
    //リトライボタンを非表示にする
    public void hideRetryButton(){
    	mRetryButton.setVisibility(View.INVISIBLE);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
    
    public void gameOver(){
    	onPause();
    }
    
    
}
