package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.graphics.Animation;
import com.lonewolf.lagom.graphics.Sprite;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 23/01/2017.
 */

public class Spell {

    private final Sprite sprite;
    private final RigidBody rigidBody;

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

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

        this.rigidBody = new RigidBody(1, new Vector2(-0.8f, -0.535f), new Vector2(0.0002f, 0.0f));
        Animation animation = new Animation(animationCoordinates, 0.2f, rigidBody);
        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates, animation);

    }
}
