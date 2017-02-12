package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player {

    private final Sprite sprite;
    private final Movement movement;

    public Sprite getSprite() {
        return sprite;
    }

    public Movement getMovement() {
        return movement;
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

        Animation animation = new Animation(animationCoordinates, 1000);

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates, animation);
        this.movement = new Movement();

    }
}
