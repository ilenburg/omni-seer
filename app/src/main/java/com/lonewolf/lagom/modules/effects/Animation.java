package com.lonewolf.lagom.modules.effects;

import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;

import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Created by Ian on 10/02/2017.
 */

public class Animation {

    private static Random random = new Random();

    private final float[][] textureFramesCoordinates;
    private final float[] jumpTextureCoordinates;

    private FloatBuffer textureBuffer;
    private final float cycleDuration;
    private final float cycleStepDuration;

    private float cycleStep;

    private final RigidBody rigidBody;
    private final Input input;
    private final boolean movementBased;
    private final boolean inputAffected;

    public Animation(float[][] textureFramesCoordinates, float cycleDuration) {
        this(textureFramesCoordinates, cycleDuration, null, null, null);
    }

    public Animation(float[][] textureFramesCoordinates, float cycleDuration, RigidBody rigidBody) {
        this(textureFramesCoordinates, cycleDuration, rigidBody, null, null);
    }

    public Animation(float[][] textureFramesCoordinates, float cycleDuration, RigidBody rigidBody, float[] jumpTextureCoordinates, Input input) {
        this.textureFramesCoordinates = textureFramesCoordinates;
        this.cycleDuration = cycleDuration;
        this.cycleStepDuration = cycleDuration / textureFramesCoordinates.length;
        this.cycleStep = random.nextFloat() * 3;
        this.rigidBody = rigidBody;
        this.input = input;
        this.jumpTextureCoordinates = jumpTextureCoordinates;

        if (this.rigidBody != null) {
            this.movementBased = true;
        } else {
            this.movementBased = false;
        }

        if (this.input != null) {
            this.inputAffected = true;
        } else {
            this.inputAffected = false;
        }
    }

    public void update(float deltaTime) {
        if (inputAffected && !input.isGrounded()) {
            setCurrentTextureCoordinates(jumpTextureCoordinates);
        } else {
            this.cycleStep += movementBased ? Math.abs(rigidBody.getVelocity().getLength()) * deltaTime : deltaTime;

            if (cycleStep >= cycleDuration) {
                cycleStep = cycleStep - cycleDuration;
            }

            int frame = (int) Math.floor(cycleStep / cycleStepDuration);

            if (frame >= textureFramesCoordinates.length) {
                frame = 0;
            }

            setCurrentTextureCoordinates(textureFramesCoordinates[frame]);
        }
    }

    public void setTextureBuffer(FloatBuffer textureBuffer) {
        this.textureBuffer = textureBuffer;
    }

    private void setCurrentTextureCoordinates(float[] textureCoordinates) {
        this.textureBuffer.clear();
        this.textureBuffer.put(textureCoordinates);
        this.textureBuffer.rewind();
    }

}
