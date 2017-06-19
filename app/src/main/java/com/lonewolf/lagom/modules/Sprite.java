package com.lonewolf.lagom.modules;

import android.opengl.GLES20;

import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.modules.effects.Scroll;
import com.lonewolf.lagom.modules.effects.TextureTransition;

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

    private final Animation animation;

    private final TextureTransition textureTransition;

    private final ColorTransition colorTransition;

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

    public Animation getAnimation() {
        return animation;
    }

    public TextureTransition getTextureTransition() {
        return textureTransition;
    }

    public ColorTransition getColorTransition() {
        return colorTransition;
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Scroll scroll, TextureTransition textureTransition) {
        this(shaderProgram, texture, geometry, textureCoordinates, scroll, null, textureTransition, null);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Animation animation, ColorTransition colorTransition) {
        this(shaderProgram, texture, geometry, textureCoordinates, null, animation, null, colorTransition);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates) {
        this(shaderProgram, texture, geometry, textureCoordinates, null, null, null, null);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, ColorTransition colorTransition) {
        this(shaderProgram, texture, geometry, textureCoordinates, null, null, null, colorTransition);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Scroll scroll) {
        this(shaderProgram, texture, geometry, textureCoordinates, scroll, null, null, null);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Animation animation) {
        this(shaderProgram, texture, geometry, textureCoordinates, null, animation, null, null);
    }

    public Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates, Scroll scroll, Animation animation, TextureTransition textureTransition, ColorTransition colorTransition) {

        this.shaderProgram = shaderProgram;
        this.texture = texture;

        this.uniformMVPMatrixPosition = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        this.vertexPosition = GLES20.glGetAttribLocation(shaderProgram, "vPosition");

        this.texturePosition = GLES20.glGetAttribLocation(shaderProgram, "texCoordIn");

        this.scroll = scroll;

        if (this.scroll != null) {
            this.scroll.setScrollPosition(GLES20.glGetUniformLocation(shaderProgram, "scroll"));
        }

        this.textureTransition = textureTransition;

        if (this.textureTransition != null) {
            this.textureTransition.setTexturePosition(GLES20.glGetUniformLocation(shaderProgram, "tex2"));
            this.textureTransition.setTimePosition(GLES20.glGetUniformLocation(shaderProgram, "time"));
        }

        this.colorTransition = colorTransition;

        if (colorTransition != null) {
            this.colorTransition.setTimePosition(GLES20.glGetUniformLocation(shaderProgram, "time"));
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

        this.animation = animation;

        if (this.animation != null) {
            this.animation.setTextureBuffer(textureBuffer);
        }
    }

}
