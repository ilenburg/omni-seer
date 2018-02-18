package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 30/08/2017.
 */

public final class EntityUtils {

    public static final float[] FULL_TEXTURE_COORDINATES;
    public static final float[] QUARTER_TEXTURE_COORDINATES;

    public static final float[][] EIGHT_TEXTURE_COORDINATES = new float[12][8];

    static {
        FULL_TEXTURE_COORDINATES = GenerateTextureCoordinates(1.0f);
        QUARTER_TEXTURE_COORDINATES = GenerateTextureCoordinates(0.5f);

        int index = 0;
        final float radius = 0.25f;
        float horizontalDisplacement = 0.0f;
        float verticalDisplacement = 0.0f;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j) {
                EIGHT_TEXTURE_COORDINATES[index] = GenerateTextureCoordinates(radius, horizontalDisplacement, verticalDisplacement);
                horizontalDisplacement += 0.25f;
                ++index;
            }
            horizontalDisplacement = 0.0f;
            verticalDisplacement += 0.25f;
        }
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

    public static float[] GenerateTextureCoordinates(float radius, float horizontalDisplacement, float verticalDisplacement) {
        return new float[]{
                horizontalDisplacement, verticalDisplacement,
                horizontalDisplacement, verticalDisplacement + radius,
                horizontalDisplacement + radius, verticalDisplacement + radius,
                horizontalDisplacement + radius, verticalDisplacement
        };
    }
}
