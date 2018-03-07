package com.lonewolf.lagom.physics.Handlers;

import com.lonewolf.lagom.entities.enemies.Aerial;
import com.lonewolf.lagom.entities.Capsule;
import com.lonewolf.lagom.entities.enemies.Minion;
import com.lonewolf.lagom.entities.enemies.Roller;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.resources.ResourceManager;

import java.util.Random;

import static com.lonewolf.lagom.utils.GameConstants.AERIAL_STARTING_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.CAPSULE_GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.CAPSULE_STARTING_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.GRAVITY_ACCELERATION;
import static com.lonewolf.lagom.utils.GameConstants.MINION_DOWN_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.MINION_STARTING_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.OUT_OF_SIGH;
import static com.lonewolf.lagom.utils.GameConstants.PLAYER_GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.ROLLER_STARTING_VELOCITY;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_FORWARD;
import static com.lonewolf.lagom.utils.GameConstants.ZERO;
import static com.lonewolf.lagom.utils.PhysicsUtils.floatEffect;
import static com.lonewolf.lagom.utils.PhysicsUtils.getVectorFromPlayer;
import static com.lonewolf.lagom.utils.PhysicsUtils.lookAtPlayer;
import static com.lonewolf.lagom.utils.PhysicsUtils.updateRigidBody;

/**
 * Created by Ian on 25/02/2018.
 */

public class EnemyHandler {

    private static final Random random = new Random();
    private static final int MINION_CHANCE_IN = 200;
    private static final int ROLLER_CHANCE_IN = 1000;
    private static final int AERIAL_CHANCE_IN = 4000;
    private static final int MINION_DROP_CHANCE_IN = 4;
    private static final int DROP_CHANCE_IN = 2;

    private final ResourceManager resourceManager;

    private int activeMinionCount;

