package com.trianguloy.adnihilation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Main activity
 */
public class MainActivity extends Activity implements ClickableLinks.OnUrlListener {

    /**
     * Utils
     */
    private Ad ad;
    private Points points;

    /**
     * When created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize
        ad = findViewById(R.id.ad);
        points = new Points(this);
        ad.setPoints(points);

        ClickableLinks.linkify((TextView) findViewById(R.id.textView2), this);
    }

    /**
     * When the app is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        points.save();
    }

    /**
     * When a button is pressed
     */
    public void onButtonClick(View view) {
//        switch (view.getId()){
//            case R.id.regenerate:
        ad.loadAd(false);
//                break;
//        }
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "email":
                ExternalUtils.sendEmail(getString(R.string.app_name), "correo--correo+appAd@hotmail.com", this);
                break;
            case "Github":
                ExternalUtils.openLink("https://github.com/TrianguloY/Adnihilation", this);
                break;
            case "playStore_open":
                ExternalUtils.openLink("https://play.google.com/store/apps/details?id=" + getPackageName(), this);
                break;
            case "playStore_share":
                ExternalUtils.shareText("https://play.google.com/store/apps/details?id=" + getPackageName(), getString(R.string.app_name), this);
                break;
            default:
                Log.d("CLICKABLE_LINK", "unknown tag: " + tag);
        }
    }
}
