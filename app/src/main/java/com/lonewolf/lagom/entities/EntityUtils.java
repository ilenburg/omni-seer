package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 30/08/2017.
 */

public final class EntityUtils {

    private EntityUtils() {

    }

    public static float[] getSymetricGeometry(float radius) {
        return new float[]{
                -radius, radius,
                -radius, -radius,
                radius, -radius,
                radius, radius
        };
    }

    public static float[] getFullTexture() {
        return new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };
    }
}