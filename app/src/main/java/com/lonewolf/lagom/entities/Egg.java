package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

import java.util.Random;

/**
 * Created by Ian on 23/01/2017.
 */

public class Egg {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;

    private boolean active;

    public Egg(int shaderProgram, int texture) {

        float radius = 0.15f;

        this.rigidBody = new RigidBody(1, radius, new Vector2(2.0f + (random.nextFloat() * 2), -0.53f), new Vector2(-2.00f, 0.0f));

        this.rigidBody.addAngle(90);

        this.sprite = new Sprite(shaderProgram, texture, EntityUtils.GenerateSymmetricGeometryCoordinates(radius), EntityUtils.FULL_TEXTURE_COORDINATES);

        active = false;
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
