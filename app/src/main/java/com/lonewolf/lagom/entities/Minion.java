package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

import java.util.Random;

/**
 * Created by Ian on 21/05/2017.
 */

public class Minion {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private boolean active;
    private boolean aggressive;

    public Minion(int shaderProgram, int texture) {

        float[] geometry = new float[]{
                -0.06f, 0.06f,
                -0.06f, -0.06f,
                0.06f, -0.06f,
                0.06f, 0.06f
        };

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        this.rigidBody = new RigidBody(1, new Vector2((random.nextFloat() * 2) + 1, random.nextFloat() - 0.5f), new Vector2(-0.2f, 0.0f));

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates);

        active = true;
        aggressive = false;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive(boolean aggresive) {
        this.aggressive = aggresive;
    }
}
