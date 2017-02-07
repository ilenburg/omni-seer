package com.lonewolf.lagom.entities;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian on 23/01/2017.
 */

public class Player extends Entity {

    private long timeLapsed = 0;
    private long lastTime = System.currentTimeMillis();
    int pos = 0;


    private float[] m0 = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f
    };

    private float[] m1 = new float[]{
            0.5f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
            1.0f, 0.0f
    };

    private float[] m2 = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f
    };

    private float[] m3 = new float[]{
            0.0f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            0.5f, 0.5f
    };

    private List<float[]> stuff = new ArrayList<float[]>();

    public Player(int shaderProgram, int texture) {

        super(shaderProgram, texture, new float[]{
                -0.12f, 0.12f,
                -0.12f, -0.12f,
                0.12f, -0.12f,
                0.12f, 0.12f
        }, new float[]{
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f
        });

        stuff.add(m0);
        stuff.add(m1);
        stuff.add(m2);
        stuff.add(m3);
    }

    @Override
    void extraDraw(float cameraMove) {

        timeLapsed += System.currentTimeMillis() - lastTime;

        lastTime = System.currentTimeMillis();

        if(timeLapsed > 200) {
            timeLapsed = 0;
            pos = (pos + 1) % stuff.size();
        }

        textureBuffer.put(stuff.get(pos));
        textureBuffer.rewind();
        GLES20.glVertexAttribPointer(tPosition, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBuffer);
    }
}
