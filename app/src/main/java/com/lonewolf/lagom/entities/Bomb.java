package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

import java.util.Random;

/**
 * Created by Ian on 23/01/2017.
 */

public class Bomb {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;

    private boolean active;

    public Bomb(int shaderProgram, int texture) {

        float[] geometry = new float[]{
                -0.15f, 0.15f,
                -0.15f, -0.15f,
                0.15f, -0.15f,
                0.15f, 0.15f
        };

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        };

        float[][] animationCoordinates = new float[][]{{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        }, {
                0.5f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
                1.0f, 0.0f
        }, {
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        }, {
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f
        }};
        this.rigidBody = new RigidBody(1, new Vector2(2.0f + (random.nextFloat() * 2), -0.51f), new Vector2(-0.5f, 0.0f));

        Animation animation = new Animation(animationCoordinates, 0.25f, rigidBody);

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates, animation);

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
