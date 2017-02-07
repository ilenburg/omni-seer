package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private GameState gameState;
    private long lastTime;
    private long deltaTime;

    public float cameraMovement;

    private final ResourceManager resourceManager;

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

            if (cameraMovement < 0.01) {
                cameraMovement *= 1.1;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
