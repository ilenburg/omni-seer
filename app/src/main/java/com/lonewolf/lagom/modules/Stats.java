package com.lonewolf.lagom.modules;

import com.lonewolf.lagom.modules.effects.TextureMapping;

/**
 * Created by Ian Ilenburg on 03/09/2017.
 */

public class Stats {

    private final int maxHp;
    private final TextureMapping textureMapping;
    private boolean killed;
    private int hp;

    public Stats(int maxHp) {
        this(maxHp, null);
    }

    public Stats(int maxHp, TextureMapping textureMapping) {
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.textureMapping = textureMapping;
        this.killed = false;
    }

    public boolean isAlive() {
        return !isDead();
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public boolean wasKilled() {
        boolean justKilled = killed;
        this.killed = false;
        return justKilled;
    }

    public float getDamageLevel() {
        return Math.min(1 - (float) hp / (float) maxHp, 1);
    }

    public void dealDamage(int damage) {
            this.hp -= damage;
            if (textureMapping != null) {
                if (isDead()) {
                    textureMapping.setTexturePosition(2);
                } else if (this.hp <= maxHp / 2) {
                    textureMapping.setTexturePosition(1);
                }
            }
            if (isDead()) {
                this.killed = true;
            }
    }

    public void restore() {
        this.hp = maxHp;
        if (textureMapping != null) {
            textureMapping.setTexturePosition(0);
        }
    }

}
