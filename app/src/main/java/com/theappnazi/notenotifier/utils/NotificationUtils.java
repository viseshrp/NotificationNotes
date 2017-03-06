package com.theappnazi.notenotifier.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theappnazi.notenotifier.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by vises_000 on 4/3/2016.
 */
public class NotificationUtils {

    public static void showNewNoteNotification(Context context, Class T, int notificationId, String title, String content, boolean isPersistent) {

        Intent intent = new Intent(context, T);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(Intent.ACTION_MAIN);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, getRequestCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        showNotification(context, pendingIntent, title, content, notificationId, true, isPersistent);
    }

    private static void showNotification(Context context, PendingIntent pendingIntent, String title,
                                         String content, int notificationId, boolean playSound, boolean isPersistent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.navigation_accept_dark)
                //.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.navigation_accept_dark))
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(isPersistent)
                .addAction(R.drawable.checkmark_24, "Done", getReadPendingIntent(context, notificationId));

        if (playSound) {
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }

        Notification notification = builder.build();

        notificationManager.notify(notificationId, notification);
    }

    public static int getNotificationId() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }

    public static int getRequestCode() {
        Random random = new Random();
        return random.nextInt(99999 - 10000) + 10000;
    }

    private static PendingIntent getReadPendingIntent(Context context, int notificationId) {
        Intent intent = new Intent("com.theappnazi.notenotifier.intent.NOTE_MARKED_READ");
        intent.putExtra(AppConstants.NOTIFICATION_ID, notificationId);
        return PendingIntent.getBroadcast(context, getRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static ArrayList<Integer> getCurrentList(Context context) {
        String json = PreferencesUtils.getString(context, AppConstants.CURRENT_NOTIF_LIST, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();

        return gson.fromJson(json, type);
    }

    public static void addToCurrentList(Context context, int notificationId, boolean isNew) {
        String json = PreferencesUtils.getString(context, AppConstants.CURRENT_NOTIF_LIST, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> currentNotifList;

        if (json != null && !json.equals("")) {
            //get arraylist from json
            currentNotifList = gson.fromJson(json, type);
        } else {
            //create a new arraylist and dump it back into sharedprefs
            currentNotifList = new ArrayList<>();
        }

        Collections.sort(currentNotifList);

        if (!isNew) {
            //check if it is a current notification
            if (!(Collections.binarySearch(currentNotifList, notificationId) >= 0)) // is not found in curr list
            {
                //add to current list
                currentNotifList.add(notificationId);
            }
        } else { //if notif is new
            currentNotifList.add(notificationId);
        }

        json = gson.toJson(currentNotifList);
        PreferencesUtils.setString(context, AppConstants.CURRENT_NOTIF_LIST, json);
    }

    public static void deleteFromCurrentList(Context context, int notificationId) {
        String json = PreferencesUtils.getString(context, AppConstants.CURRENT_NOTIF_LIST, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> currentNotifList = null;
        int index = 0;

        if (json != null && !json.equals("")) {
            //get arraylist from json
            currentNotifList = gson.fromJson(json, type);
        }

        if (currentNotifList != null) {
            index = Collections.binarySearch(currentNotifList, notificationId);

            if (index >= 0) // is found in curr list
            {
                //delete from current list
                currentNotifList.remove(index);
            }
        }

        json = gson.toJson(currentNotifList);
        PreferencesUtils.setString(context, AppConstants.CURRENT_NOTIF_LIST, json);
    }


}
