package com.lonewolf.lagom.hud;

import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.entities.base.DrawableEntity;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.utils.EntityUtils;
import com.lonewolf.lagom.utils.PhysicsUtils;
import com.lonewolf.lagom.utils.BoardAction;

/**
 * Created by Ian on 24/03/2018.
 */

public class GameOverBoard extends DrawableEntity {

    private final Input input;

    private final RigidBody playRigidBody;
    private final RigidBody shareRigidBody;

    public GameOverBoard(int shaderProgram, int texture, Input input) {
        this.input = input;
        this.playRigidBody = new RigidBody(1f, 0.3f, new Vector2(0.45f, -0.25f));
        this.shareRigidBody = new RigidBody(1f, 0.3f, new Vector2(-0.35f, -0.25f));
        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(1.0f), EntityUtils
                .FULL_TEXTURE_COORDINATES).build();
        this.setActive(false);
    }

    public BoardAction checkAction() {
        if (input.isTouchPending()) {
            if (PhysicsUtils.Collide(input.consumeTouchPosition(), playRigidBody)) {
                return BoardAction.PLAY;
            } else if (PhysicsUtils.Collide(input.consumeTouchPosition(), shareRigidBody)) {
                return BoardAction.SHARE;
            }
        }
        return BoardAction.NONE;
    }
}
