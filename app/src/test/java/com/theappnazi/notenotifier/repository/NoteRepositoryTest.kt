package com.theappnazi.notenotifier.repository

import com.theappnazi.notenotifier.data.NoteDao
import com.theappnazi.notenotifier.data.NoteEntity
import com.theappnazi.notenotifier.utils.NotificationHelper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteRepositoryTest {

    private lateinit var repository: NoteRepository
    private lateinit var noteDao: NoteDao
    private lateinit var notificationHelper: NotificationHelper

    @Before
    fun setup() {
        noteDao = mockk(relaxed = true)
        notificationHelper = mockk(relaxed = true)
        repository = NoteRepository(noteDao, notificationHelper)
    }

    @Test
    fun `addNote inserts into dao and shows notification`() = runTest {
        val title = "Test Note"
        val content = "Test Content"
        val isPersistent = true
        val generatedId = 100L

        coEvery { noteDao.insertNote(any()) } returns generatedId

        repository.addNote(title, content, isPersistent)

        coVerify { noteDao.insertNote(match { it.title == title && it.content == content && it.isPersistent == isPersistent && it.isActive }) }
        verify { notificationHelper.showNotification(generatedId.toInt(), title, content, isPersistent) }
    }

    @Test
    fun `deactivateNote updates note and cancels notification`() = runTest {
        val noteId = 1
        val note = NoteEntity(noteId, "Title", "Content", "Date", false, isActive = true)

        coEvery { noteDao.getNoteById(noteId) } returns note

        repository.deactivateNote(noteId)

        coVerify { noteDao.updateNote(match { it.id == noteId && !it.isActive }) }
        verify { notificationHelper.cancelNotification(noteId) }
    }

    @Test
    fun `restoreNotifications shows notifications for all active notes`() = runTest {
        val activeNotes = listOf(
            NoteEntity(1, "Title 1", "Content 1", "Date", true, isActive = true),
            NoteEntity(2, "Title 2", "Content 2", "Date", false, isActive = true)
        )
        coEvery { noteDao.getActiveNotes() } returns activeNotes

        repository.restoreNotifications()

        verify { notificationHelper.showNotification(1, "Title 1", "Content 1", true) }
        verify { notificationHelper.showNotification(2, "Title 2", "Content 2", false) }
    }
}
