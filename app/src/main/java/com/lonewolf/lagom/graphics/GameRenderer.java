package com.lonewolf.lagom.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.lonewolf.lagom.entities.Aerial;
import com.lonewolf.lagom.entities.Impact;
import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.Player;
import com.lonewolf.lagom.entities.Roller;
import com.lonewolf.lagom.entities.ShadowLord;
import com.lonewolf.lagom.entities.MinorSpell;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.modules.effects.Scroll;
import com.lonewolf.lagom.modules.effects.TextureTransition;
import com.lonewolf.lagom.physics.GameEngine;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.EntityState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;

/**
 * Created by Ian on 22/01/2017.
 */

public class GameRenderer implements GLSurfaceView.Renderer {

    private final ResourceManager resourceManager;
    private final GameEngine gameEngine;

    private Thread physicsThread;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mVPMatrix = new float[16];
    private final float[] mIdentityMatrix = new float[16];

    private float deltaTime = 0.0f;
    private float cameraPosition;

    public GameRenderer(ResourceManager resourceManager, GameEngine gameEngine) {
        this.resourceManager = resourceManager;
        this.gameEngine = gameEngine;
    }

    public void pause() {
        if (physicsThread != null) {
            physicsThread.interrupt();
        }
    }

    public void resume() {
        if (physicsThread != null) {
            physicsThread.start();
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        resourceManager.loadResources();

        physicsThread = new Thread(gameEngine);

        physicsThread.start();

        GLES20.glClearColor(0.0f, 0.5f, 0.75f, 1f);

        Matrix.setIdentityM(mIdentityMatrix, 0);

        Matrix.setIdentityM(mViewMatrix, 0);

        Matrix.scaleM(mViewMatrix, 0, 1.1f, 1.1f, 0);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
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

        deltaTime = gameEngine.getAnimationDeltaTime();
        cameraPosition = gameEngine.getCameraPosition();
        gameEngine.setAnimationDeltaTime(0.0f);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        draw(resourceManager.getBackground().getSprite());

        draw(resourceManager.getPanoramaFar().getSprite());

        draw(resourceManager.getPanorama().getSprite());

        for (MinorSpell minorSpell : resourceManager.getMinorSpells()) {
            if (minorSpell.isActive()) {
                draw(minorSpell.getSprite(), minorSpell.getRigidBody().getModelMatrix());
            }
        }

        for (MegaSpell megaSpell1 : resourceManager.getMegaSpells()) {
            if (megaSpell1.isActive()) {
                draw(megaSpell1.getSprite(), megaSpell1.getRigidBody().getModelMatrix());
            }
        }

        for (Aerial aerial : resourceManager.getAerials()) {
            if (aerial.isActive()) {
                draw(aerial.getSprite(), aerial.getRigidBody().getModelMatrix());
            }
        }

        for (Minion minion : resourceManager.getMinions()) {
            if (minion.isActive()) {
                draw(minion.getSprite(), minion.getRigidBody().getModelMatrix());
            }
        }

        for (Roller roller : resourceManager.getRollers()) {
            if (roller.isActive()) {
                draw(roller.getSprite(), roller.getRigidBody().getModelMatrix());
            }
        }

        ShadowLord shadowLord = resourceManager.getShadowLord();

        if (shadowLord.isActive()) {
            draw(shadowLord.getSprite(), shadowLord.getRigidBody().getModelMatrix());
        }

        Player player = resourceManager.getPlayer();

        draw(player.getSprite(), player.getRigidBody().getModelMatrix());

        draw(resourceManager.getForeground().getSprite());

        for (Sprite sprite : resourceManager.getScore().getSprites()) {
            draw(sprite, sprite.getModelMatrix());
        }

        for (Impact impact : resourceManager.getImpacts()) {
            if (impact.getEntityState() == EntityState.ENABLED) {
                draw(impact.getSprite(), impact.getPosition().getModelMatrix());
            }
        }
    }

    private void draw(Sprite sprite) {
        draw(sprite, mIdentityMatrix);
    }

    private void draw(Sprite sprite, float[] mModelMatrix) {

        GLES20.glUseProgram(sprite.getShaderProgram());

        GLES20.glEnableVertexAttribArray(sprite.getVertexPosition());
        GLES20.glVertexAttribPointer(sprite.getVertexPosition(), 2, GLES20.GL_FLOAT, false, 2 *
                4, sprite.getVertexBuffer());

        GLES20.glEnableVertexAttribArray(sprite.getTexturePosition());
        GLES20.glVertexAttribPointer(sprite.getTexturePosition(), 2, GLES20.GL_FLOAT, false, 2 *
                4, sprite.getTextureBuffer());

        Matrix.multiplyMM(mMVPMatrix, 0, mVPMatrix, 0, mModelMatrix, 0);

        GLES20.glUniformMatrix4fv(sprite.getUniformMVPMatrixPosition(), 1, false, mMVPMatrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, sprite.getTexture());

        TextureTransition textureTransition = sprite.getTextureTransition();

        if (textureTransition != null) {
            textureTransition.addTime(deltaTime);
            GLES20.glUniform1f(textureTransition.getTimePosition(), textureTransition.getTime());
            GLES20.glUniform1i(textureTransition.getTexturePosition(), 1);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureTransition.getTexture());
        }

        ColorTransition colorTransition = sprite.getColorTransition();

        if (colorTransition != null) {
            colorTransition.addTime(deltaTime);
            GLES20.glUniform1f(colorTransition.getTimePosition(), colorTransition.getTime());
        }

        Scroll scroll = sprite.getScroll();

        if (scroll != null) {
            scroll.setDisplacement(cameraPosition);
            GLES20.glUniform1f(scroll.getScrollPosition(), scroll.getDisplacement());
        }

        Animation animation = sprite.getAnimation();

        if (animation != null) {
            sprite.getAnimation().update(deltaTime);
        }

        Stats stats = sprite.getStats();

        if (stats != null) {
            GLES20.glUniform1f(sprite.getDamagePosition(), stats.getDamageLevel() / 4);
        }

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, sprite.getDrawOrder().length,
                GLES20.GL_UNSIGNED_SHORT, sprite.getOrderBuffer());

    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
