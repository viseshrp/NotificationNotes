package com.theappnazi.notificationnotes.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

//// TODO: 12/20/16 this service should check from prefutils string
// what note ids are currently in use and then notify those.
public class NotifierService extends Service {
    public NotifierService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
