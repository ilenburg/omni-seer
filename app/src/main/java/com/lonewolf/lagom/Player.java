package com.lonewolf.lagom;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player {

    private int shaderProgram;

    private int texture;

    private FloatBuffer vertexBuffer;

    private FloatBuffer textureBuffer;

    private ShortBuffer orderBuffer;

    private final int vPosition = 0;
    private final int tPosition = 1;

    private float[] geometry = {
            -0.25f, 0.25f, 0.0f,
            -0.25f, -0.25f, 0.0f,
            0.25f, -0.25f, 0.0f,
            0.25f, 0.25f, 0.0f
    };

    private float texturePos[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    private final short drawOrder[] = {0, 1, 2, 0, 2, 3};

    public Player(int shaderProgram, int texture) {
        this.shaderProgram = shaderProgram;
        this.texture = texture;

        generateBuffers();
    }

    public void draw(float[] mvpMatrix) {

        GLES20.glUseProgram(shaderProgram);

        GLES20.glEnableVertexAttribArray(vPosition);

        GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);

        GLES20.glEnableVertexAttribArray(tPosition);

        GLES20.glVertexAttribPointer(tPosition, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBuffer);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, orderBuffer);

    }

    private void generateBuffers() {

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


}
