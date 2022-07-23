package com.example.lw33;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class NoteBook extends Application {

    private DBHelper dbHelper;
    private Cursor cursor;

    public final int ADD_NOTE_CODE = 0x000312;
    public final int EDIT_NOTE_CODE = 0x000313;
    public final int DELETE_CODE = 0x000314;

    public final String NOTE_KEY = "note";

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new DBHelper(this);
        loadNotes();
    }


    public int getNotesCount() {
        return cursor.getCount();
    }

    public void loadNotes () {
        cursor = dbHelper.getReadableDatabase().query(DBHelper.Notes.TABLE_NAME, new String[] {"*"},
                null, null, null, null,
                DBHelper.Notes.COLUMN_PRIORITY+" DESC");
    }

    public Note getNote (int id){
        cursor.moveToPosition(id);
        return DBHelper.Notes.getNote(cursor);
    }

    public void addNote (Note note){
        DBHelper.Notes.insertNote(
                dbHelper.getWritableDatabase(),
                note.getHeader(), note.getText(), note.getPriority().getPriorityRange(),
                note.getDate().getTime()
        );
        loadNotes();
    }

    public void updateNote (Note note){
        DBHelper.Notes.updateNote(
                dbHelper.getWritableDatabase(),
                note.getID(), note.getHeader(), note.getText(), note.getPriority().getPriorityRange(),
                note.getDate().getTime()
        );
        loadNotes();
    }

    public void deleteNote(Note note) {
        DBHelper.Notes.deleteNote(dbHelper.getWritableDatabase(), note.getID());
        loadNotes();
    }
}