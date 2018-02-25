package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.physics.Handlers.EnemyHandler;
import com.lonewolf.lagom.physics.Handlers.PlayerHandler;
import com.lonewolf.lagom.physics.Handlers.ScoreHandler;
import com.lonewolf.lagom.physics.Handlers.SpellHandler;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.GameState;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private final PlayerHandler playerHandler;
    private final EnemyHandler enemyHandler;
    private final SpellHandler spellHandler;
    private final ScoreHandler scoreHandler;

    private GameState gameState;

    private long lastTime;
    private float deltaTime;
    private float animationDeltaTime;

    private ResourceManager resourceManager;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public float getCameraPosition() {
        return playerHandler.getCameraPosition();
    }

    public float getAnimationDeltaTime() {
        return animationDeltaTime;
    }

    public void setAnimationDeltaTime(float animationDeltaTime) {
        this.animationDeltaTime = animationDeltaTime;
    }

    public GameEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        this.playerHandler = new PlayerHandler(resourceManager);
        this.enemyHandler = new EnemyHandler(resourceManager);
        this.spellHandler = new SpellHandler(resourceManager);
        this.scoreHandler = new ScoreHandler(resourceManager);

        this.gameState = GameState.RUNNING;
        this.deltaTime = 0.0f;
        this.animationDeltaTime = 0.0f;
    }

    @Override
    public void run() {

        this.lastTime = System.currentTimeMillis();

        while (!Thread.currentThread().isInterrupted()) {
            if (gameState == GameState.RUNNING) {
                deltaTime = (System.currentTimeMillis() - lastTime) / 1000.0f;
                animationDeltaTime += deltaTime;
                lastTime = System.currentTimeMillis();

                playerHandler.update(deltaTime);

                spellHandler.update(deltaTime);

                enemyHandler.updateShadowLord(deltaTime);

                enemyHandler.updateMinions(deltaTime);

                enemyHandler.updateAirBombs(deltaTime);

                enemyHandler.updateRollers(deltaTime);

                scoreHandler.update(deltaTime);

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
