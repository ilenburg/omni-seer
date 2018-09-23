package com.lonewolf.lagom.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.lonewolf.lagom.engine.GameEngine;
import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian Ilenburg on 22/01/2017.
 */

public class GameView extends GLSurfaceView {

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
        if (event.getAction() == MotionEvent.ACTION_DOWN && gameEngine.getInputState().isActive()) {
            Log.d("X", Float.toString(event.getX()));
            Log.d("Y", Float.toString(event.getY()));
            resourceManager.getPlayer().getInput().setTouchPosition(event.getX(), event.getY());
        }
        return true;
    }
}
