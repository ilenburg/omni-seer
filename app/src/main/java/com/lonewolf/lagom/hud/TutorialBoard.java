package com.lonewolf.lagom.hud;

import com.lonewolf.lagom.entities.base.DrawableEntity;
import com.lonewolf.lagom.modules.Input;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian Ilenburg on 24/03/2018.
 */

public class TutorialBoard extends DrawableEntity {

    private final Input input;

    public TutorialBoard(int shaderProgram, int texture, Input input) {
        this.input = input;
        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(1.0f), EntityUtils
                .FULL_TEXTURE_COORDINATES).build();
        this.setActive(false);
    }

    public boolean checkAction() {
        if (input.isTouchPending()) {
            input.consumeTouchPosition();
            return true;
        }
        return false;
    }
}
