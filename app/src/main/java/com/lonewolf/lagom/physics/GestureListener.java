package com.lonewolf.lagom.physics;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian on 14/02/2017.
 */

public class GestureListener implements GestureDetector.OnGestureListener {

    private final ResourceManager resourceManager;

    public GestureListener(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        resourceManager.getPlayer().getInput().setSpellTarget(e.getX(), e.getY());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float distanceX = e2.getX() - e1.getX();
        float distanceY = e1.getY() - e2.getY();

        Input playerInput = resourceManager.getPlayer().getInput();

        boolean validFling = false;

        if (Math.abs(distanceY) > Math.abs(distanceX)) {
            if (distanceY > 200) {
                if (playerInput.isGrounded()) {
                    if (distanceY > 600.0f) {
                        distanceY = 600.0f;
                    }
                    playerInput.setJumpPower(distanceY / 200.0f);
                    playerInput.setGrounded(false);
                    validFling = true;
                }
            }
        } else if (Math.abs(distanceX) > 200) {
            if (distanceX > 0) {
                playerInput.setMegaSpell(true);
                validFling = true;
            } else {
                resourceManager.getPlayer().getState().setInvulnerable(true);
                validFling = true;
            }
        }

        if (validFling) {
            return true;
        } else {
            return onSingleTapUp(e2);
        }

    }
}
