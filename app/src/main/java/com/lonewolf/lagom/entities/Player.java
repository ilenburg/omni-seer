package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player extends Entity {

    public Player(int shaderProgram, int texture) {

        super(shaderProgram, texture, new float[]{
                -0.125f, 0.125f,
                -0.125f, -0.125f,
                0.125f, -0.125f,
                0.125f, 0.125f
        }, new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        });
    }

    @Override
    void extraDraw(float cameraMove) {

    }
}
