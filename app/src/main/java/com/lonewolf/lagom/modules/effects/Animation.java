package com.lonewolf.lagom.modules.effects;

import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.states.StateReference;

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

    private final StateReference stateReference;
    private final RigidBody rigidBody;
    private final Input input;

    private final boolean movementBased;
    private final boolean inputAffected;
    private final boolean triggered;

    private Animation(float[][] textureFramesCoordinates, float cycleDuration, RigidBody
            rigidBody, float[] jumpTextureCoordinates, Input input, StateReference
                              stateReference) {
        this.textureFramesCoordinates = textureFramesCoordinates;
        this.cycleDuration = cycleDuration;
        this.cycleStepDuration = cycleDuration / textureFramesCoordinates.length;
        this.rigidBody = rigidBody;
        this.input = input;
        this.jumpTextureCoordinates = jumpTextureCoordinates;
        this.stateReference = stateReference;

        this.movementBased = this.rigidBody != null;
        this.inputAffected = this.input != null;
        this.triggered = this.stateReference != null;

        this.cycleStep = triggered ? 0.0f : random.nextFloat() * 3;
    }

    public void update(float deltaTime) {
        if (inputAffected && !input.isGrounded() && input.isActive()) {
            setCurrentTextureCoordinates(jumpTextureCoordinates);
        } else if (!inputAffected || input.isActive()) {
            this.cycleStep += movementBased ? Math.abs(rigidBody.getVelocity().getLength()) *
                    deltaTime : deltaTime;

            if (cycleStep >= cycleDuration) {
                cycleStep = cycleStep - cycleDuration;
                if (triggered) {
                    stateReference.setActive(false);
                    return;
                }
            }

            int frame = (int) Math.floor(cycleStep / cycleStepDuration);

            if (frame >= textureFramesCoordinates.length || frame < 0) {
                frame = 0;
            }
            setCurrentTextureCoordinates(textureFramesCoordinates[frame]);
        } else {
            setCurrentTextureCoordinates(textureFramesCoordinates[0]);
        }
    }

    public void trigger() {
        this.cycleStep = 0;
        if (triggered) {
            stateReference.setActive(true);
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

    public static class Builder {

        private final float[][] textureFramesCoordinates;
        private final float cycleDuration;

        private RigidBody rigidBody;
        private StateReference stateReference;
        private float[] jumpTextureCoordinates;
        private Input input;

        public Builder(float[][] textureFramesCoordinates, float cycleDuration) {
            this.textureFramesCoordinates = textureFramesCoordinates;
            this.cycleDuration = cycleDuration;
        }

        public Builder withRigidBody(RigidBody rigidBody) {
            this.rigidBody = rigidBody;
            return this;
        }

        public Builder withJumpTextureCoordinates(float[] jumpTextureCoordinates) {
            this.jumpTextureCoordinates = jumpTextureCoordinates;
            return this;
        }

        public Builder withInput(Input input) {
            this.input = input;
            return this;
        }

        public Builder withEntityStateReference(StateReference stateReference) {
            this.stateReference = stateReference;
            return this;
        }

        public Animation build() {
            return new Animation(textureFramesCoordinates, cycleDuration, rigidBody,
                    jumpTextureCoordinates, input, stateReference);
        }
    }
}
