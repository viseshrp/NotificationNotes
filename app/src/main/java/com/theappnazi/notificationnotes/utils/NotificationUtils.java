package com.theappnazi.notificationnotes.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.theappnazi.notificationnotes.R;

import java.util.Random;

/**
 * Created by vises_000 on 4/3/2016.
 */
public class NotificationUtils {

    public static void showNewNoteNotification(Context context, Class T, String title, String content, boolean isPersistent) {

        int notificationId = getNotificationId();

        Intent intent = new Intent(context, T);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(Intent.ACTION_MAIN);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, getRequestCode(),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        showNotification(context, pendingIntent, title, content, notificationId, true, isPersistent);
    }

    private static void showNotification(Context context, PendingIntent pendingIntent, String title,
                                         String content, int notificationId, boolean playSound, boolean isPersistent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.navigation_accept_dark)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(isPersistent)
                .addAction(R.drawable.navigation_accept_dark, "READ", getReadPendingIntent(context, notificationId));

        if (playSound) {
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }

        Notification notification = builder.build();

        notificationManager.notify(notificationId, notification);
    }

    private static int getNotificationId() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }

    private static int getRequestCode() {
        Random random = new Random();
        return random.nextInt(99999 - 10000) + 10000;
    }

    private static PendingIntent getReadPendingIntent(Context context, int notificationId) {
        Intent intent = new Intent("com.theappnazi.notificationnotes.intent.NOTE_MARKED_READ");
        intent.putExtra(AppConstants.NOTIFICATION_ID, notificationId);
        return PendingIntent.getBroadcast(context, getRequestCode(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
