package com.lonewolf.lagom.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.lonewolf.lagom.resources.ResourceManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;

/**
 * Created by Ian on 22/01/2017.
 */

public class GameRenderer implements GLSurfaceView.Renderer {

    private final ResourceManager resourceManager;

    private float[] mMVPMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];

    public GameRenderer(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        resourceManager.loadResources();

        GLES20.glClearColor(0.0f, 0.5f, 0.75f, 1f);

        Matrix.setIdentityM(mViewMatrix, 0);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.orthoM(mProjectionMatrix, 0, -ratio, +ratio, -1, 1, -1, 1);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        resourceManager.background.draw(mMVPMatrix, 0.001f);

        resourceManager.panoramaFar.draw(mMVPMatrix, 0.001f);

        resourceManager.panorama.draw(mMVPMatrix, 0.001f);

        float[] moveMatrix = new float[16];

        Matrix.setIdentityM(moveMatrix, 0);

        Matrix.translateM(moveMatrix, 0, -1.0f, -0.53f, 0);

        Matrix.multiplyMM(moveMatrix, 0, mMVPMatrix, 0, moveMatrix, 0);

        resourceManager.player.draw(moveMatrix);

        resourceManager.foreground.draw(mMVPMatrix, 0.001f);

        checkGlError("Draw");

    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
