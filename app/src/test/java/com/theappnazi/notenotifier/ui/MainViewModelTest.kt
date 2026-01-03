package com.theappnazi.notenotifier.ui

import app.cash.turbine.test
import com.theappnazi.notenotifier.data.NoteEntity
import com.theappnazi.notenotifier.repository.NoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: NoteRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `notes flow emits data from repository`() = runTest {
        val notes = listOf(
            NoteEntity(1, "Title 1", "Content 1", "2023-01-01", false),
            NoteEntity(2, "Title 2", "Content 2", "2023-01-02", true)
        )
        val notesFlow = MutableStateFlow(notes)
        every { repository.allNotes } returns notesFlow

        viewModel = MainViewModel(repository)

        viewModel.notes.test {
            assertEquals(notes, awaitItem())
        }
    }

    @Test
    fun `addNote calls repository addNote`() = runTest {
        val notesFlow = MutableStateFlow(emptyList<NoteEntity>())
        every { repository.allNotes } returns notesFlow
        coEvery { repository.addNote(any(), any(), any()) } returns 1L

        viewModel = MainViewModel(repository)
        viewModel.addNote("Title", "Content", true)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.addNote("Title", "Content", true) }
    }

    @Test
    fun `deleteNote calls repository deleteNote`() = runTest {
        val note = NoteEntity(1, "Title", "Content", "Date", false)
        val notesFlow = MutableStateFlow(listOf(note))
        every { repository.allNotes } returns notesFlow
        coEvery { repository.deleteNote(note) } returns Unit

        viewModel = MainViewModel(repository)
        viewModel.deleteNote(note)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.deleteNote(note) }
    }
}
