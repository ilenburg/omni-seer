package com.lonewolf.lagom.hud;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.TextureMapping;
import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian on 17/02/2018.
 */

public class Score {

    private final Sprite[] sprites = new Sprite[4];
    private int value;

    public Score(int shaderProgram, int texture, float positionX, float positionY) {
        value = 0;
        float radius = 0.08f;
        float displacement = radius * 1.5f;

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i] = new Sprite.Builder(shaderProgram, texture, EntityUtils
                    .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                    .EIGHT_TEXTURE_COORDINATES[0]).withPosition(new Vector2(positionX, positionY))
                    .withTextureMapping(new TextureMapping()).build();
            positionX += displacement;
        }
    }

    public void setValue(int value) {
        this.value = value;
        for (int i = sprites.length - 1; i >= 0; --i) {
            sprites[i].getTextureMapping().setCurrentTextureCoordinates(EntityUtils
                    .EIGHT_TEXTURE_COORDINATES[value % 10]);
            value /= 10;
        }
    }

    public int getValue() {
        return value;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public void increment(int value) {
        setValue(this.value + value);
    }

    public void decrement(int value) {
        setValue(this.value - value);
    }

    public void increment() {
        increment(1);
    }

    public void decrement() {
        decrement(1);
    }

    public void reset() {
        this.value = 0;
    }
}
