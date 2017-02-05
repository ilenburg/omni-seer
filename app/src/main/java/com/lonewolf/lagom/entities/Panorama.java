package com.lonewolf.lagom.entities;

import android.opengl.GLES20;

/**
 * Created by Ian on 28/01/2017.
 */

public class Panorama extends Entity {

    private float scroll;
    private float scrollRatio;

    private int scrollPosition;

    public Panorama(int shaderProgram, int texture, float scrollRatio) {
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
        this.scrollRatio = scrollRatio;
        this.scroll = 0f;
    }

    @Override
    void extraDraw(float cameraMove) {
        scroll += (cameraMove * scrollRatio);
        if (scroll >= 1.0f) {
            scroll = 0;
        }
        GLES20.glUniform1f(scrollPosition, scroll);
    }
}
