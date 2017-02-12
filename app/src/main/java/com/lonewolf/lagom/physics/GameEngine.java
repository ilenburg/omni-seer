package com.lonewolf.lagom.physics;

import android.util.Log;

import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.GameState;

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

    private final float groundPosition;

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
        this.groundPosition = -0.535f;
    }

    @Override
    public void run() {
        while (gameState == GameState.RUNNING) {

            deltaTime = System.currentTimeMillis() - lastTime;
            totalTime += deltaTime / 1000;
            lastTime = System.currentTimeMillis();

            //Log.v("DeltaTime", Float.toString(deltaTime));

            updatePlayer();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void updatePlayer() {
        RigidBody playerRigidBody = resourceManager.getPlayer().getRigidBody();

        if(playerRigidBody.getPosition().getY() > groundPosition) {
            playerRigidBody.setAccelerationY(-0.0000098f);
        }
        else {
            playerRigidBody.setAccelerationY(0.0f);
            playerRigidBody.setVelocityY(0.0f);
            playerRigidBody.setPositionY(groundPosition);
        }

        if(resourceManager.getPlayer().getInput().isJumping()) {
            resourceManager.getPlayer().getInput().setJumping(false);
            playerRigidBody.setVelocityY(0.002f);
        }

        cameraPositon += playerRigidBody.getVelocity().getX() * 10;

        playerRigidBody.setVelocity(Calc.EulerMethod(playerRigidBody.getVelocity(), playerRigidBody.getAcceleration(), deltaTime));

        Vector2 newPosition = Calc.EulerMethod(playerRigidBody.getPosition(), playerRigidBody.getVelocity(), deltaTime);

        playerRigidBody.setPosition(new Vector2(playerRigidBody.getPosition().getX(), newPosition.getY()));
    }

}
