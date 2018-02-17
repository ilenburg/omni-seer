package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 17/02/2018.
 */

public class Score {

    private final Sprite[] sprites = new Sprite[4];

    public Score(int shaderProgram, int texture) {

        float radius = 0.08f;
        float displacement = radius * 1.5f;
        float positionX = -1.55f;

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i] = new Sprite(shaderProgram, texture, EntityUtils.GenerateSymmetricGeometryCoordinates(radius), EntityUtils.GenerateTextureCoordinates(0.25f), new Vector2(positionX, 0.83f));
            positionX += displacement;
        }
    }

    public Sprite[] getSprites() {
        return sprites;
    }
}
