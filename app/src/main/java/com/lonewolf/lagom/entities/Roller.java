package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.physics.Vector2;

import java.util.Random;

/**
 * Created by Ian on 23/01/2017.
 */

public class Roller {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private final Stats stats;

    private boolean active;

    public Roller(int shaderProgram, int texture) {

        float radius = 0.15f;

        this.rigidBody = new RigidBody(1, radius / 1.5f, new Vector2(2.0f + (random.nextFloat() * 2), -0.49f), new Vector2(-1.5f, 0.0f));

        this.stats = new Stats(20);

        this.sprite = new Sprite(shaderProgram, texture, EntityUtils.GenerateSymmetricGeometryCoordinates(radius), EntityUtils.FULL_TEXTURE_COORDINATES, stats);

        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Stats getStats() {
        return stats;
    }
}
