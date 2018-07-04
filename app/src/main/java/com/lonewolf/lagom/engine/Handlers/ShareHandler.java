package com.lonewolf.lagom.engine.Handlers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Ian Ilenburg on 24/03/2018.
 */

public class ShareHandler {

    private final Context context;

    public ShareHandler(Context context) {
        this.context = context;
    }

    public void facebookShare() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String message = "Text I want to share.";
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(shareIntent, "Share Omni Seer"));
    }

}
