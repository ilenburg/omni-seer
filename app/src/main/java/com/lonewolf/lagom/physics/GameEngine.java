package com.lonewolf.lagom.physics;

import android.util.Log;

import com.lonewolf.lagom.resources.ResourceManager;

import static android.content.ContentValues.TAG;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private GameState gameState;

    private long lastTime;
    private long deltaTime;

    private float cameraMovement;

    private final ResourceManager resourceManager;

    public GameState getGameState() {
        return gameState;
    }

    public float getCameraMovement() {
        return cameraMovement;
    }

    public GameEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        this.gameState = GameState.RUNNING;
        this.lastTime = 0L;
        this.deltaTime = 0L;

        this.cameraMovement = 0.001f;
    }

    @Override
    public void run() {
        while (gameState == GameState.RUNNING) {

            deltaTime = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
