package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.entities.AirBomb;
import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.Player;
import com.lonewolf.lagom.entities.Roller;
import com.lonewolf.lagom.entities.Spell;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.utils.PhysicsUtils;

import static com.lonewolf.lagom.utils.GameConstants.GRAVITY_ACCELERATION;
import static com.lonewolf.lagom.utils.GameConstants.GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_BASE_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_DISPLACEMENT;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_FORWARD;
import static com.lonewolf.lagom.utils.GameConstants.ZERO;

/**
 * Created by Ian on 18/02/2018.
 */

public class PlayerHandler {

    private static final Vector2 RESULT_AUX = new Vector2();

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

            boolean playerDead = false;

            for (Minion minion : resourceManager.getMinions()) {
                if (minion.isActive()) {
                    /*if (PhysicsUtils.Collide(playerRigidBody, minion.getRigidBody())) {
                        playerDead = true;
                    }*/
                }
            }

            for (Roller roller : resourceManager.getRollers()) {
                if (roller.isActive()) {
                    /*if (PhysicsUtils.Collide(playerRigidBody, roller.getRigidBody())) {
                        playerDead = true;
                    }*/
                }
            }

            for (AirBomb airBomb : resourceManager.getAirBombs()) {
                if (airBomb.isActive()) {
                    /*if (PhysicsUtils.Collide(playerRigidBody, airBomb.getRigidBody())) {
                        playerDead = true;
                    }*/
                }
            }

            playerDead = false;

            if (playerDead) {
                player.setActive(false);
                //playerInput.setInvulnerable(true);
                //playerRigidBody.setVelocityValue(VECTOR_GHOST);
            }

        }

        if (player.isActive()) {

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
                for (Spell spell : resourceManager.getActiveSpells()) {
                    if (!spell.isActive()) {
                        Vector2 spellPosition = playerRigidBody.getPosition().copy();
                        Vector2.add(spellPosition, spellPosition, SPELL_DISPLACEMENT);
                        spell.getRigidBody().setPosition(spellPosition);
                        Vector2.multiply(startingVelocity, startingVelocity, 2.0f);
                        spell.getRigidBody().setVelocity(startingVelocity);
                        float angle = PhysicsUtils.CalcAngle(startingVelocity, VECTOR_FORWARD);
                        spell.getRigidBody().setAngle(angle);
                        spell.setActive(true);
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

            updatePlayerPosition(playerRigidBody, deltaTime);

            cameraPosition += playerRigidBody.getVelocity().getX() * deltaTime;

            cameraPosition = cameraPosition % 1000;

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

    private void updatePlayerPosition(RigidBody playerRigidBody, float deltaTime) {
        PhysicsUtils.EulerMethod(playerRigidBody.getVelocity(), playerRigidBody.getVelocity(),
                playerRigidBody.getAcceleration(), deltaTime);
        PhysicsUtils.EulerMethod(RESULT_AUX, playerRigidBody.getPosition(), playerRigidBody
                .getVelocity(), deltaTime);
        playerRigidBody.setPositionY(RESULT_AUX.getY());
    }

}
