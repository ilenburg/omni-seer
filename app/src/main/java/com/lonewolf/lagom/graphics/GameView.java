package com.lonewolf.lagom.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.lonewolf.lagom.physics.GameEngine;
import com.lonewolf.lagom.physics.Input;
import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian on 22/01/2017.
 */

public class GameView extends GLSurfaceView {

    private final GameEngine gameEngine;
    private final GameRenderer gameRenderer;
    private final ResourceManager resourceManager;

    private float touchY;

    public GameView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        this.resourceManager = new ResourceManager(context);

        this.gameEngine = new GameEngine(resourceManager);
        this.gameRenderer = new GameRenderer(resourceManager, gameEngine);
        this.touchY = 0.0f;

        setRenderer(gameRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                touchY = event.getY();
                return true;
            case (MotionEvent.ACTION_UP):
                if (touchY > event.getY() && touchY - event.getY() > 200) {
                    Input playerInput = resourceManager.getPlayer().getInput();
                    if (playerInput.isGrounded()) {
                        playerInput.setJumping(true);
                    }
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
