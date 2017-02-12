package com.lonewolf.lagom.physics;

/**
 * Created by Ian on 12/02/2017.
 */

public final class Calc {

    public static Vector2 EulerMethod(Vector2 base, Vector2 change, float time) {
        return base.add(change.multiply(time));
    }

    private Calc() {

    }

}
