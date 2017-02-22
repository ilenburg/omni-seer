package com.lonewolf.lagom.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Spell;
import com.lonewolf.lagom.physics.GameEngine;
import com.lonewolf.lagom.resources.ResourceManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;

/**
 * Created by Ian on 22/01/2017.
 */

public class GameRenderer implements GLSurfaceView.Renderer {

    private final ResourceManager resourceManager;
    private final GameEngine gameEngine;

    private boolean initialized;

    private float[] mMVPMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mVPMatrix = new float[16];

    public GameRenderer(ResourceManager resourceManager, GameEngine gameEngine) {
        this.resourceManager = resourceManager;
        this.gameEngine = gameEngine;
        this.initialized = false;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        resourceManager.loadResources();

        if (!initialized) {
            new Thread(gameEngine).start();
        }

        GLES20.glClearColor(0.0f, 0.5f, 0.75f, 1f);

        Matrix.setIdentityM(mViewMatrix, 0);

        Matrix.scaleM(mViewMatrix, 0, 1.1f, 1.1f, 0);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        this.initialized = true;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        resourceManager.getPlayer().getInput().setScreenSize(width, height, ratio);

        Matrix.orthoM(mProjectionMatrix, 0, -ratio, +ratio, -1, 1, -1, 1);

        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        draw(resourceManager.getBackground().getSprite());

        draw(resourceManager.getPanoramaFar().getSprite());

        draw(resourceManager.getPanorama().getSprite());

        /*float[] moveMatrix = new float[16];

        Matrix.setIdentityM(moveMatrix, 0);

        Matrix.translateM(moveMatrix, 0, -1.0f, -0.53f, 0);

        Matrix.multiplyMM(moveMatrix, 0, mMVPMatrix, 0, moveMatrix, 0);*/

        for (Spell spell : resourceManager.getActiveSpells()) {
            if (spell.isActive()) {
                draw(spell.getSprite(), spell.getRigidBody().getModelMatrix());
            }
        }

        MegaSpell megaSpell = resourceManager.getMegaSpell();

        if (megaSpell.isActive()) {
            draw(megaSpell.getSprite(), megaSpell.getRigidBody().getModelMatrix());
        }

        draw(resourceManager.getPlayer().getSprite(), resourceManager.getPlayer().getRigidBody().getModelMatrix());

        draw(resourceManager.getForeground().getSprite());

        //checkGlError("Draw");

    }

    private void draw(Sprite sprite) {
        float[] mModelMatrix = new float[16];
        Matrix.setIdentityM(mModelMatrix, 0);
        draw(sprite, mModelMatrix);
    }

    private void draw(Sprite sprite, float[] mModelMatrix) {

        GLES20.glUseProgram(sprite.getShaderProgram());

        GLES20.glEnableVertexAttribArray(sprite.getVertexPosition());
        GLES20.glVertexAttribPointer(sprite.getVertexPosition(), 2, GLES20.GL_FLOAT, false, 2 * 4, sprite.getVertexBuffer());

        GLES20.glEnableVertexAttribArray(sprite.getTexturePosition());
        GLES20.glVertexAttribPointer(sprite.getTexturePosition(), 2, GLES20.GL_FLOAT, false, 2 * 4, sprite.getTextureBuffer());

        Matrix.multiplyMM(mMVPMatrix, 0, mVPMatrix, 0, mModelMatrix, 0);

        GLES20.glUniformMatrix4fv(sprite.getUniformMVPMatrixPosition(), 1, false, mMVPMatrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, sprite.getTexture());

        TextureTransition textureTransition = sprite.getTextureTransition();

        if (textureTransition != null) {
            textureTransition.setTime(gameEngine.getTotalTime());
            GLES20.glUniform1f(textureTransition.getTimePosition(), textureTransition.getTime());
            GLES20.glUniform1i(textureTransition.getTexturePosition(), 1);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureTransition.getTexture());
        }

        ColorTransition colorTransition = sprite.getColorTransition();

        if(colorTransition != null) {
            colorTransition.setTime(gameEngine.getTotalTime() * 500);
            GLES20.glUniform1f(colorTransition.getTimePosition(), colorTransition.getTime());
        }

        Scroll scroll = sprite.getScroll();

        if (scroll != null) {
            scroll.setDisplacement(gameEngine.getCameraPositon(), sprite.getTexture() == 2);
            GLES20.glUniform1f(scroll.getScrollPosition(), scroll.getDisplacement());
        }

        Animation animation = sprite.getAnimation();

        if (animation != null) {
            sprite.getAnimation().update(gameEngine.getDeltaTime());
        }

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, sprite.getDrawOrder().length, GLES20.GL_UNSIGNED_SHORT, sprite.getOrderBuffer());

    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
