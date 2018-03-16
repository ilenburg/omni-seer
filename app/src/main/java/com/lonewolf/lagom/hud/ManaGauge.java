package com.lonewolf.lagom.hud;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian on 17/02/2018.
 */

public class ManaGauge {

    private final Sprite[] sprites = new Sprite[12];
    private final int MAX_VALUE = sprites.length;
    private int value;

    public ManaGauge(int shaderProgram, int texture) {
        float radius = 0.08f;
        float displacement = radius * 1.5f;
        float positionX = -1.05f;

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i] = new Sprite.Builder(shaderProgram, texture, EntityUtils
                    .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                    .FULL_TEXTURE_COORDINATES).withPosition(new Vector2(positionX, 0.83f)).build();
            positionX += displacement;
        }
        this.value = 3;
    }

    public void add() {
        if (value + 1 < MAX_VALUE) {
            ++value;
        }
    }

    public int getValue() {
        return value;
    }

    public boolean consume() {
        if (!isEmpty()) {
            --value;
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return value < 1;
    }

    public Sprite[] getSprites() {
        return sprites;
    }
}
