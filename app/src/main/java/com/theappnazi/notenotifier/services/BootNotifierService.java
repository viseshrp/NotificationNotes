package com.theappnazi.notenotifier.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.theappnazi.notenotifier.utils.AppConstants;
import com.theappnazi.notenotifier.utils.NotificationUtils;
import com.theappnazi.notenotifier.utils.PreferencesUtils;

public class BootNotifierService extends Service {
    public BootNotifierService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (PreferencesUtils.getBoolean(this, AppConstants.IS_SET_BOOT_ENABLED, true))
            NotificationUtils.setupNotifications(BootNotifierService.this);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
