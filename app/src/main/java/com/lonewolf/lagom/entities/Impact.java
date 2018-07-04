package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.entities.base.DrawableEntity;
import com.lonewolf.lagom.modules.Position;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian Ilenburg on 25/02/2018.
 */

public class Impact extends DrawableEntity {

    private final boolean mega;
    private final Position position;

    public Impact(int shaderProgram, int texture, float radius, boolean mega) {

        float[][] animationCoordinates = new float[][]{{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        }, {
                0.5f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
                1.0f, 0.0f
        }, {
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f
        }, {
                0.5f, 0.5f,
                0.5f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.5f
        }};

        Animation animation = new Animation.Builder(animationCoordinates, 0.3f)
                .withEntityStateReference(this.getStateReference()).build();

        this.position = new Position();
        this.mega = mega;

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withAnimation(animation).build();
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean isMega() {
        return mega;
    }

    public void setPosition(Vector2 positionCoordinates) {
        this.position.setCoordinates(positionCoordinates);
    }

    public void trigger() {
        this.getSprite().getAnimation().trigger();
    }
}
