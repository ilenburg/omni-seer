package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player {

    private final Sprite sprite;
    private final RigidBody rigidBody;

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Player(int shaderProgram, int texture) {

        float[] geometry = new float[]{
                -0.12f, 0.12f,
                -0.12f, -0.12f,
                0.12f, -0.12f,
                0.12f, 0.12f
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

        this.rigidBody = new RigidBody(1, new Vector2(-1.0f, -0.535f), new Vector2(0.0002f, 0.0f));
        Animation animation = new Animation(animationCoordinates, 0.2f, rigidBody);

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates, animation);

    }
}
