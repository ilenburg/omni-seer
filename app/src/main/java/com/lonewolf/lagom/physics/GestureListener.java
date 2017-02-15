package com.lonewolf.lagom.physics;

import android.util.Log;
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
        Log.v("ok", "Can't touch this!");
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
        if (e1.getY() > e2.getY()) {
            Input playerInput = resourceManager.getPlayer().getInput();
            if (playerInput.isGrounded()) {
                playerInput.setJumping(true);
            }
        }
        return true;
    }
}
