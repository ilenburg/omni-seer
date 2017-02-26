package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.State;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.physics.RigidBody;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player {

    private static final float MAX_RADIANS = 3.14159265f;

    private final Sprite sprite;
    private final RigidBody rigidBody;
    private final Input input;
    private final State state;

    public Player(int shaderProgram, int texture) {

        float[] geometry = new float[]{
                -0.12f, 0.12f,
                -0.12f, -0.12f,
                0.12f, -0.12f,
                0.12f, 0.12f
        };

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
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

        this.input = new Input();
        this.rigidBody = new RigidBody(1, new Vector2(-1.0f, -0.535f), new Vector2(2.0f, 0.0f));

        this.state = new State();

        Animation animation = new Animation(animationCoordinates, 2.0f, rigidBody);

        ColorTransition colorTransition = new ColorTransition(MAX_RADIANS, this.state);

        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates, animation, colorTransition);

    }

    public State getState() {
        return state;
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
}
