package com.theappnazi.notenotifier.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.theappnazi.notenotifier.data.NoteEntity
import org.junit.Rule
import org.junit.Test

class NoteAppTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyState_isDisplayed_whenNoNotes() {
        composeTestRule.setContent {
            NoteScreen(
                notes = emptyList(),
                onAddNote = { _, _, _ -> },
                onUpdateNote = {},
                onDeleteNote = {},
                onClearAll = {}
            )
        }

        composeTestRule.onNodeWithText("No notes yet. Add one!").assertIsDisplayed()
    }

    @Test
    fun noteList_isDisplayed_whenNotesExist() {
        val notes = listOf(
            NoteEntity(1, "Test Title", "Test Content", "2023-01-01", false, isActive = true)
        )

        composeTestRule.setContent {
            NoteScreen(
                notes = notes,
                onAddNote = { _, _, _ -> },
                onUpdateNote = {},
                onDeleteNote = {},
                onClearAll = {}
            )
        }

        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Content").assertIsDisplayed()
        composeTestRule.onNodeWithText("2023-01-01").assertIsDisplayed()
    }

    @Test
    fun addNoteDialog_opens_whenFabClicked() {
        composeTestRule.setContent {
            NoteScreen(
                notes = emptyList(),
                onAddNote = { _, _, _ -> },
                onUpdateNote = {},
                onDeleteNote = {},
                onClearAll = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        composeTestRule.onNodeWithText("Add Note").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Persistent Notification").assertIsDisplayed()
    }
}
