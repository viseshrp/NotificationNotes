package com.theappnazi.notenotifier.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.theappnazi.notenotifier.utils.AppConstants;

public class NotificationCancelReceiver extends BroadcastReceiver {
    public NotificationCancelReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.hasExtra(AppConstants.NOTIFICATION_ID)) {
            notificationManager.cancel(intent.getIntExtra(AppConstants.NOTIFICATION_ID, 0));
        }
    }
}
