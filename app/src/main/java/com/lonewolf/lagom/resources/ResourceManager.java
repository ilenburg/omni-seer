package com.lonewolf.lagom.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.lonewolf.lagom.R;
import com.lonewolf.lagom.entities.Panorama;
import com.lonewolf.lagom.entities.Player;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;
import static com.lonewolf.lagom.graphics.GameRenderer.checkGlError;

/**
 * Created by Ian on 28/01/2017.
 */

public class ResourceManager {

    private final Context context;

    private int[] shaderPrograms = new int[6];
    private int[] textures = new int[6];

    public Player player;
    public Panorama background;
    public Panorama foreground;
    public Panorama panorama;
    public Panorama panoramaFar;

    public ResourceManager(Context context) {

        this.context = context;

    }

    public void loadResources() {
        initShaders();
        initTextures();
        initEntities();
    }

    private void initShaders() {

        String vertexShader = null;
        String fragmentShader = null;
        String fragmentScrollShader = null;

        try {
            vertexShader = getShaderCode(R.raw.base_vert);
            fragmentShader = getShaderCode(R.raw.base_frag);
            fragmentScrollShader = getShaderCode(R.raw.scroll_frag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        shaderPrograms[0] = generateShaderProgram(vertexShader, fragmentShader);
        shaderPrograms[1] = generateShaderProgram(vertexShader, fragmentScrollShader);
    }

    private void initTextures() {

        loadTexture(R.drawable.final_dude, 0, false);
        loadTexture(R.drawable.background, 1, true);
        loadTexture(R.drawable.panorama, 2, true);
        loadTexture(R.drawable.panorama_far, 3, true);
        loadTexture(R.drawable.foreground, 4, true);
    }

    private void initEntities() {

        this.background = new Panorama(shaderPrograms[1], textures[1], 0.25f);
        this.panoramaFar = new Panorama(shaderPrograms[1], textures[3], 0.5f);
        this.panorama = new Panorama(shaderPrograms[1], textures[2], 0.75f);
        this.player = new Player(shaderPrograms[0], textures[0]);
        this.foreground = new Panorama(shaderPrograms[1], textures[4], 1.0f);
    }

    private String getShaderCode(int resourceId) throws IOException {

        InputStream inputStream = context.getResources().openRawResource(resourceId);
        String shaderCode = IOUtils.toString(inputStream);
        inputStream.close();

        return shaderCode;
    }

    private void loadTexture(int textureImage, int texturePosition, boolean tileAble) {

        InputStream imageStream = context.getResources().openRawResource(textureImage);

        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

        try {
            imageStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        GLES20.glGenTextures(1, textures, texturePosition);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[texturePosition]);

        float repeatParam = tileAble ? GLES20.GL_REPEAT : GLES20.GL_CLAMP_TO_EDGE;

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, repeatParam);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, repeatParam);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        checkGlError("LoadTexture");
    }

    private static int generateShaderProgram(String vertexShaderCode, String fragmentShaderCode) {

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        int shaderProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);

        GLES20.glLinkProgram(shaderProgram);

        checkGlError("GenerateShaderProgram");

        return shaderProgram;
    }

    private static int loadShader(int type, String shaderCode) {

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
