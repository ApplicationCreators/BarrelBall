package com.appreators.game.barrelball.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class BarrelBallView extends GLSurfaceView {

	BarrelBallRenderer renderer;
	public BarrelBallView(Context context) {
		super(context);
		renderer = new BarrelBallRenderer();
		setRenderer(renderer);
	}
}
