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

        float[] geometry = new float[]{
                -0.08f, 0.08f,
                -0.08f, -0.08f,
                0.08f, -0.08f,
                0.08f, 0.08f
        };

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

        this.rigidBody = new RigidBody(0.5f, new Vector2(-1.0f, -0.535f), new Vector2(0.0f, 0.0f));

        Animation animation = new Animation(animationCoordinates, 0.4f, rigidBody);

        ColorTransition colorTransition = new ColorTransition(0.01f);

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates, animation, colorTransition);
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
