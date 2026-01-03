package com.theappnazi.notenotifier.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.theappnazi.notenotifier.repository.NoteRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: NoteRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val pendingResult = goAsync()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    repository.restoreNotifications()
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}
