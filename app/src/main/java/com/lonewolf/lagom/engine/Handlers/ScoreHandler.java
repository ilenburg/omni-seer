package com.lonewolf.lagom.engine.Handlers;

import com.lonewolf.lagom.hud.GameOverBoard;
import com.lonewolf.lagom.hud.Score;
import com.lonewolf.lagom.hud.ScoreBoard;
import com.lonewolf.lagom.hud.TutorialBoard;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.StateReference;

/**
 * Created by Ian Ilenburg on 25/02/2018.
 */

public class ScoreHandler {

    private int traveledDistance;

    private final ShareHandler shareHandler;
    private final ResourceManager resourceManager;

    private final StateReference resetState;
    private final StateReference displayScoreState;
    private final StateReference gameState;

    public ScoreHandler(ResourceManager resourceManager, StateReference gameState, ShareHandler
            shareHandler,
                        StateReference resetState, StateReference displayScoreState) {
        this.resourceManager = resourceManager;
        this.gameState = gameState;
        this.shareHandler = shareHandler;
        this.resetState = resetState;
        this.displayScoreState = displayScoreState;
        this.traveledDistance = 0;
    }

    public void update(float deltaTime) {
        if (resourceManager.getPlayer().isAlive()) {
            traveledDistance += (int) (deltaTime * 1000);
            Score score = resourceManager.getScore();
            score.setValue(traveledDistance / 300);
        }

        GameOverBoard gameOverBoard = resourceManager.getGameOverBoard();
        if (gameOverBoard.isActive()) {
            switch (gameOverBoard.checkAction()) {
                case PLAY:
                    resetState.setActive(true);
                    gameOverBoard.setActive(false);
                    break;
                case SHARE:
                    shareHandler.defaultShare();
                    break;
            }
        }

        ScoreBoard scoreBoard = resourceManager.getScoreBoard();
        if (scoreBoard.isActive()) {
            if (displayScoreState.isActive()) {
                scoreBoard.resetScores(resourceManager.getHighScore());
                displayScoreState.setActive(false);
            }
            if (scoreBoard.update(resourceManager.getScore())) {
                if (scoreBoard.getCurrentScore().getValue() % 10 == 0) {
                    resourceManager.playCount();
                }
            } else {
                resetAndPersist();
                sleep(1000);
                scoreBoard.setActive(false);
                gameOverBoard.setActive(true);
                sleep(1000);
            }
        }

        TutorialBoard tutorialBoard = resourceManager.getTutorialBoard();
        if (tutorialBoard.isActive()) {
            if (tutorialBoard.checkAction()) {
                tutorialBoard.setActive(false);
            }
        }
    }

    private void resetAndPersist() {
        resourceManager.saveHighScore(resourceManager.getScoreBoard().getCurrentScore().getValue());
        traveledDistance = 0;
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
