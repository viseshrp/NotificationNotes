package com.theappnazi.notenotifier.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.theappnazi.notenotifier.models.Note;
import com.theappnazi.notenotifier.ui.MainActivity;
import com.theappnazi.notenotifier.utils.NotificationUtils;

import java.util.ArrayList;

public class NotifierService extends Service {
    public NotifierService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationUtils.setupNotifications(NotifierService.this);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
