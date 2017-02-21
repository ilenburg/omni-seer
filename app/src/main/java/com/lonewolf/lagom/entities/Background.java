package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.graphics.Scroll;
import com.lonewolf.lagom.graphics.Sprite;
import com.lonewolf.lagom.graphics.TextureTransition;

/**
 * Created by Ian on 11/02/2017.
 */

public class Background{

    private final Sprite sprite;

    public Sprite getSprite() {
        return sprite;
    }

    public Background(int shaderProgram, int texture1, int texture2, float scrollRatio) {
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

        this.sprite = new Sprite(shaderProgram, texture1,
                geometry, textureCoordinates, new Scroll(scrollRatio), new TextureTransition(texture2));
    }
}
