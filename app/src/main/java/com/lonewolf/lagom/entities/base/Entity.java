package com.lonewolf.lagom.entities.base;

import com.lonewolf.lagom.states.StateReference;

/**
 * Created by Ian on 06/03/2018.
 */

public abstract class Entity {

    private StateReference stateReference = new StateReference();

    protected Entity() {

    }

    public boolean isActive() {
        return stateReference.isActive();
    }

    public void setActive(boolean active) {
        this.stateReference.setActive(active);
    }

    public StateReference getStateReference() {
        return stateReference;
    }
}
