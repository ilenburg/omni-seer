package com.lonewolf.lagom.modules;

import com.lonewolf.lagom.engine.Vector2;

import static com.lonewolf.lagom.utils.GameConstants.VECTOR_ZERO;

/**
 * Created by Ian Ilenburg on 11/02/2017.
 */

public class RigidBody {

    private static final Vector2 RESULT_AUX = new Vector2();

    private final float mass;
    private final float radius;

    private final Vector2 velocity;
    private final Vector2 acceleration;

    private final Position position;

    public RigidBody(float mass, float radius) {
        this(mass, radius, new Vector2());
    }

    public RigidBody(float mass, float radius, Vector2 position) {
        this(mass, radius, position, new Vector2());
    }

    public RigidBody(float mass, float radius, Vector2 positionCoordinates, Vector2 velocity) {
        this.mass = mass;
        this.radius = radius;
        this.velocity = velocity;
        this.acceleration = new Vector2();
        this.position = new Position(positionCoordinates);
    }

    public float getRadius() {
        return radius;
    }

    public float getMass() {
        return mass;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.setX(velocity.getX());
        this.velocity.setY(velocity.getY());
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration.setX(acceleration.getX());
        this.acceleration.setY(acceleration.getY());
    }

    public void setAccelerationY(float accelerationY) {
        this.acceleration.setY(accelerationY);
    }

    public void addVelocityX(float velocityX) {
        this.velocity.setX(this.velocity.getX() + velocityX);
    }

    public void setVelocityX(float velocityX) {
        this.velocity.setX(velocityX);
    }

    public void setVelocityY(float velocityY) {
        this.velocity.setY(velocityY);
    }

    public void applyForce(Vector2 force) {
        Vector2.divide(RESULT_AUX, force, this.mass);
        Vector2.add(this.acceleration, RESULT_AUX, this.acceleration);
    }

    public Vector2 getPosition() {
        return position.getCoordinates();
    }

    public float getAngle() {
        return this.position.getAngle();
    }

    public void setPosition(Vector2 positionCoordinates) {
        this.position.setCoordinates(positionCoordinates);
    }

    public void setPosition(float x, float y) {
        this.position.setCoordinates(x, y);
    }

    public void setPositionX(float x) {
        this.position.setCoordinateX(x);
    }

    public void displaceX(float displacement) {
        this.position.displaceX(displacement);
    }

    public void setPositionY(float positionY) {
        this.position.setCoordinateY(positionY);
    }

    public void setAngle(float angle) {
        this.position.setAngle(angle);
    }

    public void addAngle(float angle) {
        this.position.addAngle(angle);
    }

    public void stop() {
        this.setAcceleration(VECTOR_ZERO);
        this.setVelocity(VECTOR_ZERO);
        this.setAngle(0);
    }

    public float[] getModelMatrix() {
        return this.position.getModelMatrix();
    }
}
