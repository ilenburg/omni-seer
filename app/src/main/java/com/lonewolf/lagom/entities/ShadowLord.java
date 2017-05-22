package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 21/05/2017.
 */

public class ShadowLord {

    private final Sprite sprite;
    private final RigidBody rigidBody;

    public ShadowLord(int shaderProgram, int texture) {

        float[] geometry = new float[]{
                -1.0f, 1.0f,
                -1.0f, -1.0f,
                1.0f, -1.0f,
                1.0f, 1.0f
        };

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        this.rigidBody = new RigidBody(10, new Vector2(1.57f, 0.2f));

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }
}
