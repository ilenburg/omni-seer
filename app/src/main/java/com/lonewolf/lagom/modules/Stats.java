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

    public int getHp() {
        return hp;
    }

    public float getDamageLevel() {
        return Math.min(1 - (float) hp / (float) maxHp, 1);
    }

    public void dealDamage(int damage) {
        this.hp -= damage;
    }

}
