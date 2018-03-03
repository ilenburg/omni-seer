package com.lonewolf.lagom.physics.Handlers;

import com.lonewolf.lagom.entities.Aerial;
import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.MinorSpell;
import com.lonewolf.lagom.entities.Player;
import com.lonewolf.lagom.entities.Roller;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.utils.PhysicsUtils;

import static com.lonewolf.lagom.utils.GameConstants.GRAVITY_ACCELERATION;
import static com.lonewolf.lagom.utils.GameConstants.GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_BASE_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_DISPLACEMENT;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_FORWARD;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_GHOST;
import static com.lonewolf.lagom.utils.GameConstants.ZERO;
import static com.lonewolf.lagom.utils.PhysicsUtils.updatePlayerPosition;

/**
 * Created by Ian on 18/02/2018.
 */

public class PlayerHandler {

    private final ResourceManager resourceManager;
    private float cameraPosition;

    public PlayerHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.cameraPosition = 0.0f;
    }

    public float getCameraPosition() {
        return cameraPosition;
    }

    public void update(float deltaTime) {

        Player player = resourceManager.getPlayer();

        RigidBody playerRigidBody = player.getRigidBody();
        Input playerInput = player.getInput();

        if (!playerInput.isInvulnerable()) {

            for (Minion minion : resourceManager.getMinions()) {
                if (minion.isActive() && !minion.getStats().isDead()) {
                    if (PhysicsUtils.Collide(playerRigidBody, minion.getRigidBody(), false)) {
                        player.getStats().dealDamage(1);
                    }
                }
            }

            for (Roller roller : resourceManager.getRollers()) {
                if (roller.isActive() && !roller.getStats().isDead()) {
                    if (PhysicsUtils.Collide(playerRigidBody, roller.getRigidBody(), false)) {
                        player.getStats().dealDamage(1);
                    }
                }
            }

            for (Aerial aerial : resourceManager.getAerials()) {
                if (aerial.isActive() && !aerial.getStats().isDead()) {
                    if (PhysicsUtils.Collide(playerRigidBody, aerial.getRigidBody(), false)) {
                        player.getStats().dealDamage(1);
                    }
                }
            }

            if (player.isDead()) {
                player.getInput().setActive(false);
                playerInput.setInvulnerable(true);
                playerRigidBody.stop();
                playerRigidBody.setVelocity(VECTOR_GHOST);
            }

        }

        if (player.isActive()) {

            boolean playerAlive = player.isAlive();

            if (playerAlive) {
                playerInput.update(deltaTime);

                if (playerInput.isMegaSpell()) {
                    for (MegaSpell megaSpell : resourceManager.getMegaSpells()) {
                        if (!megaSpell.isActive()) {
                            Vector2 startingPosition = playerRigidBody.getPosition();
                            megaSpell.getRigidBody().setPosition(startingPosition.getX() + 0.1f,
                                    startingPosition.getY());
                            megaSpell.getRigidBody().setVelocity(SPELL_BASE_VELOCITY);
                            megaSpell.setActive(true);
                            playerInput.setMegaSpell(false);
                            break;
                        }
                    }
                }

                if (!playerInput.getSpellTarget().isZero()) {
                    Vector2 startingVelocity = playerInput.getSpellTarget().copy();
                    Vector2.sub(startingVelocity, startingVelocity, playerRigidBody.getPosition());
                    Vector2.normalize(startingVelocity, startingVelocity);
                    for (MinorSpell minorSpell : resourceManager.getMinorSpells()) {
                        if (!minorSpell.isActive()) {
                            Vector2 spellPosition = playerRigidBody.getPosition().copy();
                            Vector2.add(spellPosition, spellPosition, SPELL_DISPLACEMENT);
                            minorSpell.getRigidBody().setPosition(spellPosition);
                            Vector2.multiply(startingVelocity, startingVelocity, 2.0f);
                            minorSpell.getRigidBody().setVelocity(startingVelocity);
                            float angle = PhysicsUtils.CalcAngle(startingVelocity, VECTOR_FORWARD);
                            minorSpell.getRigidBody().setAngle(angle);
                            minorSpell.setActive(true);
                            break;
                        }
                    }
                    playerInput.getSpellTarget().setZero();
                }

                float playerJumpPower = playerInput.getJumpPower();

                if (playerJumpPower != ZERO) {
                    playerRigidBody.setVelocityY(playerJumpPower);
                    playerInput.setJumpPower(ZERO);
                }
            }

            updatePlayerPosition(playerRigidBody, deltaTime);

            cameraPosition += playerRigidBody.getVelocity().getX() * deltaTime;

            cameraPosition = cameraPosition % 1000;

            if (playerAlive) {
                if (playerRigidBody.getPosition().getY() > GROUND_POSITION) {
                    playerRigidBody.setAccelerationY(playerInput.isInvulnerable() ?
                            GRAVITY_ACCELERATION / 2.0f : GRAVITY_ACCELERATION);
                } else {
                    playerRigidBody.setAccelerationY(ZERO);
                    playerRigidBody.setVelocityY(ZERO);
                    playerRigidBody.setPositionY(GROUND_POSITION);
                    playerInput.setGrounded(true);
                }
            }

        }
    }

}
