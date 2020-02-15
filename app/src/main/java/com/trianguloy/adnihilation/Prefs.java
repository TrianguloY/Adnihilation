package com.trianguloy.adnihilation;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private final SharedPreferences prefs;

    public Prefs(Context cntx) {
        prefs = cntx.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
    }

    private static final String POINTS_KEY = "points";
    private static final int POINTS_DEFAULT = 0;
    public void setPoints(int points){
        prefs.edit().putInt(POINTS_KEY, points).apply();
    }
    public int getPoints(){
        return prefs.getInt(POINTS_KEY, POINTS_DEFAULT);
    }

}
