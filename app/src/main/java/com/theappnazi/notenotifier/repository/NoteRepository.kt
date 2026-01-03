package com.theappnazi.notenotifier.repository

import com.theappnazi.notenotifier.data.NoteDao
import com.theappnazi.notenotifier.data.NoteEntity
import com.theappnazi.notenotifier.utils.NotificationHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val notificationHelper: NotificationHelper
) {

    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun addNote(title: String, content: String, isPersistent: Boolean): Long {
        val note = NoteEntity(
            title = title,
            content = content,
            date = System.currentTimeMillis().toString(), // Storing as simple string for now, could be improved
            isPersistent = isPersistent,
            isActive = true
        )
        val id = noteDao.insertNote(note)
        notificationHelper.showNotification(id.toInt(), title, content, isPersistent)
        return id
    }

    suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
        if (note.isActive) {
            notificationHelper.showNotification(note.id, note.title, note.content, note.isPersistent)
        } else {
            notificationHelper.cancelNotification(note.id)
        }
    }

    suspend fun deactivateNote(id: Int) {
        val note = noteDao.getNoteById(id)
        if (note != null) {
            val updatedNote = note.copy(isActive = false)
            noteDao.updateNote(updatedNote)
            notificationHelper.cancelNotification(id)
        }
    }

    suspend fun deleteNote(note: NoteEntity) {
        notificationHelper.cancelNotification(note.id)
        noteDao.deleteNote(note)
    }

    suspend fun restoreNotifications() {
        val activeNotes = noteDao.getActiveNotes()
        activeNotes.forEach { note ->
            notificationHelper.showNotification(note.id, note.title, note.content, note.isPersistent)
        }
    }

    suspend fun clearAll() {
        // Cancel all notifications first
        val notes = noteDao.getActiveNotes()
        notes.forEach { notificationHelper.cancelNotification(it.id) }
        noteDao.deleteAllNotes()
    }
}
