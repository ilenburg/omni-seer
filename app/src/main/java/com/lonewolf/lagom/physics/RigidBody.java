package com.lonewolf.lagom.physics;

import android.opengl.Matrix;

/**
 * Created by Ian on 11/02/2017.
 */

public class RigidBody {

    private static final Vector2 RESULT_AUX = new Vector2();

    private final float mass;
    private final float radius;

    private float angle;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private final float[] mModelMatrix;

    public RigidBody(float mass, float radius) {
        this(mass, radius, new Vector2());
    }

    public RigidBody(float mass, float radius, Vector2 position) {
        this(mass, radius, position, new Vector2());
    }

    public RigidBody(float mass, float radius, Vector2 position, Vector2 velocity) {
        this.mass = mass;
        this.radius = radius;
        this.angle = 0.0f;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = new Vector2();
        this.mModelMatrix = new float[16];
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public float getMass() {
        return mass;
    }

    public void setPositionX(float x) {
        setPosition(x, this.position.getY());
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setAccelerationY(float accelerationY) {
        this.acceleration.setY(accelerationY);
    }

    public void setVelocityX(float velocityX) {
        this.velocity.setX(velocityX);
    }

    public void setVelocityY(float velocityY) {
        this.velocity.setY(velocityY);
    }

    public void setVelocityValue(Vector2 velocity) {
        this.velocity.setX(velocity.getX());
        this.velocity.setY(velocity.getY());
    }

    public void setPositionY(float positionY) {
        this.position.setY(positionY);
    }

    public void applyForce(Vector2 force) {
        Vector2.divide(RESULT_AUX, force, this.mass);
        Vector2.add(this.acceleration, RESULT_AUX, this.acceleration);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void addAngle(float angle) {
        this.angle += angle;
    }

    public float[] getModelMatrix() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, this.position.getX(), this.position.getY(), 0);
        Matrix.rotateM(mModelMatrix, 0, angle, 0, 0, 1);
        return mModelMatrix;
    }
}