    public EnemyHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        activeMinionCount = 0;
    }

    public void update(float deltaTime, int level) {
        updateShadowLord(deltaTime);
        updateMinions(deltaTime);
        updateAirBombs(deltaTime);
        updateRollers(deltaTime);
        updateDrop(deltaTime);
        updateRespawn(level);
    }

    private void updateDrop(float deltaTime) {
        for (Capsule capsule : resourceManager.getCapsules()) {
            if (capsule.isActive()) {
                RigidBody capsuleRigidBody = capsule.getRigidBody();

                if (capsuleRigidBody.getPosition().getY() > CAPSULE_GROUND_POSITION) {
                    capsuleRigidBody.setAccelerationY(GRAVITY_ACCELERATION / 10.0f);
                } else {
                    if (!capsule.isGrounded()) {
                        capsule.setGrounded(true);
                        capsuleRigidBody.setVelocityY(random.nextFloat() / 2.0f);
                    } else {
                        capsuleRigidBody.setVelocityY(ZERO);
                    }
                    capsuleRigidBody.setAccelerationY(ZERO);
                    capsuleRigidBody.setPositionY(CAPSULE_GROUND_POSITION);
                }

                if (resourceManager.getPlayer().isDead()) {
                    capsuleRigidBody.setVelocityX(0.0f);
                }

                updateRigidBody(capsuleRigidBody, deltaTime);
            }
        }
    }

    private void updateRespawn(int level) {
        if (random.nextInt(MINION_CHANCE_IN) == 0) {
            int maxMinions = resourceManager.getMinions().length;
            if (activeMinionCount < level && activeMinionCount < maxMinions) {
                for (Minion minion : resourceManager.getMinions()) {
                    if (!minion.isActive()) {
                        resetVelocity(minion.getRigidBody(), MINION_STARTING_VELOCITY);
                        setMinionSpawnPosition(minion.getRigidBody());
                        minion.getStats().restore();
                        minion.setAggressive(false);
                        minion.setActive(true);
                        ++activeMinionCount;
                        break;
                    }
                }
            }
        }

        if (random.nextInt(ROLLER_CHANCE_IN) == 0) {
            for (Roller roller : resourceManager.getRollers()) {
                if (!roller.isActive()) {
                    resetVelocity(roller.getRigidBody(), ROLLER_STARTING_VELOCITY);
                    setRollerSpawnPosition(roller.getRigidBody());
                    roller.getStats().restore();
                    roller.setActive(true);
                    break;
                }
            }
        }

        if (random.nextInt(AERIAL_CHANCE_IN) == 0) {
            for (Aerial aerial : resourceManager.getAerials()) {
                if (!aerial.isActive()) {
                    resetVelocity(aerial.getRigidBody(), AERIAL_STARTING_VELOCITY);
                    setKickerSpawnPosition(aerial.getRigidBody());
                    aerial.getStats().restore();
                    aerial.setActive(true);
                    break;
                }
            }
        }
    }

    private void updateMinions(float deltaTime) {
        for (Minion minion : resourceManager.getMinions()) {
            RigidBody minionRigidBody = minion.getRigidBody();
            if (minion.isActive()) {
                if (!minion.getStats().isDead()) {
                    Vector2 vectorFromPlayer = getVectorFromPlayer(minionRigidBody, resourceManager
                            .getPlayer().getRigidBody().getPosition());

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

                    if (minionRigidBody.getPosition().getY() < PLAYER_GROUND_POSITION -
                            minionRigidBody
                                    .getRadius()) {
                        minionRigidBody.setPositionY(PLAYER_GROUND_POSITION - minionRigidBody
                                .getRadius());
                        minionRigidBody.setVelocityY(0.5f);
                    }

                    if (minionRigidBody.getPosition().getX() < -1.0f && resourceManager.getPlayer
                            ().isAlive()) {
                        minionRigidBody.applyForce(VECTOR_FORWARD);
                    }
                } else {
                    if (minionRigidBody.getPosition().getY() > OUT_OF_SIGH) {
                        if (resourceManager.getPlayer().isDead()) {
                            minionRigidBody.setVelocityX(0.0f);
                        }
                        minionRigidBody.setVelocity(MINION_DOWN_VELOCITY);
                        minionRigidBody.addAngle(Math.abs(deltaTime * 500) * -1);
                    } else {
                        minion.setActive(false);
                        --activeMinionCount;
                    }
                }

                if (minion.getStats().wasKilled()) {
                    if (random.nextInt(MINION_DROP_CHANCE_IN) == 0) {
                        for (Capsule capsule : resourceManager.getCapsules()) {
                            if (!capsule.isActive()) {
                                spawnCapsule(capsule, minionRigidBody);
                                break;
                            }
                        }
                    }
                }

                updateRigidBody(minionRigidBody, deltaTime);
            }
        }
    }

    private void updateShadowLord(float deltaTime) {
        if (resourceManager.getShadowLord().isActive()) {
            RigidBody shadowLordRigidBody = resourceManager.getShadowLord().getRigidBody();

            float shadowLordPositionY = shadowLordRigidBody.getPosition().getY();

            floatEffect(shadowLordRigidBody, shadowLordPositionY);

            Vector2 vectorFromPlayer = getVectorFromPlayer(shadowLordRigidBody, resourceManager
                    .getPlayer()
                    .getRigidBody().getPosition());

            lookAtPlayer(shadowLordRigidBody, vectorFromPlayer);

            updateRigidBody(shadowLordRigidBody, deltaTime);
        }
    }

    private void updateAirBombs(float deltaTime) {
        for (Aerial aerial : resourceManager.getAerials()) {
            if (aerial.isActive()) {
                RigidBody aerialRigidBody = aerial.getRigidBody();

                aerialRigidBody.addAngle(Math.abs(aerialRigidBody.getVelocity().getX()) *
                        deltaTime * 500);

                if (!aerial.getStats().isDead()) {
                    if (aerialRigidBody.getPosition().getY() <= PLAYER_GROUND_POSITION - 0.03f) {
                        aerialRigidBody.setVelocityY(1.5f + random.nextFloat());
                    }
                }

                if (aerialRigidBody.getPosition().getX() < -2.0f) {
                    aerialRigidBody.setPositionX(2.0f + random.nextFloat());
                    aerial.setActive(false);
                }

                aerialRigidBody.setAccelerationY(GRAVITY_ACCELERATION / 3);

                if (aerial.getStats().isDead() && aerial.getRigidBody().getPosition().getY() <
                        OUT_OF_SIGH) {
                    aerial.setActive(false);
                }

                if (aerial.getStats().wasKilled()) {
                    if (random.nextInt(DROP_CHANCE_IN) == 0) {
                        for (Capsule capsule : resourceManager.getCapsules()) {
                            if (!capsule.isActive()) {
                                spawnCapsule(capsule, aerialRigidBody);
                                break;
                            }
                        }
                    }
                }

                updateRigidBody(aerial.getRigidBody(), deltaTime);
            }
        }
    }

    private void updateRollers(float deltaTime) {
        for (Roller roller : resourceManager.getRollers()) {
            if (roller.isActive()) {
                RigidBody rollerRigidBody = roller.getRigidBody();

                rollerRigidBody.addAngle(Math.abs(rollerRigidBody.getVelocity().getX()) *
                        deltaTime * 500);

                if (rollerRigidBody.getPosition().getX() < -2.0f) {
                    roller.setActive(false);
                }

                if (roller.getStats().isDead()) {
                    if (rollerRigidBody.getPosition().getY() > OUT_OF_SIGH) {
                        rollerRigidBody.setVelocityY(-0.5f);
                    } else {
                        roller.setActive(false);
                    }
                }

                if (roller.getStats().wasKilled()) {
                    if (random.nextInt(DROP_CHANCE_IN) == 0) {
                        for (Capsule capsule : resourceManager.getCapsules()) {
                            if (!capsule.isActive()) {
                                spawnCapsule(capsule, rollerRigidBody);
                                break;
                            }
                        }
                    }
                }

                updateRigidBody(roller.getRigidBody(), deltaTime);
            }
        }
    }

    private void setMinionSpawnPosition(RigidBody rigidBody) {
        rigidBody.setPosition((random.nextFloat()) + 2, random.nextFloat() - 0.5f);
    }

    private void setRollerSpawnPosition(RigidBody rigidBody) {
        rigidBody.setPosition(2.0f + (random.nextFloat() * 2), -0.49f);
    }

    private void setKickerSpawnPosition(RigidBody rigidBody) {
        rigidBody.setPosition(2.0f + (random.nextFloat() * 2), -0.51f);
    }

    private void resetVelocity(RigidBody rigidBody, Vector2 velocity) {
        rigidBody.stop();
        rigidBody.setVelocity(velocity);
    }

    private void spawnCapsule(Capsule capsule, RigidBody rigidBody) {
        RigidBody capsuleRigidBody = capsule.getRigidBody();
        capsuleRigidBody.stop();
        capsule.setGrounded(false);
        capsuleRigidBody.setPosition(rigidBody.getPosition());
        capsuleRigidBody.setVelocity(CAPSULE_STARTING_VELOCITY);
        capsule.setActive(true);
    }
}
