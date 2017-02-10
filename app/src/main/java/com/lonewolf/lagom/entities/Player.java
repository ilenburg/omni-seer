package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player {

    private final Sprite sprite;

    private final Animation animation;

    public Sprite getSprite() {
        return sprite;
    }

    public Player(int shaderProgram, int texture) {

        float[] vertexPosition = new float[]{
                -0.12f, 0.12f,
                -0.12f, -0.12f,
                0.12f, -0.12f,
                0.12f, 0.12f
        };

        float[] texturePosition = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        };

        this.sprite = new Sprite(shaderProgram, texture, vertexPosition, texturePosition, null);
        this.animation = null;

    }
}
