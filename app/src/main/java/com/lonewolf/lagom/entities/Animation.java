package com.lonewolf.lagom.entities;

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

    public void setTextureBuffer(FloatBuffer textureBuffer) {
        this.textureBuffer = textureBuffer;
    }

    public Animation(float[][] textureFramesCoordinates) {
        this(textureFramesCoordinates, 0);
    }

    public Animation(float[][] textureFramesCoordinates, float cicleDuration) {
        this.textureFramesCoordinates = textureFramesCoordinates;
        this.cicleDuration = cicleDuration;
        this.cicleStepDuration = cicleDuration / textureFramesCoordinates.length;
        this.cicleStep = 0.0f;
    }

    public void update(float delta) {
        this.cicleStep += delta;

        if (cicleStep >= cicleDuration) {
            cicleStep = cicleStep - cicleDuration;
        }

        int frame = (int) Math.floor(cicleStep / cicleStepDuration);

        this.textureBuffer.clear();
        this.textureBuffer.put(textureFramesCoordinates[frame]);
        this.textureBuffer.rewind();
    }

}
