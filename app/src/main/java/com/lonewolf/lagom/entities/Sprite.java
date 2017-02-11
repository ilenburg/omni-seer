package com.lonewolf.lagom.entities;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Ian on 28/01/2017.
 */

public class Sprite {

    private static final short[] drawOrder = {0, 1, 2, 0, 2, 3};

    private final int shaderProgram;
    private final int texture;

    private final FloatBuffer vertexBuffer;
    private final FloatBuffer textureBuffer;

    private final ShortBuffer orderBuffer;

    private final int vertexPosition;
    private final int texturePosition;
    private final int uniformMVPMatrixPosition;

    private final Scroll scroll;
    private final boolean scrollable;

    private final Animation animation;
    private final boolean animated;

    public static short[] getDrawOrder() {
        return drawOrder;
    }

    public int getShaderProgram() {
        return shaderProgram;
    }

    public int getTexture() {
        return texture;
    }

    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public FloatBuffer getTextureBuffer() {
        return textureBuffer;
    }

    public ShortBuffer getOrderBuffer() {
        return orderBuffer;
    }

    public int getVertexPosition() {
        return vertexPosition;
    }

    public int getTexturePosition() {
        return texturePosition;
    }

    public int getUniformMVPMatrixPosition() {
        return uniformMVPMatrixPosition;
    }

    public Scroll getScroll() {
        return scroll;
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public Animation getAnimation() {
        return animation;
    }

    public boolean isAnimated() {
        return animated;
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates) {
        this(shaderProgram, texture, geometry, textureCoordinates, null, null);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Scroll scroll) {
        this(shaderProgram, texture, geometry, textureCoordinates, scroll, null);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Animation animation) {
        this(shaderProgram, texture, geometry, textureCoordinates, null, animation);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Scroll scroll, Animation animation) {

        this.shaderProgram = shaderProgram;
        this.texture = texture;

        this.uniformMVPMatrixPosition = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        this.vertexPosition = GLES20.glGetAttribLocation(shaderProgram, "vPosition");

        this.texturePosition = GLES20.glGetAttribLocation(shaderProgram, "texCoordIn");

        if (scroll != null) {
            this.scroll = scroll;
            this.scrollable = true;
        } else {
            this.scroll = null;
            this.scrollable = false;
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(geometry.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(geometry);
        vertexBuffer.rewind();

        bb = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(textureCoordinates);
        textureBuffer.rewind();

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        orderBuffer = dlb.asShortBuffer();
        orderBuffer.put(drawOrder);
        orderBuffer.rewind();

        if (animation != null) {
            this.animation = animation;
            this.animation.setTextureBuffer(textureBuffer);
            this.animated = true;
        } else {
            this.animation = null;
            this.animated = false;
        }
    }

}
