package com.lonewolf.lagom.entities.base;

import com.lonewolf.lagom.modules.Sprite;

/**
 * Created by Ian Ilenburg on 06/03/2018.
 */

public abstract class DrawableEntity extends Entity {

    protected Sprite sprite;

    protected DrawableEntity() {

    }

    public Sprite getSprite() {
        return sprite;
    }
}
