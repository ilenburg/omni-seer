package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 10/02/2017.
 */

public class Scroll {

    private int scrollPosition;

    private float ratio;
    private float displacement;

    public Scroll(float ratio, int scrollPosition) {
        this.ratio = ratio;
        this.scrollPosition = scrollPosition;
        this.displacement = 0;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void addDiaplacement(float displacement) {
        this.displacement += displacement;
        if (this.displacement >= 1.0f) {
            this.displacement = 0;
        }
    }

    public float getRatio() {
        return ratio;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }
}
