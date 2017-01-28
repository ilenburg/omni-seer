package com.lonewolf.lagom.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.lonewolf.lagom.physics.GameEngine;
import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian on 22/01/2017.
 */

public class GameView extends GLSurfaceView {

    private final GameRenderer gameRenderer;
    private final GameEngine gameEngine;
    private final ResourceManager resourceManager;

    public GameView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        this.resourceManager = new ResourceManager(context);

        this.gameRenderer = new GameRenderer(resourceManager);
        setRenderer(gameRenderer);

        this.gameEngine = new GameEngine();

        //gameEngine.run();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
