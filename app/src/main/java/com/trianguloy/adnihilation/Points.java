package com.trianguloy.adnihilation;

import android.app.Activity;
import android.widget.TextView;

public class Points {

    private int points;
    private TextView text;

    private Prefs prefs;

    public Points(Activity cntx) {
        prefs = new Prefs(cntx);
        points = prefs.getPoints();
        text = cntx.findViewById(R.id.points);
        update();
    }

    public void addPoints(int points){
        this.points += points;
        update();
    }

    private void update() {
        text.setText(Integer.toString(points));
    }

    public void save(){
        prefs.setPoints(points);
    }
}
