package com.lonewolf.lagom.hud;

import com.lonewolf.lagom.entities.base.DrawableEntity;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian Ilenburg on 24/03/2018.
 */

public class ScoreBoard extends DrawableEntity {

    private final Score highScore;
    private final Score currentScore;
    private final Input input;

    public ScoreBoard(int shaderProgram, int texture, Input input, Score highScore, Score
            currentScore) {
        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(1.0f), EntityUtils
                .FULL_TEXTURE_COORDINATES).build();

        this.highScore = highScore;
        this.currentScore = currentScore;
        this.input = input;

        this.setActive(false);
    }

    public Score getHighScore() {
        return highScore;
    }

    public Score getCurrentScore() {
        return currentScore;
    }

    public void resetScores(int highScore) {
        this.highScore.setValue(highScore);
        this.currentScore.setValue(0);
    }

    public boolean update(Score finalScore) {
        if (input.isTouchPending()) {
            consumeTouch();
            currentScore.increment(finalScore.getValue());
            finalScore.reset();
            if (currentScore.getValue() > highScore.getValue()) {
                highScore.setValue(currentScore.getValue());
            }
            return true;
        }
        if (finalScore.getValue() > 0) {
            finalScore.decrement();
            currentScore.increment();
            if (currentScore.getValue() > highScore.getValue()) {
                highScore.increment();
            }
            return true;
        }
        return false;
    }

    private void consumeTouch() {
        input.consumeTouchPosition();
    }
}
