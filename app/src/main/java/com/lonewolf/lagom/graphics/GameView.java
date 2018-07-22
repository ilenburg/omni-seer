package com.lonewolf.lagom.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;

import com.lonewolf.lagom.engine.GameEngine;
import com.lonewolf.lagom.engine.GestureListener;
import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian Ilenburg on 22/01/2017.
 */

public class GameView extends GLSurfaceView {

    private GestureDetectorCompat mDetector;

    private final GameEngine gameEngine;
    private final GameRenderer gameRenderer;
    private final ResourceManager resourceManager;

    public GameView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        setPreserveEGLContextOnPause(true);
        this.resourceManager = new ResourceManager(context);

        this.gameEngine = new GameEngine(resourceManager);
        this.gameRenderer = new GameRenderer(resourceManager, gameEngine);

        setRenderer(gameRenderer);

        this.mDetector = new GestureDetectorCompat(context, new GestureListener(resourceManager,
                gameEngine.getInputState()));
    }

    @Override
    public void onPause() {
        super.onPause();
        gameRenderer.pause();
        resourceManager.stopMusic();
    }

    @Override
    public void onResume() {
        super.onResume();
        gameRenderer.resume();
        resourceManager.playMusic();
    }

    public void onDestroy() {
        resourceManager.clearMusic();
        destroyDrawingCache();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
