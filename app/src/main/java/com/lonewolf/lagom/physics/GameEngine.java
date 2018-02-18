package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.entities.AirBomb;
import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.Player;
import com.lonewolf.lagom.entities.Roller;
import com.lonewolf.lagom.entities.Score;
import com.lonewolf.lagom.entities.ShadowLord;
import com.lonewolf.lagom.entities.Spell;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.GameState;

import java.util.Random;

import static com.lonewolf.lagom.physics.PhysicsUtils.CollisionResponse;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private static final float GRAVITY_ACCELERATION = -9.8f * 0.8f;
    private static final float ZERO = 0.0f;
    private static final float GROUND_POSITION = -0.535f;

    private static final Vector2 VECTOR_FORWARD = new Vector2(1.0f, ZERO);
    private static final Vector2 VECTOR_BACK = new Vector2(-1.0f, ZERO);
    private static final Vector2 VECTOR_UP = new Vector2(ZERO, 1.0f);
    private static final Vector2 VECTOR_DOWN = new Vector2(ZERO, -1.0f);
    private static final Vector2 VECTOR_GHOST = new Vector2(ZERO, 0.3f);
    private static final Vector2 SPELL_BASE_VELOCITY = new Vector2(2.0f, ZERO);
    private static final Vector2 SPELL_DISPLACEMENT = new Vector2(0.03f, -0.03f);

    private static final Vector2 RESULT_AUX = new Vector2();
    private static final Vector2 VECTOR_FROM_PLAYER = new Vector2();

    private static final int SPELL_DAMAGE = 1;

    private static final Random random = new Random();

    private GameState gameState;

    private long lastTime;
    private float deltaTime;
    private float animationDeltaTime;

    private float cameraPosition;

    private ResourceManager resourceManager;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public float getCameraPosition() {
        return cameraPosition;
    }

    public float getAnimationDeltaTime() {
        return animationDeltaTime;
    }

    public void setAnimationDeltaTime(float animationDeltaTime) {
        this.animationDeltaTime = animationDeltaTime;
    }

    public GameEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        this.gameState = GameState.RUNNING;
        this.deltaTime = 0.0f;
        this.animationDeltaTime = 0.0f;

        this.cameraPosition = 0.0f;
    }

    @Override
    public void run() {

        this.lastTime = System.currentTimeMillis();

        while (!Thread.currentThread().isInterrupted()) {
            if (gameState == GameState.RUNNING) {
                deltaTime = (System.currentTimeMillis() - lastTime) / 1000.0f;
                animationDeltaTime += deltaTime;
                lastTime = System.currentTimeMillis();

                updatePlayer();

                updateSpells();

                updateShadowLord();

                updateMinions();

                updateAirBombs();

                updateRollers();

                updateScore();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void updateScore() {
        Score score = resourceManager.getScore();
            score.addValue(1);
    }

    private void updateAirBombs() {
        for (AirBomb airBomb : resourceManager.getAirBombs()) {
            if (airBomb.isActive()) {
                RigidBody airBombRigidBody = airBomb.getRigidBody();

                airBombRigidBody.addAngle(Math.abs(airBombRigidBody.getVelocity().getX()) * deltaTime * 500);

                if (airBombRigidBody.getPosition().getX() < -2.0f) {
                    airBombRigidBody.setPositionX(2.0f + random.nextFloat());
                }

                if (airBombRigidBody.getPosition().getY() <= GROUND_POSITION - 0.03f) {
                    airBombRigidBody.setVelocityY(1.5f + random.nextFloat());
                }

                airBombRigidBody.setAccelerationY(GRAVITY_ACCELERATION / 3);

                updateRigidBody(airBomb.getRigidBody());
            }
        }
    }

    private void updateRollers() {
        for (Roller roller : resourceManager.getRollers()) {
            if (roller.isActive()) {
                RigidBody rollerRigidBody = roller.getRigidBody();

                rollerRigidBody.addAngle(Math.abs(rollerRigidBody.getVelocity().getX()) * deltaTime * 500);

                if (rollerRigidBody.getPosition().getX() < -2.0f) {
                    rollerRigidBody.setPositionX(2.0f + random.nextFloat());
                }
                updateRigidBody(roller.getRigidBody());
            }
        }
    }

    private void updateMinions() {
        for (Minion minion : resourceManager.getMinions()) {

            if (minion.isActive()) {
                RigidBody minionRigidBody = minion.getRigidBody();

                Vector2 vectorFromPlayer = getVectorFromPlayer(minionRigidBody);

                lookAtPlayer(minionRigidBody, vectorFromPlayer);

                if (vectorFromPlayer.getLength() < 1.5f) {
                    minion.setAggressive(true);
                }

                if (minion.isAggressive()) {
                    Vector2.multiply(vectorFromPlayer, vectorFromPlayer, -1.0f);
                    Vector2.normalize(vectorFromPlayer, vectorFromPlayer);
                    Vector2.divide(vectorFromPlayer, vectorFromPlayer, 2.0f);
                    minionRigidBody.setAcceleration(vectorFromPlayer);
                }

                if (minionRigidBody.getPosition().getY() < GROUND_POSITION - minionRigidBody.getRadius()) {
                    minionRigidBody.setPositionY(GROUND_POSITION - minionRigidBody.getRadius());
                    minionRigidBody.setVelocityY(0.5f);
                }

                if (minionRigidBody.getPosition().getX() < -1.0f) {
                    minionRigidBody.applyForce(VECTOR_FORWARD);
                }

                updateRigidBody(minionRigidBody);
            }
        }
    }

    private void updateShadowLord() {

        if (resourceManager.getShadowLord().isActive()) {
            RigidBody shadowLordRigidBody = resourceManager.getShadowLord().getRigidBody();

            float shadowLordPositionY = shadowLordRigidBody.getPosition().getY();

            floatEffect(shadowLordRigidBody, shadowLordPositionY);

            Vector2 vectorFromPlayer = getVectorFromPlayer(shadowLordRigidBody);

            lookAtPlayer(shadowLordRigidBody, vectorFromPlayer);

            updateRigidBody(shadowLordRigidBody);
        }
    }

    private void updateSpells() {

        RigidBody spellRigidBody;

        for (MegaSpell megaSpell : resourceManager.getMegaSpells()) {
            if (megaSpell.isActive()) {
                spellRigidBody = megaSpell.getRigidBody();
                if (spellRigidBody.getPosition().getY() <= GROUND_POSITION - 0.06f) {
                    megaSpell.setActive(false);
                }

                updateRigidBody(spellRigidBody);

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
                    if (minion.isActive()) {
                        if (PhysicsUtils.Collide(spellRigidBody, minion.getRigidBody())) {
                            CollisionResponse(spellRigidBody, minion.getRigidBody());
                            spell.setActive(false);
                            minion.setAggressive(true);
                            minion.getStats().dealDamage(SPELL_DAMAGE);
                            if (minion.getStats().isDead()) {
                                minion.setActive(false);
                            }
                        }
                    }
                }

                for (Roller roller : resourceManager.getRollers()) {
                    if (PhysicsUtils.Collide(spellRigidBody, roller.getRigidBody())) {
                        roller.getStats().dealDamage(SPELL_DAMAGE);
                        spell.setActive(false);
                    }
                }

                for (AirBomb airBomb : resourceManager.getAirBombs()) {
                    if (PhysicsUtils.Collide(spellRigidBody, airBomb.getRigidBody())) {
                        airBomb.getStats().dealDamage(SPELL_DAMAGE);
                        spell.setActive(false);
                    }
                }

                if (spellRigidBody.getPosition().getY() <= GROUND_POSITION - 0.06f || !spellRigidBody.getPosition().isBounded()) {
                    spell.setActive(false);
                } else {
                    updateRigidBody(spellRigidBody);
                }
            }
        }

    }

    private void updatePlayer() {

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
                        megaSpell.getRigidBody().setPosition(startingPosition.getX() + 0.1f, startingPosition.getY());
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

            updatePlayerPosition(playerRigidBody);

            cameraPosition += playerRigidBody.getVelocity().getX() * deltaTime;

            cameraPosition = cameraPosition % 1000;

            if (playerRigidBody.getPosition().getY() > GROUND_POSITION) {
                playerRigidBody.setAccelerationY(playerInput.isInvulnerable() ? GRAVITY_ACCELERATION / 2.0f : GRAVITY_ACCELERATION);
            } else {
                playerRigidBody.setAccelerationY(ZERO);
                playerRigidBody.setVelocityY(ZERO);
                playerRigidBody.setPositionY(GROUND_POSITION);
                playerInput.setGrounded(true);
            }
        }
    }

    private void updatePlayerPosition(RigidBody playerRigidBody) {
        PhysicsUtils.EulerMethod(playerRigidBody.getVelocity(), playerRigidBody.getVelocity(), playerRigidBody.getAcceleration(), deltaTime);
        PhysicsUtils.EulerMethod(RESULT_AUX, playerRigidBody.getPosition(), playerRigidBody.getVelocity(), deltaTime);
        playerRigidBody.setPositionY(RESULT_AUX.getY());
    }

    private void updateRigidBody(RigidBody rigidBody) {
        PhysicsUtils.EulerMethod(rigidBody.getVelocity(), rigidBody.getVelocity(), rigidBody.getAcceleration(), deltaTime);
        PhysicsUtils.EulerMethod(rigidBody.getPosition(), rigidBody.getPosition(), rigidBody.getVelocity(), deltaTime);
    }

    private Vector2 getVectorFromPlayer(RigidBody rigidBody) {
        VECTOR_FROM_PLAYER.setCoordinates(rigidBody.getPosition());
        Vector2.sub(VECTOR_FROM_PLAYER, VECTOR_FROM_PLAYER, resourceManager.getPlayer().getRigidBody().getPosition());
        return VECTOR_FROM_PLAYER;
    }

    private void lookAtPlayer(RigidBody rigidBody, Vector2 vectorToPlayer) {
        float angle = PhysicsUtils.CalcAngle(vectorToPlayer, VECTOR_FORWARD);
        rigidBody.setAngle(angle);
    }

    private void floatEffect(RigidBody rigidBody, float positionY) {
        if (positionY > 0.2f) {
            rigidBody.setAccelerationY(-0.1f);
        } else {
            rigidBody.setAccelerationY(0.1f);
        }
    }

}
