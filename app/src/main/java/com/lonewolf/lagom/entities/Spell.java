package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.modules.RigidBody;

/**
 * Created by Ian on 27/02/2018.
 */

public class Spell {

    private final Impact impact;
    private final int damage;
    protected boolean active;

    protected RigidBody rigidBody;

    protected Spell(Impact impact, int damage) {
        this.impact = impact;
        this.damage = damage;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Impact getImpact() {
        return impact;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDamage() {
        return damage;
    }
}
