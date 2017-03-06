package com.theappnazi.notenotifier.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.theappnazi.notenotifier.models.Note;
import com.theappnazi.notenotifier.ui.MainActivity;
import com.theappnazi.notenotifier.utils.NotificationUtils;

import java.util.ArrayList;

//// TODO: 12/20/16 this service should check from prefutils string
// what note ids are currently in use and then notify those.
public class NotifierService extends Service {
    public NotifierService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private void setupNotifications(){
        ArrayList<Integer> currentList = NotificationUtils.getCurrentList(this);
        for(Integer notificationId : currentList) {
            Note note = Note.find(Note.class, "notification_Id = ?", String.valueOf(notificationId)).get(0);
            String notificationTitle = note.getNotification_title();
            String notificationContent = note.getNotification_content();
            boolean isPersistent = note.isPersistent();

            NotificationUtils.showNewNoteNotification(this, MainActivity.class, notificationId, notificationTitle, notificationContent, isPersistent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupNotifications();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
