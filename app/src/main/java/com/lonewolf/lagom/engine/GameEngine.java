package com.lonewolf.lagom.engine;

import com.lonewolf.lagom.engine.Handlers.ShareHandler;
import com.lonewolf.lagom.engine.Handlers.EnemyHandler;
import com.lonewolf.lagom.engine.Handlers.PlayerHandler;
import com.lonewolf.lagom.engine.Handlers.ScoreHandler;
import com.lonewolf.lagom.engine.Handlers.SpellHandler;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.StateReference;

/**
 * Created by Ian Ilenburg on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private final PlayerHandler playerHandler;
    private final EnemyHandler enemyHandler;
    private final SpellHandler spellHandler;
    private final ScoreHandler scoreHandler;
    private final ShareHandler shareHandler;

    private final StateReference inputState;
    private final StateReference gameState;
    private final StateReference resetState;
    private final StateReference displayScoreState;

    private long lastTime;
    private float deltaTime;
    private float animationDeltaTime;

    private boolean initialized;
    private boolean running;

    private ResourceManager resourceManager;

    public StateReference getInputState() {
        return inputState;
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

        this.gameState = new StateReference(true);
        this.inputState = new StateReference(false);
        this.resetState = new StateReference(false);
        this.displayScoreState = new StateReference(false);

        this.playerHandler = new PlayerHandler(resourceManager, gameState, displayScoreState);
        this.enemyHandler = new EnemyHandler(resourceManager);
        this.spellHandler = new SpellHandler(resourceManager);

        this.shareHandler = new ShareHandler(resourceManager);
        this.scoreHandler = new ScoreHandler(resourceManager, gameState, shareHandler, resetState,
                displayScoreState);

        this.deltaTime = 0.0f;
        this.animationDeltaTime = 0.0f;
        this.running = true;
    }

    public void activate() {
        this.running = true;
    }

    public void deactivate() {
        this.running = false;
    }

    @Override
    public void run() {

        this.lastTime = System.currentTimeMillis();
        this.inputState.setActive(true);

        while (!Thread.currentThread().isInterrupted()) {

            deltaTime = (System.currentTimeMillis() - lastTime) / 1000.0f;
            animationDeltaTime += deltaTime;
            lastTime = System.currentTimeMillis();

            if (running) {
                if (!initialized) {
                    initialized = true;
                }

                scoreHandler.update(deltaTime);

                if (gameState.isActive()) {

                    playerHandler.update(deltaTime);

                    spellHandler.update(deltaTime);

                    enemyHandler.update(deltaTime, 1 + resourceManager.getScore().getValue() / 200);
                }

                if (resetState.isActive()) {
                    playerHandler.reset();
                    enemyHandler.reset();
                    spellHandler.reset();
                    resetState.setActive(false);
                    gameState.setActive(true);
                    this.lastTime = System.currentTimeMillis();
                }
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
