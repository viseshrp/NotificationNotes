package com.theappnazi.notificationnotes.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vises_000 on 4/6/2016.
 */
public class NoteHelper extends SQLiteOpenHelper {

    //Database Related Constants
    private static final String DATABASE_NAME = "noteshistory";
    public static final String TABLE_NAME = "notes";
    private static final int DATABASE_VERSION = 1;

    //Database Columns
    public static final String ID = "_id";
    public static final String NOTIFICATION_TITLE = "notification_title";
    public static final String NOTIFICATION_CONTENT = "notification_content";
    public static final String NOTE_DATE = "note_date";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + ID
            + " integer primary key autoincrement, " + NOTIFICATION_TITLE + " text, "
            + NOTIFICATION_CONTENT + " text, " + NOTE_DATE + " text);";
    private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public NoteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
