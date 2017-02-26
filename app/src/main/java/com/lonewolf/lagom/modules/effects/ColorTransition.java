package com.lonewolf.lagom.modules.effects;

import com.lonewolf.lagom.modules.Input;

import java.util.Random;

/**
 * Created by Ian on 11/02/2017.
 */

public class ColorTransition {

    private static final float MAX_RADIANS = 3.14159265f;

    private final float MAX_CYCLE;
    private final Input state;

    private float time;
    private float baseTime;

    private int timePosition;

    private final float ratio;

    public ColorTransition(float ratio) {
        this(ratio, null);
    }

    public ColorTransition(float ratio, Input state) {
        if (state != null) {
            this.baseTime = 0.0f;
        } else {
            this.baseTime = new Random().nextFloat() * 10000.0f;
        }
        this.time = 0.0f;
        this.ratio = ratio;
        this.state = state;
        MAX_CYCLE = (MAX_RADIANS + baseTime) / ratio;
    }

    public float getTime() {
        return (time + baseTime) * ratio;
    }

    public void addTime(float deltaTime) {

        if (this.state != null) {
            if (this.state.isInvulnerable()) {
                this.time += deltaTime;
            } else {
                this.time = 0.0f;
            }
        } else {
            this.time += deltaTime;
        }
    }

    public int getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }

}
