package com.appreators.game.barrelball;

import com.appreators.game.barrelball.view.BarrelBallView;

import android.app.Activity;
import android.os.Bundle;


public class BarrelBallActivity extends Activity {
    BarrelBallView mGLView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGLView = new BarrelBallView(this);
        
        setContentView(mGLView);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

}
