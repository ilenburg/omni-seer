package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.entities.AirBomb;
import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.Roller;
import com.lonewolf.lagom.entities.Score;
import com.lonewolf.lagom.entities.ShadowLord;
import com.lonewolf.lagom.entities.Spell;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.GameState;
import com.lonewolf.lagom.utils.PhysicsUtils;

import java.util.Random;

import static com.lonewolf.lagom.utils.GameConstants.GRAVITY_ACCELERATION;
import static com.lonewolf.lagom.utils.GameConstants.GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.SPELL_DAMAGE;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_FORWARD;
import static com.lonewolf.lagom.utils.PhysicsUtils.CollisionResponse;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private static final Random random = new Random();

    private static final Vector2 VECTOR_FROM_PLAYER = new Vector2();

    private final PlayerHandler playerHandler;

    private GameState gameState;

    private int distance;

    private long lastTime;
    private float deltaTime;
    private float animationDeltaTime;

    private ResourceManager resourceManager;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public float getCameraPosition() {
        return playerHandler.getCameraPosition();
    }

    public float getAnimationDeltaTime() {
        return animationDeltaTime;
    }

    public void setAnimationDeltaTime(float animationDeltaTime) {
        this.animationDeltaTime = animationDeltaTime;
    }

    public GameEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.playerHandler = new PlayerHandler(resourceManager);

        this.gameState = GameState.RUNNING;
        this.deltaTime = 0.0f;
        this.animationDeltaTime = 0.0f;
        this.distance = 0;
    }

    @Override
    public void run() {

        this.lastTime = System.currentTimeMillis();

        while (!Thread.currentThread().isInterrupted()) {
            if (gameState == GameState.RUNNING) {
                deltaTime = (System.currentTimeMillis() - lastTime) / 1000.0f;
                animationDeltaTime += deltaTime;
                lastTime = System.currentTimeMillis();

                playerHandler.update(deltaTime);

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
        ++distance;
        Score score = resourceManager.getScore();
        score.setValue(distance / 30);
    }

    private void updateAirBombs() {
        for (AirBomb airBomb : resourceManager.getAirBombs()) {
            if (airBomb.isActive()) {
                RigidBody airBombRigidBody = airBomb.getRigidBody();

                airBombRigidBody.addAngle(Math.abs(airBombRigidBody.getVelocity().getX()) *
                        deltaTime * 500);

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

                rollerRigidBody.addAngle(Math.abs(rollerRigidBody.getVelocity().getX()) *
                        deltaTime * 500);

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

                if (minionRigidBody.getPosition().getY() < GROUND_POSITION - minionRigidBody
                        .getRadius()) {
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

                if (spellRigidBody.getPosition().getY() <= GROUND_POSITION - 0.06f ||
                        !spellRigidBody.getPosition().isBounded()) {
                    spell.setActive(false);
                } else {
                    updateRigidBody(spellRigidBody);
                }
            }
        }

    }

    private void updateRigidBody(RigidBody rigidBody) {
        PhysicsUtils.EulerMethod(rigidBody.getVelocity(), rigidBody.getVelocity(), rigidBody
                .getAcceleration(), deltaTime);
        PhysicsUtils.EulerMethod(rigidBody.getPosition(), rigidBody.getPosition(), rigidBody
                .getVelocity(), deltaTime);
    }

    private Vector2 getVectorFromPlayer(RigidBody rigidBody) {
        VECTOR_FROM_PLAYER.setCoordinates(rigidBody.getPosition());
        Vector2.sub(VECTOR_FROM_PLAYER, VECTOR_FROM_PLAYER, resourceManager.getPlayer()
                .getRigidBody().getPosition());
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
