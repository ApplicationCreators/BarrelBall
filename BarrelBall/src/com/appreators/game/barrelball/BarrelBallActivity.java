package com.appreators.game.barrelball;

import com.appreators.game.barrelball.view.BarrelBallRenderer;
import com.appreators.game.barrelball.view.BarrelBallView;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class BarrelBallActivity extends Activity {
	
	BarrelBallView glSurfaceView;
    BarrelBallRenderer renderer;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// フルスクリーン、タイトルバーの非表示
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
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
}
