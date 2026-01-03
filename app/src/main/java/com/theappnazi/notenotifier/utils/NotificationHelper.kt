package com.theappnazi.notenotifier.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.theappnazi.notenotifier.R
import com.theappnazi.notenotifier.receivers.NotificationReceiver

class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "note_notifier_channel"
        const val CHANNEL_NAME = "Note Notifier"
        const val ACTION_DONE = "com.theappnazi.notenotifier.ACTION_DONE"
        const val EXTRA_NOTE_ID = "extra_note_id"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications for your notes"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(id: Int, title: String, content: String, isPersistent: Boolean) {
        val doneIntent = Intent(context, NotificationReceiver::class.java).apply {
            action = ACTION_DONE
            putExtra(EXTRA_NOTE_ID, id)
        }

        // Use FLAG_IMMUTABLE for Android 12+ compatibility
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val donePendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            doneIntent,
            pendingIntentFlag
        )

        // Intent to launch the app when notification is clicked
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val launchPendingIntent = if (launchIntent != null) {
            PendingIntent.getActivity(context, 0, launchIntent, pendingIntentFlag)
        } else null

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.navigation_accept_dark) // Reusing existing icon resource
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(isPersistent)
            .setAutoCancel(false) // User must click "Done" or swipe (if not persistent)
            .setContentIntent(launchPendingIntent)
            .addAction(R.drawable.checkmark_24, "Done", donePendingIntent)

        notificationManager.notify(id, builder.build())
    }

    fun cancelNotification(id: Int) {
        notificationManager.cancel(id)
    }
}
