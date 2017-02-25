package com.lonewolf.lagom.physics;

import android.util.Log;

import com.lonewolf.lagom.entities.MegaSpell;
import com.lonewolf.lagom.entities.Spell;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.resources.ResourceManager;
import com.lonewolf.lagom.states.GameState;

import static android.content.ContentValues.TAG;

/**
 * Created by Ian on 28/01/2017.
 */

public class GameEngine implements Runnable {

    private static final float GRAVITY_ACCELERATION = -0.0000098f * 0.8f;
    private static final float ZERO = 0.0f;
    private static final Vector2 VECTOR_UP = new Vector2(1,0);
    private static final Vector2 SPELL_BASE_VELOCITY = new Vector2(0.002f, ZERO);

    private GameState gameState;

    private long lastTime;
    private float deltaTime;

    private float cameraPositon;

    private final float groundPosition;

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

    public float getDeltaTime() {
        return deltaTime;
    }

    public GameEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        this.gameState = GameState.RUNNING;
        this.lastTime = System.currentTimeMillis();
        this.deltaTime = 0L;

        this.cameraPositon = 0.0f;
        this.groundPosition = -0.535f;
    }

    @Override
    public void run() {
        while (gameState == GameState.RUNNING) {

            deltaTime = (System.currentTimeMillis() - lastTime);
            lastTime = System.currentTimeMillis();

            //Log.v("DeltaTime", Float.toString(deltaTime));

            updatePlayer();

            updateSpells();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void updateSpells() {

        RigidBody spellRigidBody;

        MegaSpell megaSpell = resourceManager.getMegaSpell();

        if (megaSpell.isActive()) {
            spellRigidBody = megaSpell.getRigidBody();
            if (spellRigidBody.getPosition().getY() <= groundPosition - 0.06f) {
                megaSpell.setActive(false);
            }

            spellRigidBody.setVelocity(Calc.EulerMethod(spellRigidBody.getVelocity(), spellRigidBody.getAcceleration(), deltaTime));

            Vector2 newPosition = Calc.EulerMethod(spellRigidBody.getPosition(), spellRigidBody.getVelocity(), deltaTime);

            spellRigidBody.setPosition(newPosition.getX(), newPosition.getY());
            if (!spellRigidBody.getPosition().isBounded()) {
                megaSpell.setActive(false);
            }
        }

        for (Spell spell : resourceManager.getActiveSpells()) {
            if (spell.isActive()) {
                spellRigidBody = spell.getRigidBody();

                if (spellRigidBody.getPosition().getY() <= groundPosition - 0.06f || !spellRigidBody.getPosition().isBounded()) {
                    spell.setActive(false);
                } else {
                    spellRigidBody.setVelocity(Calc.EulerMethod(spellRigidBody.getVelocity(), spellRigidBody.getAcceleration(), deltaTime));

                    Vector2 newPosition = Calc.EulerMethod(spellRigidBody.getPosition(), spellRigidBody.getVelocity(), deltaTime);

                    spellRigidBody.setPosition(newPosition.getX(), newPosition.getY());
                }
            }
        }

    }

    private void updatePlayer() {

        RigidBody playerRigidBody = resourceManager.getPlayer().getRigidBody();
        Input playerInput = resourceManager.getPlayer().getInput();

        if (playerInput.isMegaSpell()) {
            MegaSpell megaSpell = resourceManager.getMegaSpell();
            Vector2 startingPosition = playerRigidBody.getPosition();
            megaSpell.getRigidBody().setPosition(startingPosition.getX() + 0.1f, startingPosition.getY());
            megaSpell.getRigidBody().setVelocity(SPELL_BASE_VELOCITY);
            megaSpell.setActive(true);
            playerInput.setMegaSpell(false);
        }

        if (!playerInput.getSpellTarget().isZero()) {
            Vector2 startingVelocity = playerInput.getSpellTarget().sub(playerRigidBody.getPosition()).normalize();
            for (Spell spell : resourceManager.getActiveSpells()) {
                if (!spell.isActive()) {
                    spell.getRigidBody().setPosition(playerRigidBody.getPosition().copy());
                    spell.getRigidBody().setVelocity(startingVelocity.divide(600.0f));
                    float angle = Calc.Angle(startingVelocity, VECTOR_UP);
                    if (startingVelocity.getY() < ZERO) {
                        angle *= -1;
                    }
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

        cameraPositon += playerRigidBody.getVelocity().getX() * 10;

        playerRigidBody.setVelocity(Calc.EulerMethod(playerRigidBody.getVelocity(), playerRigidBody.getAcceleration(), deltaTime));

        Vector2 newPosition = Calc.EulerMethod(playerRigidBody.getPosition(), playerRigidBody.getVelocity(), deltaTime);

        playerRigidBody.setPositionY(newPosition.getY());

        if (playerRigidBody.getPosition().getY() > groundPosition) {
            playerRigidBody.setAccelerationY(GRAVITY_ACCELERATION);
        } else {
            playerRigidBody.setAccelerationY(ZERO);
            playerRigidBody.setVelocityY(ZERO);
            playerRigidBody.setPositionY(groundPosition);
            playerInput.setGrounded(true);
        }

    }

}
