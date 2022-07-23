package com.example.lw33;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NoteDB";

    public DBHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Notes.getCreateStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Notes.getCreateStatement());
    }

    //внутренний класс
    public static final class Notes {
        public static final String TABLE_NAME = "NoteTable";
        public static final String COLUMN_HEADER = "Header";
        public static final String COLUMN_TEXT = "Text";
        public static final String COLUMN_PRIORITY = "Priority";
        public static final String COLUMN_DATE = "Date";

        public static String getCreateStatement(){

            String state = String.format(
                    "CREATE TABLE %s(" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "%s TEXT, " +
                            "%s TEXT, " +
                            "%s INTEGER, " +
                            "%s INTEGER" +
                            ")",
                    TABLE_NAME,
                    BaseColumns._ID,
                    COLUMN_HEADER,
                    COLUMN_TEXT,
                    COLUMN_PRIORITY,
                    COLUMN_DATE
            );

            return state;
        }
        public static Note getNote(Cursor cursor){
            int ID = cursor.getColumnIndex(BaseColumns._ID),
                headerID = cursor.getColumnIndex(COLUMN_HEADER),
                textID = cursor.getColumnIndex(COLUMN_TEXT),
                priorityID = cursor.getColumnIndex(COLUMN_PRIORITY),
                dateID = cursor.getColumnIndex(COLUMN_DATE);

            int id = cursor.getInt(ID),
                priorityRange = cursor.getInt(priorityID);

            String header = cursor.getString(headerID),
                    text = cursor.getString(textID);

            Note.Priority priority = Note.Priority.getValueByRange(priorityRange);

            long date = cursor.getLong(dateID);

            return(new Note(id, header, text, priority, date));
        }

        public static long insertNote(SQLiteDatabase db, String header, String text,
                                      int priorityRange, long date){
            ContentValues values = new ContentValues();
            values.put(COLUMN_HEADER, header);
            values.put(COLUMN_TEXT, text);
            values.put(COLUMN_PRIORITY, priorityRange);
            values.put(COLUMN_DATE, date);
            return db.insert(TABLE_NAME, null, values);
        }

        public static long updateNote(SQLiteDatabase db, int id, String header, String text,
                                      int priorityRange, long date){
            ContentValues values = new ContentValues();
            values.put(COLUMN_HEADER, header);
            values.put(COLUMN_TEXT, text);
            values.put(COLUMN_PRIORITY, priorityRange);
            values.put(COLUMN_DATE, date);

            return db.update(TABLE_NAME, values, "_id = ?",
                    new String[]{Integer.toString(id)});
        }

        public static long deleteNote(SQLiteDatabase db, int id){
            return db.delete(TABLE_NAME,"_id = ?", new String[]{Integer.toString(id)});
        }
    }
}
