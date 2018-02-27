package com.lonewolf.lagom.physics.Handlers;

import com.lonewolf.lagom.entities.AirBomb;
import com.lonewolf.lagom.entities.Impact;
import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.Roller;
import com.lonewolf.lagom.entities.ShadowLord;
import com.lonewolf.lagom.entities.Spell;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.utils.PhysicsUtils;

import static com.lonewolf.lagom.utils.GameConstants.GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_DAMAGE;
import static com.lonewolf.lagom.utils.PhysicsUtils.CollisionResponse;
import static com.lonewolf.lagom.utils.PhysicsUtils.updateRigidBody;

/**
 * Created by Ian on 25/02/2018.
 */

public class SpellHandler {

    private final ResourceManager resourceManager;

    public SpellHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void update(float deltaTime) {

        RigidBody spellRigidBody;

        for (MegaSpell megaSpell : resourceManager.getMegaSpells()) {
            if (megaSpell.isActive()) {
                spellRigidBody = megaSpell.getRigidBody();
                if (spellRigidBody.getPosition().getY() <= GROUND_POSITION - 0.06f) {
                    megaSpell.setActive(false);
                }

                updateRigidBody(spellRigidBody, deltaTime);

                if (!spellRigidBody.getPosition().isBounded()) {
                    megaSpell.setActive(false);
                }
            }
        }

        for (Spell spell : resourceManager.getActiveSpells()) {
            if (spell.isActive()) {
                spellRigidBody = spell.getRigidBody();

                ShadowLord shadowLord = resourceManager.getShadowLord();

                if (shadowLord.isActive()) {
                    if (PhysicsUtils.Collide(spellRigidBody, shadowLord.getRigidBody())) {
                        spell.setActive(false);
                    }
                }

                for (Minion minion : resourceManager.getMinions()) {
                    if (minion.isActive() && !minion.getStats().isDead()) {
                        if (PhysicsUtils.Collide(spellRigidBody, minion.getRigidBody())) {
                            activateImpact(spell.getImpact(), spellRigidBody);
                            CollisionResponse(spellRigidBody, minion.getRigidBody());
                            spell.setActive(false);
                            minion.setAggressive(true);
                            minion.getStats().dealDamage(SPELL_DAMAGE);
                        }
                    }
                }

                for (Roller roller : resourceManager.getRollers()) {
                    if (PhysicsUtils.Collide(spellRigidBody, roller.getRigidBody())) {
                        activateImpact(spell.getImpact(), spellRigidBody);
                        roller.getStats().dealDamage(SPELL_DAMAGE);
                        spell.setActive(false);
                    }
                }

                for (AirBomb airBomb : resourceManager.getAirBombs()) {
                    if (PhysicsUtils.Collide(spellRigidBody, airBomb.getRigidBody())) {
                        activateImpact(spell.getImpact(), spellRigidBody);
                        airBomb.getStats().dealDamage(SPELL_DAMAGE);
                        spell.setActive(false);
                    }
                }

                if (spellRigidBody.getPosition().getY() <= GROUND_POSITION - 0.06f ||
                        !spellRigidBody.getPosition().isBounded()) {
                    activateImpact(spell.getImpact(), spellRigidBody);
                    spell.setActive(false);
                } else {
                    updateRigidBody(spellRigidBody, deltaTime);
                }
            }
        }
    }

    private void activateImpact(Impact spellImpact, RigidBody rigidBody) {
        spellImpact.setPosition(rigidBody.getPosition().copy());
        spellImpact.trigger();
    }
}
