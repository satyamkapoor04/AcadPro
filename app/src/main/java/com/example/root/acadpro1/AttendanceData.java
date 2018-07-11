package com.example.root.acadpro1;

/**
 * Created by root on 1/10/17.
 */

public class AttendanceData {
    String date;
    String data;

    public AttendanceData() {

    }

    public AttendanceData (String date, String data) {
        this.date = date;
        this.data = data;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setData (String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}
