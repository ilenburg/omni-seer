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
        String message = "My Score in Omni Seer is: " + resourceManager.getHighScore();
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        resourceManager.getContext().startActivity(Intent.createChooser(shareIntent, "Share Omni " +
                "Seer Score"));
    }

}
