package com.nfc.scan.mfccardscan.lib.util;

import android.content.Context;


public class SharedPrefUtil {

    private static final String PREF_APP = "pref_lib";
    private static final String STAN = "stan";

    public static int getStan(Context context) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(STAN, 1);
    }

    public static void saveStan(Context context) {
        int currentStan = getStan(context);
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(STAN, currentStan + 1).apply();
    }
}
