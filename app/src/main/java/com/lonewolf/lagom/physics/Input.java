package com.lonewolf.lagom.physics;

/**
 * Created by Ian on 12/02/2017.
 */

public class Input {

    private boolean jumping;
    private boolean grounded;

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public Input() {
        this.jumping = false;
        this.grounded = true;
    }

}
