package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 23/01/2017.
 */

public class Spell {

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private boolean active;

    public Spell(int shaderProgram, int texture) {

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        };

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

        float radius = 0.08f;

        this.rigidBody = new RigidBody(0.1f, radius / 2, new Vector2(-1.0f, -0.535f), new Vector2(0.0f, 0.0f));

        Animation animation = new Animation(animationCoordinates, 1.0f);

        ColorTransition colorTransition = new ColorTransition(5.0f);

        this.sprite = new Sprite(shaderProgram, texture, EntityUtils.GenerateSymetricGeometry(radius), textureCoordinates, animation, colorTransition);
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
