package com.lonewolf.lagom.graphics;

/**
 * Created by Ian on 11/02/2017.
 */

public class ColorTransition {

    private float time;
    private int timePosition;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = (float) Math.abs(Math.sin(time / 100));
    }

    public int getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }

    public ColorTransition() {
        this.time = 0.0f;
    }

}
