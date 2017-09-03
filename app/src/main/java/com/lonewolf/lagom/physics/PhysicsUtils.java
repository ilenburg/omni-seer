package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.modules.RigidBody;

import static com.lonewolf.lagom.physics.Vector2.dotProduct;

/**
 * Created by Ian on 12/02/2017.
 */

public final class PhysicsUtils {

    private static final float RADIAN_ANGLE = 57.2958f;
    private static final Vector2 OPERAND_AUX_1 = new Vector2();
    private static final Vector2 OPERAND_AUX_2 = new Vector2();
    private static final Vector2 OPERAND_AUX_3 = new Vector2();
    private static final Vector2 RESULT_AUX = new Vector2();

    private PhysicsUtils() {

    }

    public static void EulerMethod(Vector2 vR, Vector2 base, Vector2 change, float time) {
        Vector2.multiply(RESULT_AUX, change, time);
        Vector2.add(vR, base, RESULT_AUX);
    }

    public static float CalcAngle(Vector2 v1, Vector2 v2) {
        int swap = v1.getY() < 0.0f ? -1 : 1;
        float theta = dotProduct(v1, v2) / (v1.getLength() * v2.getLength());
        return ((float) (Math.acos(theta) * RADIAN_ANGLE)) * swap;
    }

    public static boolean Collide(RigidBody r1, RigidBody r2) {

        Vector2 p1 = r1.getPosition();
        Vector2 p2 = r2.getPosition();

        float dx = (float) Math.pow(p2.getX() - p1.getX(), 2);
        float dy = (float) Math.pow(p2.getY() - p1.getY(), 2);
        float dist = (float) Math.sqrt(dx + dy);

        if (dist < r1.getRadius() + r2.getRadius()) {

            float pen = r1.getRadius() + r2.getRadius() - dist;
            Vector2.sub(RESULT_AUX, r1.getPosition(), r2.getPosition());
            Vector2.normalize(RESULT_AUX, RESULT_AUX);
            Vector2.multiply(RESULT_AUX, RESULT_AUX, pen);

            Vector2.add(r1.getPosition(), r1.getPosition(), RESULT_AUX);

            return true;
        }

        return false;
    }

    public static void CollisionResponse(RigidBody r1, RigidBody r2) {

        Vector2.sub(OPERAND_AUX_1, r1.getPosition(), r2.getPosition());
        Vector2.normalize(OPERAND_AUX_1, OPERAND_AUX_1);

        Vector2.sub(OPERAND_AUX_2, r1.getVelocity(), r2.getVelocity());
        float vn = dotProduct(OPERAND_AUX_1, OPERAND_AUX_2);
        float eq1 = (-1f * (1f + 0.9f)) * vn;

        float mass = (1f / r1.getMass() + 1f / r2.getMass());
        Vector2.multiply(OPERAND_AUX_3, OPERAND_AUX_1, mass);
        float eq2 = Vector2.dotProduct(OPERAND_AUX_1, OPERAND_AUX_3);

        float sForce = eq1 / eq2;

        Vector2.multiply(OPERAND_AUX_2, OPERAND_AUX_1, sForce / r1.getMass());
        Vector2.add(OPERAND_AUX_2, r1.getVelocity(), OPERAND_AUX_2);

        Vector2.multiply(OPERAND_AUX_3, OPERAND_AUX_1, sForce / r2.getMass());
        Vector2.sub(OPERAND_AUX_3, r2.getVelocity(), OPERAND_AUX_3);

        r1.setVelocityValue(OPERAND_AUX_2);
        r2.setVelocityValue(OPERAND_AUX_3);
    }

}
