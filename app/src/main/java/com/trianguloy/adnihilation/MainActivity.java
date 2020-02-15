package com.trianguloy.adnihilation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Main activity
 */
public class MainActivity extends Activity {

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
        ad.loadAd();
//                break;
//        }
    }
}
