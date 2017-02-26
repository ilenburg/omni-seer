package com.lonewolf.lagom.modules;

import android.util.Log;

/**
 * Created by Ian on 26/02/2017.
 */

public class State {

    private boolean invulnerable;
    private float time;

    public State() {
        this.invulnerable = false;
        this.time = 0.0f;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
        this.time = 0.0f;
    }

    public void update(float deltaTime) {

        if (this.time > 1.0f) {
            Log.v("StateTime", Float.toString(this.time));
            this.invulnerable = false;
            this.time = 0.0f;
        } else {
            this.time += deltaTime;
        }

    }
}
