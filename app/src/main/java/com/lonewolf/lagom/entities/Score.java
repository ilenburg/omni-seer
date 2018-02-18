package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.TextureMapping;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 17/02/2018.
 */

public class Score {

    private final Sprite[] sprites = new Sprite[4];
    private int value;

    public Score(int shaderProgram, int texture) {

        value = 0;

        float radius = 0.08f;
        float displacement = radius * 1.5f;
        float positionX = -1.55f;

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i] = new Sprite(shaderProgram, texture, EntityUtils.GenerateSymmetricGeometryCoordinates(radius), EntityUtils.EIGHT_TEXTURE_COORDINATES[0], new Vector2(positionX, 0.83f), new TextureMapping());
            positionX += displacement;
        }
    }

    public void addValue(int value) {
        setValue(this.value + value);
    }

    public void setValue(int value) {
        this.value = value;
        for (int i = sprites.length - 1; i >= 0; --i) {
            sprites[i].getTextureMapping().setCurrentTextureCoordinates(EntityUtils.EIGHT_TEXTURE_COORDINATES[value % 10]);
            value /= 10;
        }
    }

    public Sprite[] getSprites() {
        return sprites;
    }
}
