package com.trianguloy.adnihilation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages the preferences
 */
class Prefs {

    /**
     * Preferences
     */
    private final SharedPreferences prefs;

    /**
     * Initializes
     */
    Prefs(Context cntx) {
        prefs = cntx.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
    }

    /**
     * Points
     */
    private static final String POINTS_KEY = "points";
    private static final int POINTS_DEFAULT = 0;
    void setPoints(int points){
        prefs.edit().putInt(POINTS_KEY, points).apply();
    }
    int getPoints(){
        return prefs.getInt(POINTS_KEY, POINTS_DEFAULT);
    }

}
