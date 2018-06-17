package com.lonewolf.lagom.engine;

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

    public void setCoordinates(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public boolean isZero() {
        return (this.x == 0.0f && this.y == 0.0f);
    }

    public boolean isBounded() {
        if (this.x > 3.0f || this.y > 1.0f || this.x < -3.0f || this.y < -1.0f)
            return false;
        return true;
    }

    public float getLength() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }

    public static void add(Vector2 vR, Vector2 v1, Vector2 v2) {
        vR.x = v1.x + v2.x;
        vR.y = v1.y + v2.y;
    }

    public static void sub(Vector2 vR, Vector2 v1, Vector2 v2) {
        vR.x = v1.x - v2.x;
        vR.y = v1.y - v2.y;
    }

    public static void multiply(Vector2 vR, Vector2 vT, float scalar) {
        vR.x = vT.x * scalar;
        vR.y = vT.y * scalar;
    }

    public static void divide(Vector2 vR, Vector2 vT, float scalar) {
        vR.x = vT.x / scalar;
        vR.y = vT.y / scalar;
    }

    public static void normalize(Vector2 vR, Vector2 vT) {
        float vTLength = vT.getLength();
        vR.x = vT.x / vTLength;
        vR.y = vT.y / vTLength;
    }

    public static float dotProduct(Vector2 v1, Vector2 v2) {
        return (v1.x * v2.x + v1.y * v2.y);
    }

    @Override
    public String toString() {
        return "X: " + this.x + " Y: " + this.y;
    }
}
