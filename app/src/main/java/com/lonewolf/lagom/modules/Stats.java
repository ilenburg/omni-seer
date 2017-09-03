package com.lonewolf.lagom.modules;

/**
 * Created by Ian on 03/09/2017.
 */

public class Stats {

    private final int maxHp;
    private int hp;

    public Stats(int maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public float getDamageLevel() {
        return Math.min(1 - (float) hp / (float) maxHp, 1);
    }

    public void dealDamage(int damage) {
        this.hp -= damage;
    }

}
