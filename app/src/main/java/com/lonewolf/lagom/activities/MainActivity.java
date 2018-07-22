package com.lonewolf.lagom.activities;

import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.lonewolf.lagom.graphics.GameView;
import com.lonewolf.lagom.utils.GameConstants;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        MobileAds.initialize(this, GameConstants.AD_MOB_KEY);

        final RelativeLayout layout = new RelativeLayout(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(GameConstants.AD_MOB_KEY);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice
                (GameConstants.TEST_DEVICE_CODE).build();
        adView.loadAd(adRequest);
        adView.setBackgroundColor(Color.TRANSPARENT);

        adView.setLayoutParams(params);

        layout.addView(gameView);
        layout.addView(adView);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        adView.setAdListener(new AdListener() {

            boolean firstLoad = true;

            public void onAdLoaded() {
                if (firstLoad) {
                    setContentView(layout);
                    firstLoad = false;
                }
            }

            public void onAdFailedToLoad(int errorCode) {
                if (firstLoad) {
                    setContentView(layout);
                    firstLoad = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            gameView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gameView.onTouchEvent(event);
    }

}
