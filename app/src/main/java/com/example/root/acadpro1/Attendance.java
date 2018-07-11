package com.example.root.acadpro1;

/**
 * Created by root on 28/9/17.
 */

public class Attendance {
    int id;
    String section;
    String name;
    String roll;
    int attain;

    public Attendance() {

    }

    public Attendance (int id, String section, String name, String roll, int attain) {
        this.id  = id;
        this.section = section;;
        this.name = name;
        this.roll = roll;
        this.attain = attain;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getSection () {
        return this.section;
    }

    public void setSection (String section) {
        this.section = section;
    }

    public String getRoll () {
        return this.roll;
    }

    public void setRoll (String roll) {
        this.roll = roll;
    }

    public int getAttain () {
        return this.attain;
    }

    public void setAttain (int attain) {
        this.attain = attain;
    }
}
