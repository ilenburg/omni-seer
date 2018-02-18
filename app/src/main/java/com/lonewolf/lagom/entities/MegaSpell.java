package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.builders.SpriteBuilder;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 23/01/2017.
 */

public class MegaSpell {

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private boolean active;

    public MegaSpell(int shaderProgram, int texture) {

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
                0.5f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
                1.0f, 0.0f
        }};

        float radius = 0.2f;

        this.rigidBody = new RigidBody(0.5f, radius, new Vector2(-1.0f, -0.535f), new Vector2
                (0.0f, 0.0f));

        Animation animation = new Animation(animationCoordinates, 1.0f);

        ColorTransition colorTransition = new ColorTransition(5.0f);

        this.sprite = new SpriteBuilder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withAnimation(animation).withColorTransition
                (colorTransition).build();
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }
}
