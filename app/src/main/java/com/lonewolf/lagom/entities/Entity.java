package com.lonewolf.lagom.entities;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Ian on 28/01/2017.
 */

public abstract class Entity {

    private static final short drawOrder[] = {0, 1, 2, 0, 2, 3};

    private final int shaderProgram;
    private final int texture;

    private final FloatBuffer vertexBuffer;
    private final FloatBuffer textureBuffer;

    private final ShortBuffer orderBuffer;

    private final int vPosition;
    private final int tPosition;
    private final int uMVPMatrixPosition;

    protected Entity(int shaderProgram, int texture, float[] geometry, float[] texturePos) {

        this.shaderProgram = shaderProgram;
        this.texture = texture;

        this.uMVPMatrixPosition = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        this.vPosition = GLES20.glGetAttribLocation(shaderProgram, "vPosition");

        this.tPosition = GLES20.glGetAttribLocation(shaderProgram, "texCoordIn");

        ByteBuffer bb = ByteBuffer.allocateDirect(geometry.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(geometry);
        vertexBuffer.rewind();

        bb = ByteBuffer.allocateDirect(texturePos.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(texturePos);
        textureBuffer.rewind();

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        orderBuffer = dlb.asShortBuffer();
        orderBuffer.put(drawOrder);
        orderBuffer.rewind();
    }

    public void draw(float[] mvpMatrix) {

        GLES20.glUseProgram(shaderProgram);

        GLES20.glEnableVertexAttribArray(vPosition);

        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 2 * 4, vertexBuffer);

        GLES20.glEnableVertexAttribArray(tPosition);

        GLES20.glVertexAttribPointer(tPosition, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBuffer);

        GLES20.glUniformMatrix4fv(uMVPMatrixPosition, 1, false, mvpMatrix, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, orderBuffer);

    }

}
