package com.example.root.acadpro1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/10/17.
 */

public class DataHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myattendancedata";
    private static final String TABLE_CONTACTS = "attendancedata";
    private static final String KEY_DATE = "date";
    private static final String KEY_DATA = "data";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "(" + KEY_DATE + " VARCHAR(20) PRIMARY KEY," + KEY_DATA + " VARCHAR(200)" +")";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);

    }

    public void addAttendanceData (AttendanceData attendanceData) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, attendanceData.getDate());
        values.put(KEY_DATA, attendanceData.getData());
        sqLiteDatabase.insert(TABLE_CONTACTS, null,values);
        sqLiteDatabase.close();
    }

    public AttendanceData getAttendanceData(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_DATE,
                        KEY_DATA }, KEY_DATE + "=?",
                new String[] { date }, null, null, null, null);
        if (cursor.getCount() == 0)
            return null;
        if (cursor != null)
            cursor.moveToFirst();


        AttendanceData attendanceData = new AttendanceData(cursor.getString(0),
                cursor.getString(1));
        // return contact
        return attendanceData;
    }

    public List<AttendanceData> getAllAttendanceData() {

        List<AttendanceData> contactList = new ArrayList<AttendanceData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AttendanceData contact = new AttendanceData();
                contact.setDate(cursor.getString(0));
                contact.setData(cursor.getString(1));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;

    }

    public int getAttendanceDataCount() {

        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();

    }

    public void updateAttendace(String date, int pos) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_DATE,
                        KEY_DATA }, KEY_DATE + "=?",
                new String[] { date }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        AttendanceData attendanceData = new AttendanceData(cursor.getString(0),
                cursor.getString(1));
        db.close();
        // return contact
        String data = attendanceData.getData();
        String updatedData;
        if (data.charAt(pos) == 1)
            updatedData = data.substring(0,pos) + '0' + data.substring(pos+1);
        else
            updatedData = data.substring(0,pos) + '1' + data.substring(pos+1);

        attendanceData.setData(updatedData);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CONTACTS, KEY_DATE + " = ?",
                new String[] { String.valueOf(attendanceData.getDate()) });

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, attendanceData.getDate());
        values.put(KEY_DATA, attendanceData.getData());
        sqLiteDatabase.insert(TABLE_CONTACTS, null,values);
        sqLiteDatabase.close();
    }

    public void updateData (String date, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "Update " + TABLE_CONTACTS + " set " + KEY_DATA +"='" + data + "' where " + KEY_DATE + "='" + date + "'" + "";
        db.execSQL(s);
        db.close();
    }

    public void deleteAttendance(AttendanceData contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_DATE + " = ?",
                new String[] { String.valueOf(contact.getDate()) });
        db.close();
    }


    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);

    }
}
