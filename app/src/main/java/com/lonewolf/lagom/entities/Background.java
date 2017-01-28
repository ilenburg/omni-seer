package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 28/01/2017.
 */

public class Background extends Entity {

    public Background(int shaderProgram, int texture) {
        super(shaderProgram, texture, new float[]{
                -3.0f, 1.0f, 0.0f,
                -3.0f, -1.0f, 0.0f,
                3.0f, -1.0f, 0.0f,
                3.0f, 1.0f, 0.0f
        }, new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        });
    }

}
