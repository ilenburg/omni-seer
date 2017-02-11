package com.lonewolf.lagom.entities;

import android.util.Log;

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
        //this.displacement += 0.001f;

        //this.displacement = Math.round(this.displacement * 10000.0f) / 10000.0f;
        if (this.displacement > 1.0f) {
            this.displacement = this.displacement - 1.0f;
        }

        Log.v("Displacement", Float.toString(this.displacement));
    }

    public float getRatio() {
        return ratio;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }
}
