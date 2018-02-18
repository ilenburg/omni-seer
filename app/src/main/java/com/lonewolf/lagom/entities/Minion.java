package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.builders.SpriteBuilder;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

import java.util.Random;

/**
 * Created by Ian on 21/05/2017.
 */

public class Minion {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private final Stats stats;
    private boolean active;
    private boolean aggressive;

    public Minion(int shaderProgram, int texture) {

        float radius = 0.06f;

        this.rigidBody = new RigidBody(0.5f, radius / 2, new Vector2((random.nextFloat() * 2) +
                1, random.nextFloat() - 0.5f), new Vector2(-0.2f, 0.0f));

        this.stats = new Stats(6);

        this.sprite = new SpriteBuilder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .FULL_TEXTURE_COORDINATES).withStats(stats).build();

        active = true;
        aggressive = false;
    }

    public Stats getStats() {
        return stats;
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

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }
}
