package com.lonewolf.lagom.states;

/**
 * Created by Ian on 25/02/2018.
 */

public class EntityStateReference {

    private EntityState entityState;

    public EntityStateReference(EntityState entityState) {
        this.entityState = entityState;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }
}
