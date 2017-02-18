package com.lonewolf.lagom.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;

import com.lonewolf.lagom.physics.GameEngine;
import com.lonewolf.lagom.physics.GestureListener;
import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian on 22/01/2017.
 */

public class GameView extends GLSurfaceView {

    private GestureDetectorCompat mDetector;

    private final GameEngine gameEngine;
    private final GameRenderer gameRenderer;
    private final ResourceManager resourceManager;

    private float touchY;
    private boolean isSwipe;

    public GameView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        this.resourceManager = new ResourceManager(context);

        this.gameEngine = new GameEngine(resourceManager);
        this.gameRenderer = new GameRenderer(resourceManager, gameEngine);
        this.touchY = 0.0f;
        this.isSwipe = false;

        setRenderer(gameRenderer);

        mDetector = new GestureDetectorCompat(context, new GestureListener(resourceManager));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }
}
