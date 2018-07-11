package com.example.root.acadpro1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 11/11/17.
 */

public class NoteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mynote";

    private static final String TABLE_CONTACTS = "note";
    private static final String KEY_ID = "id";

    public NoteHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "(" + KEY_ID + " varchar(3000) PRIMARY KEY" + ")";
        db.execSQL(CREATE_CONTACT_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public void addData(String data) {
        String removeData = "DELETE FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(removeData);

        ContentValues values = new ContentValues();
        values.put(KEY_ID, data);
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public String getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID
                        }, null,
                null, null, null, null, null);
        String data = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            data = cursor.getString(0);
        }
        // return data
        return data;
    }
}
