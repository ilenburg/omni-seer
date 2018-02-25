package com.lonewolf.lagom.physics.Handlers;

import com.lonewolf.lagom.entities.AirBomb;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.Roller;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.resources.ResourceManager;

import java.util.Random;

import static com.lonewolf.lagom.utils.GameConstants.GRAVITY_ACCELERATION;
import static com.lonewolf.lagom.utils.GameConstants.GROUND_POSITION;
import static com.lonewolf.lagom.utils.GameConstants.VECTOR_FORWARD;
import static com.lonewolf.lagom.utils.PhysicsUtils.floatEffect;
import static com.lonewolf.lagom.utils.PhysicsUtils.getVectorFromPlayer;
import static com.lonewolf.lagom.utils.PhysicsUtils.lookAtPlayer;
import static com.lonewolf.lagom.utils.PhysicsUtils.updateRigidBody;

/**
 * Created by Ian on 25/02/2018.
 */

public class EnemyHandler {

    private static final Vector2 WAY_DOWN = new Vector2(0.0f, -0.3f);
    private static final Random random = new Random();
    private final ResourceManager resourceManager;

    public EnemyHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void updateMinions(float deltaTime) {
        for (Minion minion : resourceManager.getMinions()) {
            RigidBody minionRigidBody = minion.getRigidBody();
            if (minion.isActive() && !minion.getStats().isDead()) {
                Vector2 vectorFromPlayer = getVectorFromPlayer(minionRigidBody, resourceManager
                        .getPlayer()
                        .getRigidBody().getPosition());

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
            } else {
                minionRigidBody.setVelocity(WAY_DOWN.getX(), WAY_DOWN.getY());
                minionRigidBody.addAngle(Math.abs(deltaTime * 500) * -1);
            }

            updateRigidBody(minionRigidBody, deltaTime);
        }
    }

    public void updateShadowLord(float deltaTime) {

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

    public void updateAirBombs(float deltaTime) {
        for (AirBomb airBomb : resourceManager.getAirBombs()) {
            if (airBomb.isActive()) {
                RigidBody airBombRigidBody = airBomb.getRigidBody();

                airBombRigidBody.addAngle(Math.abs(airBombRigidBody.getVelocity().getX()) *
                        deltaTime * 500);

                if (airBombRigidBody.getPosition().getX() < -2.0f) {
                    airBombRigidBody.setPositionX(2.0f + random.nextFloat());
                }

                if (!airBomb.getStats().isDead()) {
                    if (airBombRigidBody.getPosition().getY() <= GROUND_POSITION - 0.03f) {
                        airBombRigidBody.setVelocityY(1.5f + random.nextFloat());
                    }
                }

                airBombRigidBody.setAccelerationY(GRAVITY_ACCELERATION / 3);

                updateRigidBody(airBomb.getRigidBody(), deltaTime);
            }
        }
    }

    public void updateRollers(float deltaTime) {
        for (Roller roller : resourceManager.getRollers()) {
            if (roller.isActive()) {
                RigidBody rollerRigidBody = roller.getRigidBody();

                rollerRigidBody.addAngle(Math.abs(rollerRigidBody.getVelocity().getX()) *
                        deltaTime * 500);

                if (rollerRigidBody.getPosition().getX() < -2.0f) {
                    rollerRigidBody.setPositionX(2.0f + random.nextFloat());
                }

                if (roller.getStats().isDead()) {
                    rollerRigidBody.setVelocityY(-0.5f);
                }
                updateRigidBody(roller.getRigidBody(), deltaTime);
            }
        }
    }
}
