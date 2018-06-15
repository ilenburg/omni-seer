package com.lonewolf.lagom.entities.enemies;

import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.entities.base.PhysicalEntity;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.utils.EntityUtils;
import com.lonewolf.lagom.utils.GameConstants;

/**
 * Created by Ian on 21/05/2017.
 */

public class ShadowLord extends PhysicalEntity {

    private final Vector2 startingPosition;

    public ShadowLord(int shaderProgram, int texture) {

        startingPosition = new Vector2(1.57f, 0.2f);

        float radius = 1.0f;

        this.rigidBody = new RigidBody(10, radius / 1.7f, startingPosition.copy(), new Vector2
                (GameConstants.SHADOW_VELOCITY_X, 0.1f));

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .FULL_TEXTURE_COORDINATES).build();
        this.setActive(true);
    }

    public boolean reachedLimit() {
        return this.startingPosition.getX() < this.getRigidBody().getPosition().getX();
    }
}
