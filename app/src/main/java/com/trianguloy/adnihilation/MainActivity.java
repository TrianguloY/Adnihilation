package com.trianguloy.adnihilation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity  {


    private Ad ad;
    private Points points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ad = findViewById(R.id.ad);
        points = new Points(this);
        ad.setPoints(points);
    }

    @Override
    protected void onPause() {
        super.onPause();
        points.save();
    }

    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.regenerate:
                ad.loadAd();
                break;
        }
    }
}
