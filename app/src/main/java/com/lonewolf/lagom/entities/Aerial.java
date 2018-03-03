package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.builders.SpriteBuilder;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.modules.effects.TextureMapping;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

import java.util.Random;

/**
 * Created by Ian on 23/01/2017.
 */

public class Aerial {

    private static final Random random = new Random();

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private final Stats stats;

    private boolean active;

    public Aerial(int shaderProgram, int texture) {

        float radius = 0.1f;

        this.rigidBody = new RigidBody(1, radius / 1.5f, new Vector2(2.0f + (random.nextFloat() *
                2), -0.51f), new Vector2(-0.5f, 0.0f));

        float[][] textureFramesCoordinates = {EntityUtils
                .QUARTER_TEXTURE_COORDINATES, EntityUtils.GenerateTextureCoordinates(0.5f, 0.5f,
                0.0f), EntityUtils.GenerateTextureCoordinates(0.5f, 0.0f, 0.5f)};

        TextureMapping textureMapping = new
                TextureMapping(textureFramesCoordinates);

        this.sprite = new SpriteBuilder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withTextureMapping(textureMapping).build();

        this.stats = new Stats(10, textureMapping);
        active = false;
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

    public Stats getStats() {
        return stats;
    }
}