package com.lonewolf.lagom.physics;

/**
 * Created by Ian on 11/02/2017.
 */

public class Vector2 {

    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZero() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2 add(Vector2 vector2) {
        return new Vector2(this.x + vector2.x, this.y + vector2.y);
    }

    public Vector2 sub(Vector2 vector2) {
        return new Vector2(this.x - vector2.x, this.y - vector2.y);
    }

    public Vector2 multiply(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public Vector2 divide(float scalar) {
        return new Vector2(this.x / scalar, this.y / scalar);
    }

    public Vector2 normalize() {
        float length = getLength();
        return new Vector2(this.x / length, this.y / length);
    }

    public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }

    public float dotProduct(Vector2 vector2) {
        return (this.x * vector2.x + this.y * vector2.y);
    }

    public float getLength() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public boolean isBounded() {
        if (this.x > 3.0f || this.y > 1.0f || this.x < -3.0f || this.y < -1.0f)
            return false;
        return true;
    }

    public boolean isZero() {
        return (this.x == 0.0f && this.y == 0.0f);
    }

    @Override
    public String toString() {
        return "X: " + this.x + "Y: " + this.y;
    }
}
