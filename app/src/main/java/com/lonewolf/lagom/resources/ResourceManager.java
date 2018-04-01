package com.lonewolf.lagom.resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.lonewolf.lagom.R;
import com.lonewolf.lagom.entities.Capsule;
import com.lonewolf.lagom.entities.Impact;
import com.lonewolf.lagom.entities.Player;
import com.lonewolf.lagom.entities.enemies.Aerial;
import com.lonewolf.lagom.entities.enemies.Minion;
import com.lonewolf.lagom.entities.enemies.Roller;
import com.lonewolf.lagom.entities.enemies.ShadowLord;
import com.lonewolf.lagom.entities.spell.MegaSpell;
import com.lonewolf.lagom.entities.spell.MinorSpell;
import com.lonewolf.lagom.external.PerfectLoopMediaPlayer;
import com.lonewolf.lagom.hud.ManaGauge;
import com.lonewolf.lagom.hud.Score;
import com.lonewolf.lagom.hud.GameOverBoard;
import com.lonewolf.lagom.hud.ScoreBoard;
import com.lonewolf.lagom.scenario.Background;
import com.lonewolf.lagom.scenario.Panorama;
import com.lonewolf.lagom.utils.GameConstants;

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

    private final SharedPreferences sharedPreferences;

    private SoundPool soundPool;

    private int[] shaderPrograms = new int[8];
    private int[] textures = new int[20];
    private int[] sounds = new int[8];

    private final MinorSpell[] minorSpells = new MinorSpell[30];

    private final MegaSpell[] megaSpells = new MegaSpell[6];

    private final Minion[] minions = new Minion[60];

    private final Aerial[] aerials = new Aerial[6];

    private final Roller[] rollers = new Roller[6];

    private final Impact[] impacts = new Impact[minorSpells.length + megaSpells.length];

    private final Capsule[] capsules = new Capsule[6];

    private PerfectLoopMediaPlayer musicPlayer;

    private Player player;
    private ShadowLord shadowLord;
    private Background background;
    private Panorama foreground;
    private Panorama panorama;
    private Panorama panoramaFar;
    private Score score;
    private ManaGauge manaGauge;
    private GameOverBoard gameOverBoard;
    private ScoreBoard scoreBoard;

    public ResourceManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(GameConstants.OMNI_PREFERENCE_KEY,
                Context.MODE_PRIVATE);
        this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }

    public Context getContext() {
        return context;
    }

    public MegaSpell[] getMegaSpells() {
        return megaSpells;
    }

    public Player getPlayer() {
        return player;
    }

    public ShadowLord getShadowLord() {
        return shadowLord;
    }

    public Background getBackground() {
        return background;
    }

    public Panorama getForeground() {
        return foreground;
    }

    public Panorama getPanorama() {
        return panorama;
    }

    public Panorama getPanoramaFar() {
        return panoramaFar;
    }

    public MinorSpell[] getMinorSpells() {
        return minorSpells;
    }

    public Minion[] getMinions() {
        return minions;
    }

    public Aerial[] getAerials() {
        return aerials;
    }

    public Roller[] getRollers() {
        return rollers;
    }

    public Score getScore() {
        return score;
    }

    public ManaGauge getManaGauge() {
        return manaGauge;
    }

    public GameOverBoard getGameOverBoard() {
        return gameOverBoard;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public Impact[] getImpacts() {
        return impacts;
    }

    public Capsule[] getCapsules() {
        return capsules;
    }

    public int getHighScore() {
        return this.sharedPreferences.getInt(GameConstants.HIGH_SCORE, 0);
    }

    public void saveHighScore(int score) {
        if (score > getHighScore()) {
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putInt(GameConstants.HIGH_SCORE, score);
            editor.commit();
        }
    }

    public void playDamage() {
        playSound(5, 0.1f);
    }

    public void playGhost() {
        playSound(4, 0.6f);
    }

    public void playBigHit() {
        playSound(3, 0.2f);
    }

    public void playHit() {
        playSound(3, 0.1f);
    }

    public void playGetItem() {
        playSound(2, 0.4f);
    }

    public void playMegaSpell() {
        playSound(1, 0.6f);
    }

    public void playMinorSpell() {
        playSound(1, 0.3f);
    }

    public void playBump() {
        playSound(6, 0.6f);
    }

    public void playCount() {
        playSound(7, 0.6f);
    }

    public void playJump(float jumpPower) {
        playSound(0, 0.2f, 1.5f - (jumpPower / 3.0f));
    }

    public void playMusic() {
        musicPlayer.start();
    }

    public void stopMusic() {
        musicPlayer.pause();
    }

    public void loadResources() {
        initShaders();
        initTextures();
        initSound();
        initEntities();
    }

    private void initEntities() {

        float scrollBase = 0.012f;

        this.background = new Background(shaderPrograms[2], textures[1], textures[5], scrollBase);
        this.panoramaFar = new Panorama(shaderPrograms[1], textures[3], scrollBase * 2);
        this.panorama = new Panorama(shaderPrograms[1], textures[2], scrollBase * 3);
        this.foreground = new Panorama(shaderPrograms[1], textures[4], scrollBase * 4);
        this.player = new Player(shaderPrograms[4], textures[0]);
        this.shadowLord = new ShadowLord(shaderPrograms[0], textures[7]);
        this.score = new Score(shaderPrograms[0], textures[12], -1.55f, 0.83f);
        this.manaGauge = new ManaGauge(shaderPrograms[0], textures[15]);
        this.gameOverBoard = new GameOverBoard(shaderPrograms[0], textures[16], player.getInput());

        Score highScore = new Score(shaderPrograms[0], textures[12], -0.15f, 0.23f);
        Score currentScore = new Score(shaderPrograms[0], textures[12], -0.15f, -0.26f);
        this.scoreBoard = new ScoreBoard(shaderPrograms[0], textures[17], player.getInput(),
                highScore, currentScore);

        int i;

        float spellImpactRadius = 0.1f;
        int size = minorSpells.length;
        for (i = 0; i < size; ++i) {
            impacts[i] = new Impact(shaderPrograms[0], textures[13], spellImpactRadius, false);
            minorSpells[i] = new MinorSpell(shaderPrograms[3], textures[6], impacts[i]);
        }

        float megaSpellImpactRadius = 0.2f;
        int spellsLastIndex = size;
        size = megaSpells.length;
        for (i = 0; i < size; ++i) {
            impacts[i + spellsLastIndex] = new Impact(shaderPrograms[0], textures[13],
                    megaSpellImpactRadius, true);
            megaSpells[i] = new MegaSpell(shaderPrograms[3], textures[6], impacts[i +
                    spellsLastIndex]);
        }

        size = minions.length;
        for (i = 0; i < size; ++i) {
            minions[i] = new Minion(shaderPrograms[6], textures[8]);
        }

        size = aerials.length;
        for (i = 0; i < size; ++i) {
            aerials[i] = new Aerial(shaderPrograms[0], textures[14]);
        }

        size = rollers.length;
        for (i = 0; i < size; ++i) {
            rollers[i] = new Roller(shaderPrograms[0], textures[14]);
        }

        size = capsules.length;
        for (i = 0; i < size; ++i) {
            capsules[i] = new Capsule(shaderPrograms[3], textures[15]);
        }
    }

    private void initSound() {
        musicPlayer = PerfectLoopMediaPlayer.create(context, R.raw.background);

        sounds[0] = soundPool.load(context, R.raw.jump, 1);
        sounds[1] = soundPool.load(context, R.raw.spell, 1);
        sounds[2] = soundPool.load(context, R.raw.get_item, 1);
        sounds[3] = soundPool.load(context, R.raw.hit, 1);
        sounds[4] = soundPool.load(context, R.raw.ghost, 1);
        sounds[5] = soundPool.load(context, R.raw.damage, 1);
        sounds[6] = soundPool.load(context, R.raw.bump, 1);
        sounds[7] = soundPool.load(context, R.raw.count, 1);
    }

    private void playSound(int index, float volume, float rate) {
        soundPool.play(sounds[index], volume, volume, 1, 0, rate);
    }

    private void playSound(int index, float volume) {
        soundPool.play(sounds[index], volume, volume, 1, 0, 1.0f);
    }

    private void initShaders() {

        String vertexShader = null;
        String fragmentShader = null;
        String fragmentScrollShader = null;
        String doubleTexFragmentScrollShader = null;
        String colorTransitionFragmentShader = null;
        String hueTransitionFragmentShader = null;
        String colorSwapFragmentShader = null;
        String damageFragmentShader = null;

        try {
            vertexShader = getShaderCode(R.raw.base_vert);
            fragmentShader = getShaderCode(R.raw.base_frag);
            fragmentScrollShader = getShaderCode(R.raw.scroll_frag);
            doubleTexFragmentScrollShader = getShaderCode(R.raw.tex_transition_scroll_frag);
            colorTransitionFragmentShader = getShaderCode(R.raw.color_transition_base_frag);
            hueTransitionFragmentShader = getShaderCode(R.raw.hue_transition_base_frag);
            colorSwapFragmentShader = getShaderCode(R.raw.color_swap_base_frag);
            damageFragmentShader = getShaderCode(R.raw.damage_frag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        shaderPrograms[0] = generateShaderProgram(vertexShader, fragmentShader);
        shaderPrograms[1] = generateShaderProgram(vertexShader, fragmentScrollShader);
        shaderPrograms[2] = generateShaderProgram(vertexShader, doubleTexFragmentScrollShader);
        shaderPrograms[3] = generateShaderProgram(vertexShader, colorTransitionFragmentShader);
        shaderPrograms[4] = generateShaderProgram(vertexShader, colorSwapFragmentShader);
        shaderPrograms[5] = generateShaderProgram(vertexShader, hueTransitionFragmentShader);
        shaderPrograms[6] = generateShaderProgram(vertexShader, damageFragmentShader);
    }

    private void initTextures() {
        loadTexture(R.drawable.player_sprite, 0, false);
        loadTexture(R.drawable.day, 1, true);
        loadTexture(R.drawable.panorama, 2, true);
        loadTexture(R.drawable.panorama_far, 3, true);
        loadTexture(R.drawable.foreground, 4, true);
        loadTexture(R.drawable.night, 5, true);
        loadTexture(R.drawable.fire_sprite, 6, false);
        loadTexture(R.drawable.shadow_lord, 7, false);
        loadTexture(R.drawable.minion, 8, false);
        loadTexture(R.drawable.bomb, 9, false);
        loadTexture(R.drawable.goo, 10, false);
        loadTexture(R.drawable.egg, 11, false);
        loadTexture(R.drawable.numbers, 12, false);
        loadTexture(R.drawable.impact2, 13, false);
        loadTexture(R.drawable.meat_blow, 14, false);
        loadTexture(R.drawable.glass_orb, 15, false);
        loadTexture(R.drawable.score_board, 16, false);
        loadTexture(R.drawable.blank_score_board, 17, false);
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

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_NEAREST);

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
