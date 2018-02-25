package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.builders.SpriteBuilder;
import com.lonewolf.lagom.modules.Position;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.states.EntityState;
import com.lonewolf.lagom.states.EntityStateReference;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian on 25/02/2018.
 */

public class Impact {

    private final Sprite sprite;
    private final EntityStateReference entityStateReference;

    private Position position;

    public Impact(int shaderProgram, int texture) {

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

        this.entityStateReference = new EntityStateReference(EntityState.DISABLED);

        Animation animation = new Animation.Builder(animationCoordinates, 0.5f)
                .withEntityStateReference(entityStateReference).build();

        this.position = new Position();

        float radius = 0.1f;

        this.sprite = new SpriteBuilder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withAnimation(animation).build();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public EntityState getEntityState() {
        return entityStateReference.getEntityState();
    }

    public EntityStateReference getEntityStateReference() {
        return entityStateReference;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 positionCoordinates) {
        this.position.setCoordinates(positionCoordinates);
    }

    public void trigger() {
        this.getSprite().getAnimation().trigger();
    }
}
