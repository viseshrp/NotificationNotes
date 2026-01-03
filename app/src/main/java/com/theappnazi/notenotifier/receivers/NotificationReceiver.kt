package com.theappnazi.notenotifier.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.theappnazi.notenotifier.repository.NoteRepository
import com.theappnazi.notenotifier.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: NoteRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == NotificationHelper.ACTION_DONE) {
            val noteId = intent.getIntExtra(NotificationHelper.EXTRA_NOTE_ID, -1)
            if (noteId != -1) {
                val pendingResult = goAsync()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        repository.deactivateNote(noteId)
                    } finally {
                        pendingResult.finish()
                    }
                }
            }
        }
    }
}
