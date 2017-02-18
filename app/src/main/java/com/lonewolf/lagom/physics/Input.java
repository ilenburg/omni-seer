package com.lonewolf.lagom.physics;

/**
 * Created by Ian on 12/02/2017.
 */

public class Input {

    private float jumpPower;
    private boolean grounded;

    public float getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(float jumpPower) {
        this.jumpPower = jumpPower;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public Input() {
        this.jumpPower = 0.0f;
        this.grounded = true;
    }

}
