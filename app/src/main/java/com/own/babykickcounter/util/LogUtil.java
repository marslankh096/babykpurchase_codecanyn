package com.own.babykickcounter.util;

import android.util.Log;

public class LogUtil {
    private static final String tagg = "Minato_";

    public static void i(String str, String str2) {
        Log.i(tagg + str, str2);
    }

    public static void e(String str, String str2) {
        Log.e(tagg + str, str2);
    }

    public static void d(String str, String str2) {
        Log.d(tagg + str, str2);
    }
}
