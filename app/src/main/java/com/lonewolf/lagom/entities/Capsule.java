package com.lonewolf.lagom.entities;

import com.lonewolf.lagom.entities.base.PhysicalEntity;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

import java.util.Random;

/**
 * Created by Ian on 04/03/2018.
 */

public class Capsule extends PhysicalEntity {

    private static final Random random = new Random();

    private boolean grounded;

    public Capsule(int shaderProgram, int texture) {

        float radius = 0.08f;

        this.rigidBody = new RigidBody(1, radius / 4, new Vector2(1 + random.nextFloat(), 2.0f),
                new Vector2(-0.19f, 0.0f));

        ColorTransition colorTransition = new ColorTransition(6.0f);

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .FULL_TEXTURE_COORDINATES).withColorTransition(colorTransition).build();

        grounded = false;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
