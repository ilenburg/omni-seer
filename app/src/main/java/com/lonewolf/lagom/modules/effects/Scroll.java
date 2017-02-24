package com.lonewolf.lagom.modules.effects;

/**
 * Created by Ian on 10/02/2017.
 */

public class Scroll {

    private int scrollPosition;

    private final float ratio;
    private float displacement;

    public Scroll(float ratio) {
        this.ratio = ratio;
        this.displacement = 0;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement) {
        displacement *= ratio;
        this.displacement = displacement - (long) displacement;

    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }
}
