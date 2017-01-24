package com.lonewolf.lagom;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Ian on 22/01/2017.
 */

public class GameView extends GLSurfaceView {

    private GameRenderer gameRenderer;

    public GameView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        this.gameRenderer = new GameRenderer(context);
        setRenderer(gameRenderer);
    }

}
