package com.lonewolf.lagom.graphics;

/**
 * Created by Ian on 11/02/2017.
 */

public class TextureTransition {

    private float time;
    private int timePosition;

    private int texturePosition;
    private final int texture;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = (float) Math.abs(Math.sin(time / 100) * 3);
    }

    public int getTexturePosition() {
        return texturePosition;
    }

    public void setTexturePosition(int texturePosition) {
        this.texturePosition = texturePosition;
    }

    public int getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }

    public int getTexture() {
        return texture;
    }

    public TextureTransition(int texture) {
        this.texture = texture;
        this.time = 0.0f;
    }

}
