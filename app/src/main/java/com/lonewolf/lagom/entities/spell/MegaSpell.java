package com.lonewolf.lagom.entities.spell;

import com.lonewolf.lagom.entities.Impact;
import com.lonewolf.lagom.modules.RigidBody;
import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.engine.Vector2;
import com.lonewolf.lagom.utils.EntityUtils;

/**
 * Created by Ian Ilenburg on 23/01/2017.
 */

public class MegaSpell extends Spell {

    public MegaSpell(int shaderProgram, int texture, Impact impact) {
        super(impact, 10);

        float[][] animationCoordinates = new float[][]{{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        }, {
                0.5f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
                1.0f, 0.0f
        }, {
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f
        }, {
                0.5f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
                1.0f, 0.0f
        }};

        float radius = 0.2f;

        this.rigidBody = new RigidBody(0.5f, radius / 3, new Vector2(-1.0f, -0.535f), new Vector2
                (0.0f, 0.0f));

        Animation animation = new Animation.Builder(animationCoordinates, 1.0f).build();

        ColorTransition colorTransition = new ColorTransition(5.0f);

        this.sprite = new Sprite.Builder(shaderProgram, texture, EntityUtils
                .GenerateSymmetricGeometryCoordinates(radius), EntityUtils
                .QUARTER_TEXTURE_COORDINATES).withAnimation(animation).withColorTransition
                (colorTransition).build();
    }
}
