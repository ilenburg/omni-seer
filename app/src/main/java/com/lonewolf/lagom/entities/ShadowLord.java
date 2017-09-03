package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 21/05/2017.
 */

public class ShadowLord {

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private boolean active;

    public ShadowLord(int shaderProgram, int texture) {

        float radius = 1.0f;

        this.rigidBody = new RigidBody(10, radius, new Vector2(1.57f, 0.2f), new Vector2(0.0f, 0.1f));

        this.sprite = new Sprite(shaderProgram, texture, EntityUtils.GenerateSymetricGeometry(radius), EntityUtils.GenerateFullTexture());

        active = true;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
