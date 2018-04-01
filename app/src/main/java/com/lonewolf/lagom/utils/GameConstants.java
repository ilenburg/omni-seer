package com.lonewolf.lagom.utils;

import com.lonewolf.lagom.engine.Vector2;

/**
 * Created by Ian on 18/02/2018.
 */

public final class GameConstants {

    public static final String OMNI_PREFERENCE_KEY = "seerPreferenceKey";
    public static final String HIGH_SCORE = "highScore";

    public static final int STARTING_MANA = 3;

    public static final float ZERO = 0.0f;
    public static final float GRAVITY_ACCELERATION = -9.8f * 0.8f;
    public static final float PLAYER_GROUND_POSITION = -0.535f;
    public static final float CAPSULE_GROUND_POSITION = -0.575f;
    public static final float OUT_OF_SIGH = -1.0f;
    public static final float HEAVEN = 1.2f;

    public static final Vector2 VECTOR_ZERO = new Vector2(ZERO, ZERO);
    public static final Vector2 VECTOR_FORWARD = new Vector2(1.0f, ZERO);
    public static final Vector2 VECTOR_BACK = new Vector2(-1.0f, ZERO);
    public static final Vector2 VECTOR_UP = new Vector2(ZERO, 1.0f);
    public static final Vector2 VECTOR_DOWN = new Vector2(ZERO, -1.0f);
    public static final Vector2 GHOST_VELOCITY = new Vector2(ZERO, 0.3f);
    public static final Vector2 SPELL_BASE_VELOCITY = new Vector2(2.0f, ZERO);
    public static final Vector2 SPELL_DISPLACEMENT = new Vector2(0.03f, -0.03f);
    public static final Vector2 MINION_STARTING_VELOCITY = new Vector2(-0.2f, 0.0f);
    public static final Vector2 MINION_DOWN_VELOCITY = new Vector2(-0.2f, -0.3f);
    public static final Vector2 ROLLER_STARTING_VELOCITY = new Vector2(-1.5f, 0.0f);
    public static final Vector2 AERIAL_STARTING_VELOCITY = new Vector2(-0.5f, 0.0f);
    public static final Vector2 CAPSULE_STARTING_VELOCITY = new Vector2(-0.19f, 0.0f);
    public static final Vector2 PLAYER_POSITION = new Vector2(-1.0f, -0.535f);
    public static final Vector2 PLAYER_VELOCITY = new Vector2(2.0f, 0.0f);

    private GameConstants() {

    }

}
