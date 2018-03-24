package com.lonewolf.lagom.engine.Handlers;

import com.lonewolf.lagom.hud.Score;
import com.lonewolf.lagom.hud.ScoreBoard;
import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian on 25/02/2018.
 */

public class ScoreHandler {

    private int traveledDistance;
    private final ResourceManager resourceManager;

    public ScoreHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.traveledDistance = 0;
    }

    public void update(float deltaTime) {
        if (resourceManager.getPlayer().isAlive()) {
            traveledDistance += (int) (deltaTime * 1000);
            Score score = resourceManager.getScore();
            score.setValue(traveledDistance / 300);
        }

        ScoreBoard scoreBoard = resourceManager.getScoreBoard();
        if (scoreBoard.isActive()) {
            scoreBoard.checkAction();
        }
    }
}
