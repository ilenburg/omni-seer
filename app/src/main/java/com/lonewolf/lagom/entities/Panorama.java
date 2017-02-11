package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 10/02/2017.
 */

public class Panorama {

    private final Sprite sprite;

    public Sprite getSprite() {
        return sprite;
    }

    public Panorama(int shaderProgram, int texture, float scrollRatio) {

        float[] geometry = new float[]{
                -3.0f, 1.0f,
                -3.0f, -1.0f,
                3.0f, -1.0f,
                3.0f, 1.0f
        };

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                3.0f, 1.0f,
                3.0f, 0.0f
        };

        this.sprite = new Sprite(shaderProgram, texture,
                geometry, textureCoordinates, new Scroll(scrollRatio));
    }
}
