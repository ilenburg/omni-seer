package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

import java.util.Random;

/**
 * Created by Ian on 23/01/2017.
 */

public class Roller {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;

    private boolean active;

    public Roller(int shaderProgram, int texture) {

        this.rigidBody = new RigidBody(1, new Vector2(2.0f + (random.nextFloat() * 2), -0.49f), new Vector2(-1.5f, 0.0f));

        this.sprite = new Sprite(shaderProgram, texture, EntityUtils.getSymetricGeometry(0.15f), EntityUtils.getFullTexture());

        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }
}
