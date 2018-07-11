package com.example.root.acadpro1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 29/9/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myattendance";

    private static final String TABLE_CONTACTS = "attendance";
    private static final String KEY_ID = "id";
    private static final String KEY_SECTION = "section";
    private static final String KEY_NAME = "name";
    private static final String KEY_ROLL = "roll";
    private static final String KEY_ATTAIN = "attain";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "(" + KEY_ID + " INETGER PRIMARY KEY," + KEY_SECTION + " TEXT," + KEY_NAME + " TEXT," + KEY_ROLL + " TEXT," + KEY_ATTAIN + " TEXT" + ")";
        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);

    }

    public void addAttendace(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, attendance.getId());
        values.put(KEY_SECTION, attendance.getSection());
        values.put(KEY_NAME, attendance.getName());
        values.put(KEY_ROLL, attendance.getRoll());
        values.put(KEY_ATTAIN, attendance.getAttain());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Attendance getAttendance(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_SECTION, KEY_NAME, KEY_ROLL, KEY_ATTAIN }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        Attendance attendance = new Attendance(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
        // return contact
        return attendance;
    }

    public List<Attendance> getAllAttendance() {

        List<Attendance> contactList = new ArrayList<Attendance>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Attendance contact = new Attendance();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setSection(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setRoll(cursor.getString(3));
                contact.setAttain(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;

    }

    public int getAttendanceCount() {

        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();

    }

    public void updateAttendace(int id) {
        id = id+1;
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "Update " + TABLE_CONTACTS + " set " + KEY_ATTAIN +"=" + KEY_ATTAIN + "+1 where " + KEY_ID + "=" + id + "";
        db.execSQL(s);
        db.close();

    }

    public void downgradeAttendace(int id) {
        id = id+1;
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "Update " + TABLE_CONTACTS + " set " + KEY_ATTAIN +"=" + KEY_ATTAIN + "-1 where " + KEY_ID + "=" + id + "";
        db.execSQL(s);
        db.close();

    }

    public void deleteAttendance(Attendance contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);

    }
}
