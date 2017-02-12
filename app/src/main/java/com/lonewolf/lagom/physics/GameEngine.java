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
    private float deltaTime;
    private float totalTime;

    private float cameraPositon;

    private final float cameraMovement;

    private final ResourceManager resourceManager;

    public GameState getGameState() {
        return gameState;
    }

    public float getCameraPositon() {
        return cameraPositon;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public GameEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        this.gameState = GameState.RUNNING;
        this.lastTime = System.currentTimeMillis();
        this.deltaTime = 0L;

        this.totalTime = 0.0f;
        this.cameraPositon = 0.0f;
        this.cameraMovement = 0.002f;
    }

    @Override
    public void run() {
        while (gameState == GameState.RUNNING) {

            deltaTime = System.currentTimeMillis() - lastTime;
            totalTime +=  deltaTime / 1000;
            lastTime = System.currentTimeMillis();

            cameraPositon += cameraMovement;

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
