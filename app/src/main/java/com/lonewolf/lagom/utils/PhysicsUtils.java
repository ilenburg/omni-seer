package com.lonewolf.lagom.utils;

import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.engine.Vector2;

import static com.lonewolf.lagom.engine.Vector2.dotProduct;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_FORWARD;

/**
 * Created by Ian on 12/02/2017.
 */

public final class PhysicsUtils {

    private static final float RADIAN_ANGLE = 57.2958f;
    private static final Vector2 VECTOR_FROM_PLAYER = new Vector2();
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
        return Collide(r1, r2, true);
    }

    public static boolean Collide(RigidBody r1, RigidBody r2, boolean applyFeedback) {

        Vector2 p1 = r1.getPosition();
        Vector2 p2 = r2.getPosition();

        float dx = (float) Math.pow(p2.getX() - p1.getX(), 2);
        float dy = (float) Math.pow(p2.getY() - p1.getY(), 2);
        float dist = (float) Math.sqrt(dx + dy);

        if (dist < r1.getRadius() + r2.getRadius()) {

            if (applyFeedback) {
                float pen = r1.getRadius() + r2.getRadius() - dist;
                Vector2.sub(RESULT_AUX, r1.getPosition(), r2.getPosition());
                Vector2.normalize(RESULT_AUX, RESULT_AUX);
                Vector2.multiply(RESULT_AUX, RESULT_AUX, pen);

                Vector2.add(r1.getPosition(), r1.getPosition(), RESULT_AUX);
            }

            return true;
        }

        return false;
    }

    public static boolean Collide(Vector2 p1, RigidBody r2) {
        Vector2 p2 = r2.getPosition();

        float dx = (float) Math.pow(p2.getX() - p1.getX(), 2);
        float dy = (float) Math.pow(p2.getY() - p1.getY(), 2);
        float dist = (float) Math.sqrt(dx + dy);

        if (dist < r2.getRadius()) {
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

        r1.setVelocity(OPERAND_AUX_2);
        r2.setVelocity(OPERAND_AUX_3);
    }

    public static void updateRigidBody(RigidBody rigidBody, float deltaTime) {
        PhysicsUtils.EulerMethod(rigidBody.getVelocity(), rigidBody.getVelocity(), rigidBody
                .getAcceleration(), deltaTime);
        PhysicsUtils.EulerMethod(rigidBody.getPosition(), rigidBody.getPosition(), rigidBody
                .getVelocity(), deltaTime);
    }

    public static void updatePlayerPosition(RigidBody playerRigidBody, float deltaTime) {
        PhysicsUtils.EulerMethod(playerRigidBody.getVelocity(), playerRigidBody.getVelocity(),
                playerRigidBody.getAcceleration(), deltaTime);
        PhysicsUtils.EulerMethod(RESULT_AUX, playerRigidBody.getPosition(), playerRigidBody
                .getVelocity(), deltaTime);
        playerRigidBody.setPositionY(RESULT_AUX.getY());
    }

    public static Vector2 getVectorFromPlayer(RigidBody rigidBody, Vector2 playerPosition) {
        VECTOR_FROM_PLAYER.setCoordinates(rigidBody.getPosition());
        Vector2.sub(VECTOR_FROM_PLAYER, VECTOR_FROM_PLAYER, playerPosition);
        return VECTOR_FROM_PLAYER;
    }

    public static void lookAtPlayer(RigidBody rigidBody, Vector2 vectorToPlayer) {
        float angle = PhysicsUtils.CalcAngle(vectorToPlayer, VECTOR_FORWARD);
        rigidBody.setAngle(angle);
    }

    public static void floatEffect(RigidBody rigidBody, float positionY) {
        if (positionY > 0.2f) {
            rigidBody.setAccelerationY(-0.1f);
        } else {
            rigidBody.setAccelerationY(0.1f);
        }
    }
}
