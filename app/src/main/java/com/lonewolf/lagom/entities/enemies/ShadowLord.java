package com.lonewolf.lagom.entities.enemies;

import com.lonewolf.lagom.entities.base.PhysicalEntity;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.physics.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian on 21/05/2017.
 */

public class ShadowLord extends PhysicalEntity {

    public ShadowLord(int shaderProgram, int texture) {

        float radius = 1.0f;

        this.rigidBody = new RigidBody(10, radius / 1.7f, new Vector2(1.57f, 0.2f), new Vector2
                (0.0f, 0.1f));

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .FULL_TEXTURE_COORDINATES).build();
        this.setActive(true);
    }
}
