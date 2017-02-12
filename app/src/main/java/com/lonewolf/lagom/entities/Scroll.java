package com.lonewolf.lagom.entities;

/**
 * Created by Ian on 10/02/2017.
 */

public class Scroll {

    private int scrollPosition;

    private final float ratio;
    private float displacement;
    //private static final float max = 1000.0f;

    public Scroll(float ratio) {
        this.ratio = ratio;
        this.displacement = 0;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement, boolean log) {
        //this.displacement = (Math.round(displacement * this.ratio * max) / max);

        displacement *= ratio;

        this.displacement = displacement - (long) displacement;

        if (log) {
            //Log.v("", Long.toString(Math.round(displacement)));
            //Log.v("Displacement", Float.toString(this.displacement));
        }

    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }
}
