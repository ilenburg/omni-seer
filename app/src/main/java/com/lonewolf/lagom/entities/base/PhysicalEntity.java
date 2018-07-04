package com.lonewolf.lagom.entities.base;

import com.lonewolf.lagom.modules.RigidBody;

/**
 * Created by Ian Ilenburg on 06/03/2018.
 */

public abstract class PhysicalEntity extends DrawableEntity {

    protected RigidBody rigidBody;

    protected PhysicalEntity() {

    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }
}
