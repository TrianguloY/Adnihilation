package com.trianguloy.adnihilation;

import android.app.Activity;
import android.widget.TextView;

/**
 * Manages the points
 */
class Points {

    /**
     * Saved points
     */
    private int points;

    /**
     * Where to display the points
     */
    private TextView text;

    /**
     * To store/retrieve the points
     */
    private Prefs prefs;

    /**
     * Initialize
     */
    Points(Activity cntx) {
        // load
        prefs = new Prefs(cntx);
        points = prefs.getPoints();
        text = cntx.findViewById(R.id.points);

        // show
        update();
    }

    /**
     * Add the desired points
     * @param points points to add
     */
    void addPoints(int points){
        this.points += points;
        update();
    }

    /**
     * Updates the points display
     */
    private void update() {
        text.setText(Integer.toString(points));
    }

    /**
     * Saves the points
     */
    void save(){
        prefs.setPoints(points);
    }
}
