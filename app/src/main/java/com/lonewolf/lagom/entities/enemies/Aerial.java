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
 * Created by Ian Ilenburg on 23/01/2017.
 */

public class Aerial extends PhysicalEntity {

    private static final Random random = new Random();

    private final Stats stats;

    public Aerial(int shaderProgram, int texture) {

        float radius = 0.1f;

        this.rigidBody = new RigidBody(1, radius / 1.5f, new Vector2(2.0f + (random.nextFloat() *
                2), -0.51f), new Vector2(-0.5f, 0.0f));

        float[][] textureFramesCoordinates = {EntityUtils
                .QUARTER_TEXTURE_COORDINATES, EntityUtils.GenerateTextureCoordinates(0.5f, 0.5f,
                0.0f), EntityUtils.GenerateTextureCoordinates(0.5f, 0.0f, 0.5f)};

        TextureMapping textureMapping = new
                TextureMapping(textureFramesCoordinates);

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withTextureMapping(textureMapping).build();

        this.stats = new Stats(6, textureMapping);
    }

    public Stats getStats() {
        return stats;
    }
}
