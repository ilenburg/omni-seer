package com.lonewolf.lagom.entities;

import android.opengl.Matrix;

import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 11/02/2017.
 */

public class RigidBody {

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

    public float[] getModelMatrix() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, this.position.getX(), this.position.getY(), 0);
        return mModelMatrix;
    }
}
