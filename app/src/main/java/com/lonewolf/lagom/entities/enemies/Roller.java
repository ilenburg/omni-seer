package com.lonewolf.lagom.entities.enemies;

import com.lonewolf.lagom.entities.base.PhysicalEntity;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.modules.effects.TextureMapping;
import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

import java.util.Random;

/**
 * Created by Ian on 23/01/2017.
 */

public class Roller extends PhysicalEntity {

    private static final Random random = new Random();

    private final Stats stats;

    public Roller(int shaderProgram, int texture) {

        float radius = 0.15f;

        this.rigidBody = new RigidBody(1, radius / 1.5f, new Vector2(2.0f + (random.nextFloat() *
                2), -0.49f), new Vector2(-1.5f, 0.0f));

        float[][] textureFramesCoordinates = {EntityUtils
                .QUARTER_TEXTURE_COORDINATES, EntityUtils.GenerateTextureCoordinates(0.5f, 0.5f,
                0.0f), EntityUtils.GenerateTextureCoordinates(0.5f, 0.0f, 0.5f)};

        TextureMapping textureMapping = new
                TextureMapping(textureFramesCoordinates);

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withTextureMapping(textureMapping).build();

        this.stats = new Stats(15, textureMapping);
    }

    public Stats getStats() {
        return stats;
    }
}
