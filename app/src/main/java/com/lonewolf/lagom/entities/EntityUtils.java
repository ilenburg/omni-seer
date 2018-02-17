package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 30/08/2017.
 */

public final class EntityUtils {

    public static final float[] FULL_TEXTURE_COORDINATES;
    public static final float[] QUARTER_TEXTURE_COORDINATES;

    static {
        FULL_TEXTURE_COORDINATES = GenerateTextureCoordinates(1.0f);
        QUARTER_TEXTURE_COORDINATES = GenerateTextureCoordinates(0.5f);
    }

    private EntityUtils() {

    }

    public static float[] GenerateSymmetricGeometryCoordinates(float radius) {
        return new float[]{
                -radius, radius,
                -radius, -radius,
                radius, -radius,
                radius, radius
        };
    }

    public static float[] GenerateTextureCoordinates(float radius) {
        return new float[]{
                0.0f, 0.0f,
                0.0f, radius,
                radius, radius,
                radius, 0.0f
        };
    }
}
