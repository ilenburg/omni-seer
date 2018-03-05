package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.builders.SpriteBuilder;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian on 17/02/2018.
 */

public class ManaGauge {

    private final Sprite[] sprites = new Sprite[12];
    private int value;

    public ManaGauge(int shaderProgram, int texture) {
        value = 0;
        float radius = 0.08f;
        float displacement = radius * 1.5f;
        float positionX = -1.05f;

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i] = new SpriteBuilder(shaderProgram, texture, EntityUtils
                    .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                    .FULL_TEXTURE_COORDINATES).withPosition(new Vector2(positionX, 0.83f)).build();
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
}
