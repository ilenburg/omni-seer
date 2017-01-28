package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    public GameState gameState;

    private final ResourceManager resourceManager;

    public GameEngine( ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public void run() {
        while(gameState == GameState.RUNNING) {

        }
    }
}
