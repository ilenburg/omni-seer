package com.lonewolf.lagom.physics;

import android.opengl.Matrix;

/**
 * Created by Ian on 11/02/2017.
 */

public class RigidBody {

    private static final float POSITION_RATION = 1000.0f;

    private final float mass;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private final float[] mModelMatrix;

    public RigidBody(float mass) {
        this(mass, new Vector2());
    }

    public RigidBody(float mass, Vector2 position) {
        this(mass, position, new Vector2());
    }

    public RigidBody(float mass, Vector2 position, Vector2 velocity) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = new Vector2();
        this.mModelMatrix = new float[16];
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

    public void setVelocityY(float velocityY) {
        this.velocity.setY(velocityY);
    }

    public void setPositionY(float positionY) {
        this.position.setY(positionY);
    }

    public void applyForce(Vector2 force) {
        this.acceleration = force.divide(this.mass).add(this.acceleration);
    }

    public float[] getModelMatrix() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, this.position.getX(), this.position.getY(), 0);
        return mModelMatrix;
    }
}
