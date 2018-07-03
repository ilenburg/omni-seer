package com.lonewolf.lagom.modules.effects;

import com.lonewolf.lagom.utils.GameConstants;

/**
 * Created by Ian on 11/02/2017.
 */

public class ColorTransitionTriggerable extends ColorTransition {

    private final float MAX_CYCLE;

    private boolean active;

    public ColorTransitionTriggerable(float ratio) {
        super(ratio);
        this.MAX_CYCLE = GameConstants.PI_RADIANS / ratio;
        this.baseTime = 0.5f / ratio;
        this.active = false;
    }

    public void trigger() {
        active = true;
        time = 0.0f;
    }

    @Override
    public void addTime(float deltaTime) {
        if (active) {
            if (time <= MAX_CYCLE / 2) {
                time += deltaTime;
            } else {
                time = 0.0f;
                active = false;
            }
        }
    }
}
