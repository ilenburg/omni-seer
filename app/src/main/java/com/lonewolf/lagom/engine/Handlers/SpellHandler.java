package com.lonewolf.lagom.engine.Handlers;

import com.lonewolf.lagom.entities.Impact;
import com.lonewolf.lagom.entities.enemies.Aerial;
import com.lonewolf.lagom.entities.enemies.Minion;
import com.lonewolf.lagom.entities.enemies.Roller;
import com.lonewolf.lagom.entities.enemies.ShadowLord;
import com.lonewolf.lagom.entities.spell.MegaSpell;
import com.lonewolf.lagom.entities.spell.Spell;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.utils.GameConstants;
import com.lonewolf.lagom.utils.PhysicsUtils;

import static com.lonewolf.lagom.utils.GameConstants.PLAYER_GROUND_POSITION;
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

        for (Spell megaSpell : resourceManager.getMegaSpells()) {
            if (megaSpell.isActive()) {
                RigidBody spellRigidBody = megaSpell.getRigidBody();

                checkImpactShadowLord(megaSpell);

                checkImpactMinions(megaSpell);

                checkImpactRoller(megaSpell);

                checkImpactAirBomb(megaSpell);

                if (spellRigidBody.getPosition().getY() <= PLAYER_GROUND_POSITION - 0.06f) {
                    megaSpell.setActive(false);
                }

                if (!spellRigidBody.getPosition().isBounded()) {
                    megaSpell.setActive(false);
                }

                updateRigidBody(spellRigidBody, deltaTime);
            }
        }

        for (Spell minorSpell : resourceManager.getMinorSpells()) {
            if (minorSpell.isActive()) {

                checkImpactShadowLord(minorSpell);

                checkImpactMinions(minorSpell);

                checkImpactRoller(minorSpell);

                checkImpactAirBomb(minorSpell);

                checkImpactGround(minorSpell);

                if (!minorSpell.getRigidBody().getPosition().isBounded()) {
                    minorSpell.setActive(false);
                }

                updateRigidBody(minorSpell.getRigidBody(), deltaTime);
            }
        }
    }

    private void checkImpactShadowLord(Spell spell) {
        RigidBody spellRigidBody = spell.getRigidBody();
        ShadowLord shadowLord = resourceManager.getShadowLord();
        if (shadowLord.isActive()) {
            if (PhysicsUtils.Collide(spellRigidBody, shadowLord.getRigidBody())) {
                activateImpact(spell.getImpact(), spellRigidBody);
                if (!shadowLord.reachedLimit()) {
                    if (spell instanceof MegaSpell) {
                        shadowLord.getRigidBody().setVelocityX(0.1f);
                    } else {
                        shadowLord.getRigidBody().displaceX(GameConstants.SHADOW_PUSH);
                    }
                }
                spell.setActive(false);
            }
        }
    }

    private void checkImpactMinions(Spell spell) {
        RigidBody spellRigidBody = spell.getRigidBody();
        for (Minion minion : resourceManager.getMinions()) {
            if (minion.isActive() && !minion.getStats().isDead()) {
                if (PhysicsUtils.Collide(spellRigidBody, minion.getRigidBody())) {
                    activateImpact(spell.getImpact(), spellRigidBody);
                    CollisionResponse(spellRigidBody, minion.getRigidBody());
                    spell.setActive(false);
                    minion.setAggressive(true);
                    minion.getStats().dealDamage(spell.getDamage());
                    resourceManager.playDamage();
                }
            }
        }
    }

    private void checkImpactRoller(Spell spell) {
        RigidBody spellRigidBody = spell.getRigidBody();
        for (Roller roller : resourceManager.getRollers()) {
            if (roller.isActive() && !roller.getStats().isDead()) {
                if (PhysicsUtils.Collide(spellRigidBody, roller.getRigidBody())) {
                    activateImpact(spell.getImpact(), spellRigidBody);
                    roller.getStats().dealDamage(spell.getDamage());
                    spell.setActive(false);
                }
            }
        }
    }

    private void checkImpactAirBomb(Spell spell) {
        RigidBody spellRigidBody = spell.getRigidBody();
        for (Aerial aerial : resourceManager.getAerials()) {
            if (aerial.isActive() && !aerial.getStats().isDead()) {
                if (PhysicsUtils.Collide(spellRigidBody, aerial.getRigidBody())) {
                    activateImpact(spell.getImpact(), spellRigidBody);
                    aerial.getStats().dealDamage(spell.getDamage());
                    spell.setActive(false);
                }
            }
        }
    }

    private void checkImpactGround(Spell spell) {
        RigidBody spellRigidBody = spell.getRigidBody();
        if (spellRigidBody.getPosition().getY() <= PLAYER_GROUND_POSITION - 0.06f) {
            activateImpact(spell.getImpact(), spellRigidBody);
            spell.setActive(false);
        }
    }

    private void activateImpact(Impact spellImpact, RigidBody rigidBody) {
        spellImpact.setPosition(rigidBody.getPosition().copy());
        spellImpact.trigger();
        if (spellImpact.isMega()) {
            resourceManager.playBigHit();
        } else {
            resourceManager.playHit();
        }
    }
}
