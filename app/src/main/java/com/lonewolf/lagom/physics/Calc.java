package com.lonewolf.lagom.physics;

/**
 * Created by Ian on 12/02/2017.
 */

public final class Calc {

    private final static float RADIAN_ANGLE = 57.2958f;

    public static Vector2 EulerMethod(Vector2 base, Vector2 change, float time) {
        return base.add(change.multiply(time));
    }

    public static float Angle(Vector2 v1, Vector2 v2) {
        float theta = v1.dotProduct(v2) / (v1.getLength() * v2.getLength());
        return (float) (Math.acos(theta) * RADIAN_ANGLE);
    }

    private Calc() {

    }

}
