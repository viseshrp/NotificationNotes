package com.theappnazi.notificationnotes.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.theappnazi.notificationnotes.helpers.NoteHelper;
import com.theappnazi.notificationnotes.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vises_000 on 4/6/2016.
 */
public class NoteDataSource {

    private SQLiteDatabase database;
    private NoteHelper noteHelper;
    private String[] allColumns = {NoteHelper.ID, NoteHelper.NOTIFICATION_TITLE,
            NoteHelper.NOTIFICATION_CONTENT, NoteHelper.NOTE_DATE};

    public NoteDataSource(Context context) {
        noteHelper = new NoteHelper(context);
    }

    public void open() {
        database = noteHelper.getWritableDatabase();
    }

    public void close() {
        noteHelper.close();
    }

    public Note createNote(String notification_title, String notification_content, String note_date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteHelper.NOTIFICATION_TITLE, notification_title);
        contentValues.put(NoteHelper.NOTIFICATION_CONTENT, notification_content);
        contentValues.put(NoteHelper.NOTE_DATE, note_date);

        long insertId = database.insert(NoteHelper.TABLE_NAME, null, contentValues);

        Cursor cursor = database.query(NoteHelper.TABLE_NAME, allColumns, NoteHelper.ID
                + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    public void deleteNote(Note note) {
        long id = note.getId();
        database.delete(NoteHelper.TABLE_NAME, NoteHelper.ID + " = " + id, null);
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = database.query(NoteHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            noteList.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return noteList;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(NoteHelper.ID)));
        note.setNotification_title(cursor.getString(cursor.getColumnIndexOrThrow(NoteHelper.NOTIFICATION_TITLE)));
        note.setNotification_content(cursor.getString(cursor.getColumnIndexOrThrow(NoteHelper.NOTIFICATION_CONTENT)));
        note.setNote_date(cursor.getString(cursor.getColumnIndexOrThrow(NoteHelper.NOTE_DATE)));

        return note;
    }
}
