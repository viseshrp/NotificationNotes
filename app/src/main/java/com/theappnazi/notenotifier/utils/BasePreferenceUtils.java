package com.theappnazi.notenotifier.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by viseshprasad on 11/4/16.
 */

public class BasePreferenceUtils {
    public static String getKey(Context context, int keyId) {
        return context.getString(keyId);
    }

    public static boolean getBoolean(String fileName, Context context, String keyId, boolean defaultValue) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(keyId, defaultValue);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setBoolean(String fileName, Context context, String keyId, boolean value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyId, value);
        editor.commit();
    }

    public static int getInt(String fileName, Context context, String keyId, int defaultValue) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyId, defaultValue);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setInt(String fileName, Context context, String keyId, int value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(keyId, value);
        editor.commit();
    }

    public static long getLong(String fileName, Context context, int keyId) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(getKey(context, keyId), -1L);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setLong(String fileName, Context context, int keyId, long value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(getKey(context, keyId), value);
        editor.commit();
    }

    public static String getString(String fileName, Context context, String keyId, String defaultValue) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyId, defaultValue);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setString(String fileName, Context context, String keyId, String value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyId, value);
        editor.commit();
    }

    @SuppressLint("CommitPrefEdits")
    public static void resetAll(String fileName, Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}