package com.lonewolf.lagom.entities.enemies;

import com.lonewolf.lagom.entities.base.PhysicalEntity;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

import java.util.Random;

/**
 * Created by Ian Ilenburg on 21/05/2017.
 */

public class Minion extends PhysicalEntity {

    private static final Random random = new Random();

    private final Stats stats;
    private boolean aggressive;

    public Minion(int shaderProgram, int texture) {

        float radius = 0.06f;

        this.rigidBody = new RigidBody(0.7f, radius / 2, new Vector2((random.nextFloat() * 2) +
                1, random.nextFloat() - 0.5f), new Vector2(-0.2f, 0.0f));

        this.stats = new Stats(4);

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .FULL_TEXTURE_COORDINATES).withStats(stats).build();

        this.aggressive = false;
    }

    public Stats getStats() {
        return stats;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }
}
