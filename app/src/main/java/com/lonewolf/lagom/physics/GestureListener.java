package com.lonewolf.lagom.physics;

import android.view.GestureDetector;
import android.view.MotionEvent;

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

        float distanceY = (e1.getY() - e2.getY());
        float distanceX = e2.getX() - e1.getX();

        Input playerInput = resourceManager.getPlayer().getInput();

        if (distanceY > 300 && distanceY > distanceX) {
            if (playerInput.isGrounded()) {
                playerInput.setJumpPower(distanceY / 300000.0f);
                playerInput.setGrounded(false);
            }
        } else if (distanceX > 300) {
            playerInput.setMegaSpell(true);
        } else {
            return onSingleTapUp(e2);
        }
        return true;
    }
}
