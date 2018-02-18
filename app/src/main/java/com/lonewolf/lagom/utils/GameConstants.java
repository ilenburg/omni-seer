package com.lonewolf.lagom.utils;

import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 18/02/2018.
 */

public final class GameConstants {

    public static final float GRAVITY_ACCELERATION = -9.8f * 0.8f;
    public static final float ZERO = 0.0f;
    public static final float GROUND_POSITION = -0.535f;

    public static final Vector2 VECTOR_FORWARD = new Vector2(1.0f, ZERO);
    public static final Vector2 VECTOR_BACK = new Vector2(-1.0f, ZERO);
    public static final Vector2 VECTOR_UP = new Vector2(ZERO, 1.0f);
    public static final Vector2 VECTOR_DOWN = new Vector2(ZERO, -1.0f);
    public static final Vector2 VECTOR_GHOST = new Vector2(ZERO, 0.3f);
    public static final Vector2 SPELL_BASE_VELOCITY = new Vector2(2.0f, ZERO);
    public static final Vector2 SPELL_DISPLACEMENT = new Vector2(0.03f, -0.03f);

    public static final int SPELL_DAMAGE = 1;

    private GameConstants() {

    }

}
