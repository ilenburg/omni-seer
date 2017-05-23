package com.lonewolf.lagom.physics;

import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Minion;
import com.lonewolf.lagom.entities.Spell;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.GameState;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private static final float GRAVITY_ACCELERATION = -9.8f * 0.8f;
    private static final float ZERO = 0.0f;
    private static final float GROUND_POSITION = -0.535f;

    private static final Vector2 VECTOR_FORWARD = new Vector2(1.0f, 0.0f);
    private static final Vector2 SPELL_BASE_VELOCITY = new Vector2(2.0f, ZERO);
    private static final Vector2 SPELL_DISPLACEMENT = new Vector2(0.03f, -0.03f);

    private GameState gameState;

    private long lastTime;
    private float deltaTime;
    private float animationDeltaTime;

    private float cameraPositon;

    private ResourceManager resourceManager;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public float getCameraPositon() {
        return cameraPositon;
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

        this.cameraPositon = 0.0f;
    }

    @Override
    public void run() {

        this.lastTime = System.currentTimeMillis();

        while (!Thread.currentThread().isInterrupted()) {
            if (gameState == GameState.RUNNING) {
                deltaTime = (System.currentTimeMillis() - lastTime) / 1000.0f;
                animationDeltaTime += deltaTime;
                lastTime = System.currentTimeMillis();

                //Log.v("DeltaTime", Float.toString(deltaTime));

                updatePlayer();

                updateSpells();

                updateShadowLord();

                updateMinions();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void updateMinions() {
        for (Minion minion : resourceManager.getMinions()) {

            RigidBody minionRigidBody = minion.getRigidBody();

            Vector2 vectorFromPlayer = getVectorFromPlayer(minionRigidBody);

            lookAtPlayer(minionRigidBody, vectorFromPlayer);

            if(vectorFromPlayer.getLength() < 1.5f) {

                minionRigidBody.setAcceleration(vectorFromPlayer.multiply(-1.0f).normalize().divide(2.0f));
            }

            if (minionRigidBody.getPosition().getY() <= GROUND_POSITION - 0.02f) {
                minionRigidBody.setVelocityY(0.5f);
            }

            if(minionRigidBody.getPosition().getX() < -1.0f) {
                minionRigidBody.applyForce(new Vector2(1.0f, ZERO));
            }

            if(minionRigidBody.getPosition().getY() > 1.0f) {
                minionRigidBody.applyForce(new Vector2(ZERO, -1.0f));
            }

            updateRigidBody(minionRigidBody);
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

                if (spellRigidBody.getPosition().getY() <= GROUND_POSITION - 0.06f || !spellRigidBody.getPosition().isBounded()) {
                    spell.setActive(false);
                } else {
                    updateRigidBody(spellRigidBody);
                }
            }
        }

    }

    private void updatePlayer() {

        RigidBody playerRigidBody = resourceManager.getPlayer().getRigidBody();
        Input playerInput = resourceManager.getPlayer().getInput();

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
            Vector2 startingVelocity = playerInput.getSpellTarget().sub(playerRigidBody.getPosition()).normalize();
            for (Spell spell : resourceManager.getActiveSpells()) {
                if (!spell.isActive()) {
                    spell.getRigidBody().setPosition(playerRigidBody.getPosition().add(SPELL_DISPLACEMENT));
                    spell.getRigidBody().setVelocity(startingVelocity.multiply(2.0f));
                    float angle = Calc.Angle(startingVelocity, VECTOR_FORWARD);
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

        cameraPositon += playerRigidBody.getVelocity().getX();

        playerRigidBody.setVelocity(Calc.EulerMethod(playerRigidBody.getVelocity(), playerRigidBody.getAcceleration(), deltaTime));

        Vector2 newPosition = Calc.EulerMethod(playerRigidBody.getPosition(), playerRigidBody.getVelocity(), deltaTime);

        playerRigidBody.setPositionY(newPosition.getY());

        if (playerRigidBody.getPosition().getY() > GROUND_POSITION) {
            playerRigidBody.setAccelerationY(playerInput.isInvulnerable() && playerRigidBody.getVelocity().getY() < 0.0f ? GRAVITY_ACCELERATION / 6.0f : GRAVITY_ACCELERATION);
        } else {
            playerRigidBody.setAccelerationY(ZERO);
            playerRigidBody.setVelocityY(ZERO);
            playerRigidBody.setPositionY(GROUND_POSITION);
            playerInput.setGrounded(true);
        }

    }

    private void updateRigidBody(RigidBody rigidBody) {

        rigidBody.setVelocity(Calc.EulerMethod(rigidBody.getVelocity(), rigidBody.getAcceleration(), deltaTime));

        Vector2 newPosition = Calc.EulerMethod(rigidBody.getPosition(), rigidBody.getVelocity(), deltaTime);

        rigidBody.setPosition(newPosition.getX(), newPosition.getY());

    }

    private Vector2 getVectorFromPlayer(RigidBody rigidBody) {
        return rigidBody.getPosition().sub(resourceManager.getPlayer().getRigidBody().getPosition());
    }

    private void lookAtPlayer(RigidBody rigidBody, Vector2 vectorToPlayer) {
        float angle = Calc.Angle(vectorToPlayer, VECTOR_FORWARD);
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
