package com.lonewolf.lagom.physics;

import android.util.Log;

import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.GameState;

import static android.content.ContentValues.TAG;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private static final float GRAVITY_ACCELERATION = -0.0000098f;
    private static final float ZERO = 0.0f;

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
        Input playerInput = resourceManager.getPlayer().getInput();

        float playerJumpPower = playerInput.getJumpPower();

        if (playerJumpPower != ZERO) {
            playerRigidBody.setVelocityY(playerJumpPower);
            playerInput.setJumpPower(ZERO);
        }

        cameraPositon += playerRigidBody.getVelocity().getX() * 10;

        playerRigidBody.setVelocity(Calc.EulerMethod(playerRigidBody.getVelocity(), playerRigidBody.getAcceleration(), deltaTime));

        Vector2 newPosition = Calc.EulerMethod(playerRigidBody.getPosition(), playerRigidBody.getVelocity(), deltaTime);

        playerRigidBody.setPosition(new Vector2(playerRigidBody.getPosition().getX(), newPosition.getY()));

        if (playerRigidBody.getPosition().getY() > groundPosition) {
            playerRigidBody.setAccelerationY(GRAVITY_ACCELERATION);
        } else {
            playerRigidBody.setAccelerationY(ZERO);
            playerRigidBody.setVelocityY(ZERO);
            playerRigidBody.setPositionY(groundPosition);
            playerInput.setGrounded(true);
        }

    }

}
