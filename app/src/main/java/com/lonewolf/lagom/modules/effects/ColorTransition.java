package com.lonewolf.lagom.modules.effects;

import java.util.Random;

/**
 * Created by Ian Ilenburg on 11/02/2017.
 */

public class ColorTransition {

    protected float time;
    protected float baseTime;

    private int timePosition;

    private final float ratio;

    public ColorTransition(float ratio) {
        this.baseTime = new Random().nextFloat() * 10000.0f;
        this.time = 0.0f;
        this.ratio = ratio;
    }

    public float getTime() {
        return (time + baseTime) * ratio;
    }

    public void addTime(float deltaTime) {
        this.time += deltaTime;
    }

    public int getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }

}
