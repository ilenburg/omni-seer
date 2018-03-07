package com.lonewolf.lagom.entities.base;

import com.lonewolf.lagom.states.EntityStateReference;

/**
 * Created by Ian on 06/03/2018.
 */

public abstract class Entity {

    private EntityStateReference entityStateReference = new EntityStateReference();

    protected Entity() {

    }

    public boolean isActive() {
        return entityStateReference.isActive();
    }

    public void setActive(boolean active) {
        this.entityStateReference.setActive(active);
    }

    public EntityStateReference getEntityStateReference() {
        return entityStateReference;
    }
}
