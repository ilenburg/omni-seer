package com.lonewolf.lagom.engine.Handlers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Ian on 24/03/2018.
 */

public class AndroidHandler {

    private final Context context;

    public AndroidHandler(Context context) {
        this.context = context;
    }

    public void facebookShare() {
        Intent share = new Intent(Intent.ACTION_SEND);
        String message = "Text I want to share.";
        share.putExtra(Intent.EXTRA_TEXT, message);
        share.setType("text/plain");
        context.startActivity(Intent.createChooser(share, "Share Omni Seer"));
    }

}
