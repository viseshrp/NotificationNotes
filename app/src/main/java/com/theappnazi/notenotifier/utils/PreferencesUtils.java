package com.theappnazi.notenotifier.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by viseshprasad on 11/4/16.
 */

public class PreferencesUtils extends BasePreferenceUtils {

    static String fileName = Constants.USER_SETTINGS;

    public static boolean getBoolean(Context context, String keyId, boolean defaultValue) {
        return getBoolean(fileName, context, keyId, defaultValue);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setBoolean(Context context, String keyId, boolean value) {
        setBoolean(fileName, context, keyId, value);
    }

    public static int getInt(Context context, String keyId, int defaultValue) {
        return getInt(fileName, context, keyId, defaultValue);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setInt(Context context, String keyId, int value) {
        setInt(fileName, context, keyId, value);
    }

    public static long getLong(Context context, int keyId) {
        return getLong(fileName, context, keyId);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setLong(Context context, int keyId, long value) {
        setLong(fileName, context, keyId, value);
    }

    public static String getString(Context context, String keyId, String defaultValue) {
        return getString(fileName, context, keyId, defaultValue);

    }

    @SuppressLint("CommitPrefEdits")
    public static void setString(Context context, String keyId, String value) {
        setString(fileName, context, keyId, value);
    }

    @SuppressLint("CommitPrefEdits")
    public static void resetAll(Context context) {
        resetAll(fileName, context);
    }

}
