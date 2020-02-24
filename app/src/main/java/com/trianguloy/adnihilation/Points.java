package com.trianguloy.adnihilation;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
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

        // set color
        final int b_color = getBackgroundColor(text.getRootView());
        text.setBackgroundColor(Color.argb(125,Color.red(b_color),Color.green(b_color),Color.blue(b_color)));

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


    // ------------------- utils -------------------

    /**
     * Returns the view background color
     * @param view view
     * @return color
     */
    private static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            return ((ColorDrawable) drawable).getColor();
        }
        return Color.GRAY;
    }
}
