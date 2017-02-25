package com.lonewolf.lagom.modules.effects;

import java.util.Random;

/**
 * Created by Ian on 11/02/2017.
 */

public class ColorTransition {
    private static final float MAX_RADIANS = 6.28319f;

    private float time;
    private float base;
    private float elapsedTime;
    private float baseTime;

    private int timePosition;

    private boolean initialized;

    private final float ratio;
    private final boolean continuous;

    public ColorTransition(float ratio) {
        this(ratio, true);
    }

    public ColorTransition(float ratio, boolean continuous) {
        if(continuous) {
            this.base = new Random().nextFloat() * 10000.0f;
        }
        else {
            this.base = 0.0f;
        }
        this.time = 0.0f;
        this.elapsedTime = 0.0f;
        this.ratio = ratio;
        this.continuous = continuous;
        this.initialized = false;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = (time + base) * ratio;
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
