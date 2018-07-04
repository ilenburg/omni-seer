package com.lonewolf.lagom.entities.spell;

import com.lonewolf.lagom.entities.Impact;
import com.lonewolf.lagom.entities.base.PhysicalEntity;

/**
 * Created by Ian Ilenburg on 27/02/2018.
 */

public class Spell extends PhysicalEntity {

    private final Impact impact;
    private final int damage;

    protected Spell(Impact impact, int damage) {
        this.impact = impact;
        this.damage = damage;
    }

    public Impact getImpact() {
        return impact;
    }

    public int getDamage() {
        return damage;
    }
}
