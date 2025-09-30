package com.own.babykickcounter.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil instance;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    private SharedPreferenceUtil() {
    }

    public void init(Context context) {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = this.mSharedPreferences.edit();
    }

    public static SharedPreferenceUtil getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceUtil();
        }
        return instance;
    }

    public void putFloat(String str, float f) {
        this.mEditor.putFloat(str, f);
        this.mEditor.commit();
    }

    public void putString(String str, String str2) {
        this.mEditor.putString(str, str2);
        this.mEditor.commit();
    }

    public void putInt(String str, int i) {
        this.mEditor.putInt(str, i);
        this.mEditor.commit();
    }

    public void putLong(String str, long j) {
        this.mEditor.putLong(str, j);
        this.mEditor.commit();
    }

    public void putBoolean(String str, boolean z) {
        this.mEditor.putBoolean(str, z);
        this.mEditor.commit();
    }

    public float getFloat(String str) {
        return this.mSharedPreferences.getFloat(str, -1.0f);
    }

    public String getString(String str) {
        return this.mSharedPreferences.getString(str, "");
    }

    public int getInt(String str) {
        return this.mSharedPreferences.getInt(str, -1);
    }

    public int getInt(String str, int i) {
        return this.mSharedPreferences.getInt(str, i);
    }

    public long getLong(String str) {
        return this.mSharedPreferences.getLong(str, -1);
    }

    public long getLong(String str, int i) {
        return this.mSharedPreferences.getLong(str, (long) i);
    }

    public boolean getBoolean(String str) {
        return this.mSharedPreferences.getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        return this.mSharedPreferences.getBoolean(str, z);
    }
}
