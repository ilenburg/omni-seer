package com.lonewolf.lagom.engine.Handlers;

import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.entities.Capsule;
import com.lonewolf.lagom.entities.Player;
import com.lonewolf.lagom.entities.enemies.Aerial;
import com.lonewolf.lagom.entities.enemies.Minion;
import com.lonewolf.lagom.entities.enemies.Roller;
import com.lonewolf.lagom.entities.spell.MegaSpell;
import com.lonewolf.lagom.entities.spell.MinorSpell;
import com.lonewolf.lagom.hud.ManaGauge;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.StateReference;
import com.lonewolf.lagom.utils.GameConstants;
import com.lonewolf.lagom.utils.PhysicsUtils;

import static com.lonewolf.lagom.utils.GameConstants.GHOST_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.GRAVITY_ACCELERATION;
import static com.lonewolf.lagom.utils.GameConstants.PLAYER_GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.PLAYER_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_BASE_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_DISPLACEMENT;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_FORWARD;
import static com.lonewolf.lagom.utils.GameConstants.ZERO;
import static com.lonewolf.lagom.utils.PhysicsUtils.updatePlayerPosition;

/**
 * Created by Ian Ilenburg on 18/02/2018.
 */

public class PlayerHandler {

    private final StateReference gameState;
    private final StateReference displayScoreState;
    private final ResourceManager resourceManager;
    private float cameraPosition;

    private static final Vector2 VECTOR_AUX_VELOCITY = new Vector2();
    private static final Vector2 VECTOR_AUX_POSITION = new Vector2();

    public PlayerHandler(ResourceManager resourceManager, StateReference gameState,
                         StateReference scoreDisplayState) {
        this.resourceManager = resourceManager;
        this.displayScoreState = scoreDisplayState;
        this.gameState = gameState;
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

            for (Capsule capsule : resourceManager.getCapsules()) {
                if (capsule.isActive()) {
                    if (PhysicsUtils.Collide(playerRigidBody, capsule.getRigidBody(), false)) {
                        resourceManager.getManaGauge().add();
                        capsule.setActive(false);
                        resourceManager.playGetItem();
                        resourceManager.getPowerButton().setActive(true);
                    }
                }
            }

            if (player.isDead()) {
                playerInput.setActive(false);
                playerInput.setInvulnerable(true);
                playerInput.setGrounded(false);
                playerRigidBody.stop();
                playerRigidBody.setVelocity(GHOST_VELOCITY);
                resourceManager.stopMusic();
                resourceManager.playGhost();
            }
        }

        if (player.isActive()) {

            boolean playerAlive = player.isAlive();

            if (playerAlive) {
                playerInput.update(deltaTime);

                if (playerInput.isTouchPending()) {
                    if (resourceManager.getJumpButton().checkPress()) {
                        if (playerInput.isGrounded()) {
                            playerRigidBody.setVelocityY(2.5f);
                            playerInput.setGrounded(false);
                            resourceManager.playJump(2.5f);
                        }
                    } else if (resourceManager.getPowerButton().checkPress()) {
                        ManaGauge manaGauge = resourceManager.getManaGauge();
                        if (manaGauge.consume()) {
                            for (MegaSpell megaSpell : resourceManager.getMegaSpells()) {
                                if (!megaSpell.isActive()) {
                                    Vector2 startingPosition = playerRigidBody.getPosition();
                                    megaSpell.getRigidBody().setPosition(startingPosition.getX()
                                                    + 0.1f,
                                            startingPosition.getY());
                                    megaSpell.getRigidBody().setVelocity(SPELL_BASE_VELOCITY);
                                    megaSpell.setActive(true);
                                    resourceManager.playMegaSpell();
                                    break;
                                }
                            }
                            if(manaGauge.isEmpty()) {
                                resourceManager.getPowerButton().setActive(false);
                            }
                        }
                    } else {
                        Vector2 startingVelocity = VECTOR_AUX_VELOCITY;
                        startingVelocity.setCoordinates(playerInput.consumeTouchPosition());
                        Vector2.sub(startingVelocity, startingVelocity, playerRigidBody
                                .getPosition());
                        Vector2.normalize(startingVelocity, startingVelocity);
                        for (MinorSpell minorSpell : resourceManager.getMinorSpells()) {
                            if (!minorSpell.isActive()) {
                                Vector2 spellPosition = VECTOR_AUX_POSITION;
                                spellPosition.setCoordinates(playerRigidBody.getPosition());
                                Vector2.add(spellPosition, spellPosition, SPELL_DISPLACEMENT);
                                minorSpell.getRigidBody().setPosition(spellPosition);
                                Vector2.multiply(startingVelocity, startingVelocity, 2.0f);
                                minorSpell.getRigidBody().setVelocity(startingVelocity);
                                float angle = PhysicsUtils.CalcAngle(startingVelocity,
                                        VECTOR_FORWARD);
                                minorSpell.getRigidBody().setAngle(angle);
                                minorSpell.setActive(true);
                                resourceManager.playMinorSpell();
                                break;
                            }
                        }
                    }
                }
            }

            updatePlayerPosition(playerRigidBody, deltaTime);

            cameraPosition += playerRigidBody.getVelocity().getX() * deltaTime;

            cameraPosition = cameraPosition % 1000;

            if (playerAlive) {
                if (playerRigidBody.getPosition().getY() > PLAYER_GROUND_POSITION) {
                    playerRigidBody.setAccelerationY(playerInput.isInvulnerable() ?
                            GRAVITY_ACCELERATION / 2.0f : GRAVITY_ACCELERATION);
                } else if (!playerInput.isGrounded()) {
                    playerRigidBody.setAccelerationY(ZERO);
                    playerRigidBody.setVelocityY(ZERO);
                    playerRigidBody.setPositionY(PLAYER_GROUND_POSITION);
                    playerInput.setGrounded(true);
                    resourceManager.playBump();
                }
            }

            if (player.isDead() && playerRigidBody.getPosition().getY() > GameConstants.HEAVEN) {
                player.setActive(false);
                player.getInput().setGrounded(false);
                player.getInput().clearTouch();
                gameState.setActive(false);
                displayScoreState.setActive(true);
                resourceManager.getScoreBoard().setActive(true);
            }
        }
    }

    public void reset() {
        Player player = resourceManager.getPlayer();
        RigidBody playerRigidBody = player.getRigidBody();

        playerRigidBody.stop();
        playerRigidBody.setVelocity(PLAYER_VELOCITY);

        player.getStats().restore();

        player.getInput().setActive(true);
        player.getInput().setInvulnerable(false);
        player.setActive(true);

        resourceManager.getManaGauge().reset();
        resourceManager.getPowerButton().setActive(true);
        resourceManager.playMusic();
    }
}
