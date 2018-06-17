package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.entities.base.PhysicalEntity;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.utils.EntityUtils;
import com.lonewolf.lagom.utils.GameConstants;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player extends PhysicalEntity {

    private final Input input;
    private final Stats stats;
    private boolean wasAlive;

    public Player(int shaderProgram, int texture) {

        float[] jumpCoordinates = new float[]{
                0.5f, 0.5f,
                0.5f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.5f
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
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        }, {
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f
        }};

        float radius = 0.12f;

        this.input = new Input();
        this.rigidBody = new RigidBody(1, radius / 2, GameConstants.PLAYER_POSITION.copy(), GameConstants.PLAYER_VELOCITY.copy());

        Animation animation = new Animation.Builder(animationCoordinates, 1.5f).withRigidBody
                (rigidBody).withJumpTextureCoordinates(jumpCoordinates).withInput(input).build();

        ColorTransition colorTransition = new ColorTransition(GameConstants.MAX_RADIANS, this.input);

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withAnimation(animation).withColorTransition
                (colorTransition).build();

        this.stats = new Stats(1);
        this.wasAlive = true;
        this.setActive(true);
    }

    public Input getInput() {
        return input;
    }

    public boolean isDead() {
        return stats.isDead();
    }

    public boolean isAlive() {
        return stats.isAlive();
    }

    public Stats getStats() {
        return stats;
    }
}
