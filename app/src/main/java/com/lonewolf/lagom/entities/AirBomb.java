package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

import java.util.Random;

/**
 * Created by Ian on 23/01/2017.
 */

public class AirBomb {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;

    private boolean active;

    public AirBomb(int shaderProgram, int texture) {

        float radius = 0.1f;

        this.rigidBody = new RigidBody(1, radius, new Vector2(2.0f + (random.nextFloat() * 2), -0.51f), new Vector2(-0.5f, 0.0f));

        ColorTransition colorTransition = new ColorTransition(10.0f);

        this.sprite = new Sprite(shaderProgram, texture, EntityUtils.GenerateSymetricGeometry(radius), EntityUtils.GenerateFullTexture(), colorTransition);

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
