package com.lonewolf.lagom.modules;

import com.lonewolf.lagom.engine.Vector2;

/**
 * Created by Ian Ilenburg on 12/02/2017.
 */

public class Input {

    private Vector2[] touchPositions;
    private int vectorIndex;
    private boolean grounded;

    private float screenWidth;
    private float screenHeight;
    private float ratio;

    private boolean active;
    private boolean invulnerable;
    private float time;

    public Input() {
        this.grounded = true;
        this.touchPositions = new Vector2[3];
        for (int i = 0; i < touchPositions.length; ++i) {
            touchPositions[i] = new Vector2();
        }
        this.screenHeight = 0.0f;
        this.screenWidth = 0.0f;

        this.invulnerable = false;
        this.vectorIndex = 0;
        this.time = 0.0f;
        this.active = true;
    }

    public Vector2 readTouchPosition() {
        return touchPositions[vectorIndex - 1];
    }

    public Vector2 consumeTouchPosition() {
        if (vectorIndex > 0) {
            --vectorIndex;
        }
        return touchPositions[vectorIndex];
    }

    public boolean isTouchPending() {
        return vectorIndex > 0;
    }

    public void setTouchPosition(float x, float y) {
        if (vectorIndex < touchPositions.length - 1) {
            this.touchPositions[vectorIndex].setX(getNormalizedX(x));
            this.touchPositions[vectorIndex].setY(getNormalizedY(y));
            ++vectorIndex;
        }
    }

    public void setScreenSize(float screenWidth, float screenHeight, float ratio) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.ratio = ratio;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    private float getNormalizedX(float x) {
        return ((x / screenWidth) * 2 - 1) * ratio;
    }

    private float getNormalizedY(float y) {
        return -((y / screenHeight) * 2 - 1);
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
        this.time = 0.0f;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(float deltaTime) {
        if (this.time > 1.0f) {
            this.invulnerable = false;
            this.time = 0.0f;
        } else {
            this.time += deltaTime;
        }
    }
}
