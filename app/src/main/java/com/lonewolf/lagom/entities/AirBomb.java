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

        float[] geometry = new float[]{
                -0.1f, 0.1f,
                -0.1f, -0.1f,
                0.1f, -0.1f,
                0.1f, 0.1f
        };

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        this.rigidBody = new RigidBody(1, new Vector2(2.0f + (random.nextFloat() * 2), -0.51f), new Vector2(-0.5f, 0.0f));

        ColorTransition colorTransition = new ColorTransition(10.0f);

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates, colorTransition);

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
