package com.lonewolf.lagom.graphics;

import com.lonewolf.lagom.physics.RigidBody;

import java.nio.FloatBuffer;

/**
 * Created by Ian on 10/02/2017.
 */

public class Animation {

    private final float[][] textureFramesCoordinates;

    private FloatBuffer textureBuffer;
    private final float cicleDuration;
    private final float cicleStepDuration;

    private float cicleStep;

    private final RigidBody rigidBody;
    private boolean movementBased;

    public Animation(float[][] textureFramesCoordinates) {
        this(textureFramesCoordinates, 0);
    }

    public Animation(float[][] textureFramesCoordinates, float cicleDuration) {
        this(textureFramesCoordinates, cicleDuration, null);
    }

    public Animation(float[][] textureFramesCoordinates, float cicleDuration, RigidBody rigidBody) {
        this.textureFramesCoordinates = textureFramesCoordinates;
        this.cicleDuration = cicleDuration;
        this.cicleStepDuration = cicleDuration / textureFramesCoordinates.length;
        this.cicleStep = 0.0f;
        this.rigidBody = rigidBody;

        if (this.rigidBody != null) {
            this.movementBased = true;
        } else {
            this.movementBased = false;
        }
    }

    public void setTextureBuffer(FloatBuffer textureBuffer) {
        this.textureBuffer = textureBuffer;
    }

    public void update(float delta) {
        this.cicleStep += movementBased ? Math.abs(rigidBody.getVelocity().getLength()) * delta : delta;

        if (cicleStep >= cicleDuration) {
            cicleStep = cicleStep - cicleDuration;
        }

        int frame = (int) Math.floor(cicleStep / cicleStepDuration);

        if (frame >= textureFramesCoordinates.length) {
            frame = 0;
        }

        this.textureBuffer.clear();
        this.textureBuffer.put(textureFramesCoordinates[frame]);
        this.textureBuffer.rewind();
    }

}
