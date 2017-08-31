package com.lonewolf.lagom.physics;

/**
 * Created by Ian on 12/02/2017.
 */

public final class CalcUtils {

    private static final float RADIAN_ANGLE = 57.2958f;
    private static final Vector2 RESULT_AUX = new Vector2();

    private CalcUtils() {

    }

    public static void EulerMethod(Vector2 vR, Vector2 base, Vector2 change, float time) {
        Vector2.multiply(RESULT_AUX, change, time);
        Vector2.add(vR, base, RESULT_AUX);
    }

    public static float Angle(Vector2 v1, Vector2 v2) {
        int swap = v1.getY() < 0.0f ? -1 : 1;
        float theta = Vector2.dotProduct(v1, v2) / (v1.getLength() * v2.getLength());
        return ((float) (Math.acos(theta) * RADIAN_ANGLE)) * swap;
    }


}
