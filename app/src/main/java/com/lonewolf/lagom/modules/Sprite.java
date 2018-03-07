package com.lonewolf.lagom.modules;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.modules.effects.Scroll;
import com.lonewolf.lagom.modules.effects.TextureMapping;
import com.lonewolf.lagom.modules.effects.TextureTransition;
import com.lonewolf.lagom.physics.Vector2;

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
    private int damagePosition;

    private final float[] mModelMatrix;

    private Scroll scroll;
    private Animation animation;
    private TextureMapping textureMapping;
    private TextureTransition textureTransition;
    private ColorTransition colorTransition;
    private Stats stats;

    private Sprite(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates) {
        this.shaderProgram = shaderProgram;
        this.texture = texture;

        this.uniformMVPMatrixPosition = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");
        this.vertexPosition = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        this.texturePosition = GLES20.glGetAttribLocation(shaderProgram, "texCoordIn");

        this.mModelMatrix = new float[16];
        Matrix.setIdentityM(mModelMatrix, 0);

        this.damagePosition = 0;

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
    }

    public void setScroll(Scroll scroll) {
        this.scroll = scroll;
        this.scroll.setScrollPosition(GLES20.glGetUniformLocation(shaderProgram, "scroll"));
    }

    public void setPosition(Vector2 position) {
        Matrix.translateM(mModelMatrix, 0, position.getX(), position.getY(), 0);
    }

    public void setTextureTransition(TextureTransition textureTransition) {
        this.textureTransition = textureTransition;
        this.textureTransition.setTexturePosition(GLES20.glGetUniformLocation(shaderProgram,
                "tex2"));
        this.textureTransition.setTimePosition(GLES20.glGetUniformLocation(shaderProgram, "time"));

    }

    public void setColorTransition(ColorTransition colorTransition) {
        this.colorTransition = colorTransition;
        this.colorTransition.setTimePosition(GLES20.glGetUniformLocation(shaderProgram, "time"));
    }

    public void setStats(Stats stats) {
        this.stats = stats;
        this.damagePosition = GLES20.glGetUniformLocation(shaderProgram, "damage");
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        this.animation.setTextureBuffer(textureBuffer);
    }

    public void setTextureMapping(TextureMapping textureMapping) {
        this.textureMapping = textureMapping;
        this.textureMapping.setTextureBuffer(textureBuffer);
    }

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

    public TextureMapping getTextureMapping() {
        return textureMapping;
    }

    public TextureTransition getTextureTransition() {
        return textureTransition;
    }

    public ColorTransition getColorTransition() {
        return colorTransition;
    }

    public Stats getStats() {
        return stats;
    }

    public float[] getModelMatrix() {
        return mModelMatrix;
    }

    public int getDamagePosition() {
        return damagePosition;
    }

    public static class Builder {

        private final int shaderProgram;
        private final int texture;
        private final float[] geometry;
        private final float[] textureCoordinates;

        private Scroll scroll;
        private Animation animation;
        private TextureMapping textureMapping;
        private TextureTransition textureTransition;
        private ColorTransition colorTransition;
        private Stats stats;
        private Vector2 positionCoordinates;

        public Builder(int shaderProgram, int texture, float[] geometry, float[]
                textureCoordinates) {
            this.shaderProgram = shaderProgram;
            this.texture = texture;
            this.geometry = geometry;
            this.textureCoordinates = textureCoordinates;
        }

        public Sprite build() {
            Sprite sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates);
            if (scroll != null) sprite.setScroll(scroll);
            if (animation != null) sprite.setAnimation(animation);
            if (textureMapping != null) sprite.setTextureMapping(textureMapping);
            if (textureTransition != null) sprite.setTextureTransition(textureTransition);
            if (colorTransition != null) sprite.setColorTransition(colorTransition);
            if (stats != null) sprite.setStats(stats);
            if (positionCoordinates != null) sprite.setPosition(positionCoordinates);
            return sprite;
        }

        public Builder withScroll(Scroll scroll) {
            this.scroll = scroll;
            return this;
        }

        public Builder withAnimation(Animation animation) {
            this.animation = animation;
            return this;
        }

        public Builder withTextureMapping(TextureMapping textureMapping) {
            this.textureMapping = textureMapping;
            return this;
        }

        public Builder withTextureTransition(TextureTransition textureTransition) {
            this.textureTransition = textureTransition;
            return this;
        }

        public Builder withColorTransition(ColorTransition colorTransition) {
            this.colorTransition = colorTransition;
            return this;
        }

        public Builder withStats(Stats stats) {
            this.stats = stats;
            return this;
        }

        public Builder withPosition(Vector2 positionCoordinates) {
            this.positionCoordinates = positionCoordinates;
            return this;
        }
    }

}
