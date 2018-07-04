package com.lonewolf.lagom.modules.effects;

import com.lonewolf.lagom.modules.Input;

/**
 * Created by Ian Ilenburg on 11/02/2017.
 */

public class ColorTransitionStateful extends ColorTransition {

    private final Input state;

    public ColorTransitionStateful(float ratio, Input state) {
        super(ratio);
        this.state = state;
        this.baseTime = 0.0f;
    }

    @Override
    public void addTime(float deltaTime) {
        if (this.state.isInvulnerable()) {
            this.time += deltaTime;
        } else {
            this.time = 0.0f;
        }
    }
}
