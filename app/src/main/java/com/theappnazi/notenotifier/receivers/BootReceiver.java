package com.theappnazi.notenotifier.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.theappnazi.notenotifier.services.NotifierService;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, NotifierService.class);
            context.startService(serviceIntent);
        }

/*
        Intent mainIntent = new Intent(context, MainActivity.class);
        //Todo : instead start a service that checks what notifications are currently in use,
        // and then notifies user again on boot
        //http://stackoverflow.com/questions/6391902/how-to-start-an-application-on-startup
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
*/
    }
}
