package com.trianguloy.adnihilation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"correo--correo+appAd@hotmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                startActivity(Intent.createChooser(emailIntent, getString(R.string.send)));
                break;
            case "Github":
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/TrianguloY/"))); //TODO: change with github url
                break;
            case "playStore_open":
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException ignored) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
            case "playStore_share":
                final String title = getString(R.string.app_name);

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, title);
                i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(i, getString(R.string.send)));
                break;
            default:
                Log.d("CLICKABLE_LINK", "unknown url: " + tag);
        }
    }
}
