package com.trianguloy.adnihilation;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

class AdsList {
    private static final String AD_PREFIX = "ad_"; // ads must have this prefix
    private static final String AD_TEST = "test_ad"; // the test ad

    /**
     * List of ads identifiers
     */
    private LinkedList<Integer> adsIds = new LinkedList<>();

    /**
     * Index of test ad
     */
    private int testAd;

    /**
     * Random utils
     */
    private Random random = new Random();

    /**
     * Constructor
     */
    AdsList() {
        // clear just in case
        adsIds.clear();

        // foreach drawable, get the ads
        Field[] fields = R.drawable.class.getDeclaredFields();
        R.drawable drawableResources = new R.drawable();
        for (Field field : fields) {
            try {
                final int ad_id = field.getInt(drawableResources);
                if (field.getName().startsWith(AD_PREFIX)) {
                    // ads start with 'ad_'
                    adsIds.add(ad_id);
                }
                if (field.getName().equals(AD_TEST)) {
                    // test ad is always the first to load
                    testAd = ad_id;
                }
            } catch (Exception ignored) {
            }
        }
        Collections.shuffle(adsIds);
    }

    /**
     * @return the test ad id
     */
    int getTestAd() {
        return testAd;
    }

    /**
     * @return a random ad id (ensures the probability of getting a recent one is low)
     */
    int getRandomAd() {
        // get random (first are more probable)
        int index = 0;
        while(index < adsIds.size() - 1 && random.nextBoolean()){
            index++;
        }
        int ad = adsIds.remove(index);

        // add to back and return
        adsIds.addLast(ad);
        return ad;
    }

}
