package com.lonewolf.lagom.states;

/**
 * Created by Ian on 25/02/2018.
 */

public class EntityStateReference {

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
