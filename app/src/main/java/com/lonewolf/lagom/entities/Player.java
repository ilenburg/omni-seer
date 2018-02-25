package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.builders.SpriteBuilder;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player {

    private static final float MAX_RADIANS = 3.14159265f;

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private final Input input;

    private boolean active;

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
        this.rigidBody = new RigidBody(1, radius / 2, new Vector2(-1.0f, -0.535f), new Vector2
                (2.0f, 0.0f));

        Animation animation = new Animation.Builder(animationCoordinates, 1.5f).withRigidBody
                (rigidBody).withJumpTextureCoordinates(jumpCoordinates).withInput(input).build();

        ColorTransition colorTransition = new ColorTransition(MAX_RADIANS, this.input);

        this.sprite = new SpriteBuilder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withAnimation(animation).withColorTransition
                (colorTransition).build();

        active = true;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Input getInput() {
        return input;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
