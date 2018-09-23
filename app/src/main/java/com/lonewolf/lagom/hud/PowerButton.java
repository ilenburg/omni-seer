package com.lonewolf.lagom.hud;

import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.entities.base.DrawableEntity;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.utils.EntityUtils;
import com.lonewolf.lagom.utils.PhysicsUtils;

/**
 * Created by Ian Ilenburg on 23/09/2018.
 */

public class PowerButton extends DrawableEntity {

    private final Input input;
    private final RigidBody rigidBody;

    public PowerButton(int shaderProgram, int texture, Input input) {
        this.input = input;
        float baseRadius = 0.2f;
        this.rigidBody = new RigidBody(1f, baseRadius, new Vector2(1.40f, -0.4f));
        this.rigidBody.addAngle(-90);
        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(baseRadius), EntityUtils
                .FULL_TEXTURE_COORDINATES).build();
        this.setActive(true);
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public boolean checkPress() {
        if (this.isActive() && input.isTouchPending()) {
            if (PhysicsUtils.EdgeCollide(input.readTouchPosition(), rigidBody)) {
                input.consumeTouchPosition();
                return true;
            }
        }
        return false;
    }
}