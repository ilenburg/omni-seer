package com.lonewolf.lagom.modules.effects;

import java.nio.FloatBuffer;

/**
 * Created by Ian on 18/02/2018.
 */

public class TextureMapping {

    private FloatBuffer textureBuffer;
    private float[][] textureFramesCoordinates;

    public TextureMapping() {
    }

    public TextureMapping(float[][] textureFramesCoordinates) {
        this.textureFramesCoordinates = textureFramesCoordinates;
    }

    public void setTextureBuffer(FloatBuffer textureBuffer) {
        this.textureBuffer = textureBuffer;
    }

    public void setTexturePosition(int index) {
        if (textureFramesCoordinates != null && index < textureFramesCoordinates.length) {
            setCurrentTextureCoordinates(textureFramesCoordinates[index]);
        }
    }

    public void setCurrentTextureCoordinates(float[] textureCoordinates) {
        this.textureBuffer.clear();
        this.textureBuffer.put(textureCoordinates);
        this.textureBuffer.rewind();
    }
}
