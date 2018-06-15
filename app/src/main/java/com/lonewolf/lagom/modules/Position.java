package com.lonewolf.lagom.modules;

import android.opengl.Matrix;

import com.lonewolf.lagom.engine.Vector2;

/**
 * Created by Ian on 25/02/2018.
 */

public class Position {

    private final Vector2 coordinates;
    private float angle;

    private final float[] mModelMatrix;

    public Position() {
        this(new Vector2(0.0f, 0.0f));
    }

    public Position(Vector2 coordinates) {
        this.mModelMatrix = new float[16];
        this.coordinates = coordinates;
        this.angle = 0.0f;
    }

    public Vector2 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vector2 coordinates) {
        this.coordinates.setX(coordinates.getX());
        this.coordinates.setY(coordinates.getY());
    }

    public void setCoordinates(float x, float y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    public void setCoordinateX(float x) {
        setCoordinates(x, this.coordinates.getY());
    }

    public void displaceX(float displacement) {
        this.coordinates.setX(this.coordinates.getX() + displacement);
    }

    public void setCoordinateY(float coordinatesY) {
        this.coordinates.setY(coordinatesY);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void addAngle(float angle) {
        this.angle += angle;
    }

    public float[] getModelMatrix() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, this.coordinates.getX(), this.coordinates.getY(), 0);
        Matrix.rotateM(mModelMatrix, 0, angle, 0, 0, 1);
        return mModelMatrix;
    }

}
