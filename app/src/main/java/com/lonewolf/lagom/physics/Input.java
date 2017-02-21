package com.lonewolf.lagom.physics;

import android.util.Log;

/**
 * Created by Ian on 12/02/2017.
 */

public class Input {

    private float jumpPower;
    private Vector2 spellTarget;
    private boolean grounded;
    private boolean megaSpell;

    private float screenWidth;
    private float screenHeight;
    private float ratio;

    public Input() {
        this.jumpPower = 0.0f;
        this.grounded = true;
        this.spellTarget = new Vector2(0.0f, 0.0f);
        this.screenHeight = 0.0f;
        this.screenWidth = 0.0f;
    }

    public Vector2 getSpellTarget() {
        return spellTarget;
    }

    public void setSpellTarget(float x, float y) {
        this.spellTarget.setX(getNormalizedX(x));
        this.spellTarget.setY(getNormalizedY(y));
        Log.v("SpellTarget", Float.toString(spellTarget.getX()) + "||" + Float.toString(spellTarget.getY()));
    }

    public void setScreenSize(float screenWidth, float screenHeight, float ratio) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.ratio = ratio;
    }

    public float getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(float jumpPower) {
        this.jumpPower = jumpPower;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    private float getNormalizedX(float x) {
        return ((x / screenWidth) * 2 - 1) * ratio;
    }

    private float getNormalizedY(float y) {
        return -((y / screenHeight) * 2 - 1);
    }

    public boolean isMegaSpell() {
        return megaSpell;
    }

    public void setMegaSpell(boolean megaSpell) {
        this.megaSpell = megaSpell;
    }
}
