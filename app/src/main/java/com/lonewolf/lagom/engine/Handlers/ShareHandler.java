package com.lonewolf.lagom.engine.Handlers;

import android.content.Intent;

import com.lonewolf.lagom.resources.ResourceManager;

/**
 * Created by Ian Ilenburg on 24/03/2018.
 */

public class ShareHandler {

    private final ResourceManager resourceManager;

    public ShareHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void defaultShare() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String message = "My High Score in Omni Seer is: " + resourceManager.getHighScore() +
                " https://play.google.com/store/apps/details?id=com.lonewolf.lagom";
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        resourceManager.getContext().startActivity(Intent.createChooser(shareIntent, "Share Omni " +
                "Seer High Score"));
    }

}
