package com.lonewolf.lagom.modules.effects;

import java.util.Random;

/**
 * Created by Ian on 11/02/2017.
 */

public class ColorTransition {

    private static final float MAX_RADIANS = 3.14159265f;

    private final float MAX_CYCLE;

    private float time;
    private float baseTime;
    private float elapsedTime;
    private float startTime;

    private int timePosition;

    private final float ratio;
    private final boolean continuous;

    public ColorTransition(float ratio) {
        this(ratio, true);
    }

    public ColorTransition(float ratio, boolean continuous) {
        if (continuous) {
            this.baseTime = new Random().nextFloat() * 10000.0f;
        } else {
            this.baseTime = 0.0f;
        }
        this.time = 0.0f;
        this.elapsedTime = 0.0f;
        this.ratio = ratio;
        this.continuous = continuous;
        this.startTime = 0.0f;
        MAX_CYCLE = (MAX_RADIANS + baseTime) * ratio * 100;
    }

    public float getTime() {
        return (time + baseTime) * ratio;
    }

    public void addTime(float deltaTime) {

        if(!continuous) {

        }
        else {
            this.time += deltaTime / 1000;
        }

        /*if (!continuous) {
            if (this.time != 0.0f) {
                this.elapsedTime += time - this.time;
            }
            Log.v("Elapsed", Float.toString(elapsedTime));
            if ((elapsedTime + baseTime) * ratio > MAX_CYCLE) {
                this.time = 0.0f;
            } else {
                this.time = time;
            }
        } else {
            this.time = time;
        }*/
    }

    public int getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }

    public void reset() {
        this.elapsedTime = 0.0f;
    }

}
