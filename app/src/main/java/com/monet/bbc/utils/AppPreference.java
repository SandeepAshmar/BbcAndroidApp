package com.monet.bbc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference  {
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor mPrefsEditor;

    public static String getImageURL(Context ctx) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return mPrefs.getString("img_url", "");
    }

    public static void setImageURL(Context ctx, String value) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("img_url", value);
        mPrefsEditor.apply();
    }

    public static void clearAllPreferences(Context ctx) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.clear();
        mPrefsEditor.commit();
    }
}
