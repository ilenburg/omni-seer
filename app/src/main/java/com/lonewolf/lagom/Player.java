package com.lonewolf.lagom;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player {

    private int shaderProgram;

    private FloatBuffer vertexBuffer;

    private float[] geometry = {
            -0.75f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
    };

    public Player(int shaderProgram) {
        this.shaderProgram = shaderProgram;

        generateBuffers();
    }

    public void draw(float[] mvpMatrix) {

        GLES20.glUseProgram(shaderProgram);

        int mPositionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
    
    private void generateBuffers() {
        ByteBuffer bb = ByteBuffer.allocateDirect(geometry.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(geometry);
        vertexBuffer.rewind();
    }


}
