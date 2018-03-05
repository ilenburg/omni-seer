package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.builders.SpriteBuilder;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

import java.util.Random;

/**
 * Created by Ian on 04/03/2018.
 */

public class Capsule {

    private static final Random random = new Random();

    private final RigidBody rigidBody;
    private final Sprite sprite;
    private boolean grounded;
    private boolean active;

    public Capsule(int shaderProgram, int texture) {

        float radius = 0.08f;

        this.rigidBody = new RigidBody(1, radius / 4, new Vector2(1 + random.nextFloat(), 2.0f),
                new Vector2(-0.19f, 0.0f));

        ColorTransition colorTransition = new ColorTransition(6.0f);

        this.sprite = new SpriteBuilder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .FULL_TEXTURE_COORDINATES).withColorTransition(colorTransition).build();

        active = false;
        grounded = false;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
