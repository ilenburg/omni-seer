package com.lonewolf.lagom.entities;

import android.opengl.GLES20;

/**
 * Created by Ian on 28/01/2017.
 */

public class Background extends Entity {

    private float scroll;
    private int scrollPosition;

    public Background(int shaderProgram, int texture) {
        super(shaderProgram, texture, new float[]{
                -3.0f, 1.0f,
                -3.0f, -1.0f,
                3.0f, -1.0f,
                3.0f, 1.0f
        }, new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                3.0f, 1.0f,
                3.0f, 0.0f
        });

        this.scrollPosition = GLES20.glGetUniformLocation(shaderProgram, "scroll");
        this.scroll = 0f;
    }

    @Override
    void extraDraw() {
        GLES20.glUniform1f(scrollPosition, scroll);
        scroll += 0.001f;
    }
}
