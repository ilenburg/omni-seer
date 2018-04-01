package com.lonewolf.lagom.engine.Handlers;

import android.util.Log;

import com.lonewolf.lagom.hud.Score;
import com.lonewolf.lagom.hud.ScoreBoard;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.StateReference;

/**
 * Created by Ian on 25/02/2018.
 */

public class ScoreHandler {

    private int traveledDistance;
    private final AndroidHandler androidHandler;
    private final ResourceManager resourceManager;
    private final StateReference resetState;

    public ScoreHandler(ResourceManager resourceManager, AndroidHandler androidHandler,
                        StateReference resetState) {
        this.resourceManager = resourceManager;
        this.androidHandler = androidHandler;
        this.resetState = resetState;
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
            switch (scoreBoard.checkAction()) {
                case PLAY:
                    resetState.setActive(true);
                    scoreBoard.setActive(false);
                    break;
                case SHARE:
                    androidHandler.facebookShare();
                    break;
            }
        }
    }

    public void reset() {
        Score score = resourceManager.getScore();
        resourceManager.saveHighScore(score.getValue());
        Log.v("HighScore", Integer.toString(resourceManager.getHighScore()));
        resourceManager.getScore().reset();
        traveledDistance = 0;
    }
}
