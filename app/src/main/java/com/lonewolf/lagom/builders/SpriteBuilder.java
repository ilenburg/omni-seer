package com.lonewolf.lagom.builders;

import com.lonewolf.lagom.modules.Sprite;
import com.lonewolf.lagom.modules.Stats;
import com.lonewolf.lagom.modules.effects.Animation;
import com.lonewolf.lagom.modules.effects.ColorTransition;
import com.lonewolf.lagom.modules.effects.Scroll;
import com.lonewolf.lagom.modules.effects.TextureMapping;
import com.lonewolf.lagom.modules.effects.TextureTransition;
import com.lonewolf.lagom.physics.Vector2;

/**
 * Created by Ian on 18/02/2018.
 */

public class SpriteBuilder {

    private final Sprite sprite;

    public SpriteBuilder(int shaderProgram, int texture, float[] geometry, float[] textureCoordinates) {
        this.sprite = new Sprite(shaderProgram, texture, geometry, textureCoordinates);
    }

    public Sprite build() {
        return this.sprite;
    }

    public SpriteBuilder withScroll(Scroll scroll) {
        this.sprite.setScroll(scroll);
        return this;
    }

    public SpriteBuilder withAnimation(Animation animation) {
        this.sprite.setAnimation(animation);
        return this;
    }

    public SpriteBuilder withTextureMapping(TextureMapping textureMapping) {
        this.sprite.setTextureMapping(textureMapping);
        return this;
    }

    public SpriteBuilder withTextureTransition(TextureTransition textureTransition) {
        this.sprite.setTextureTransition(textureTransition);
        return this;
    }

    public SpriteBuilder withColorTransition(ColorTransition colorTransition) {
        this.sprite.setColorTransition(colorTransition);
        return this;
    }

    public SpriteBuilder withStats(Stats stats) {
        this.sprite.setStats(stats);
        return this;
    }

    public SpriteBuilder withPosition(Vector2 position) {
        this.sprite.setPosition(position);
        return this;
    }
}
