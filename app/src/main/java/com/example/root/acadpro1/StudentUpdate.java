package com.example.root.acadpro1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/10/17.
 */

public class StudentUpdate extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<String> ad;
    ArrayList<String> al;
    DatabaseHandler dh;
    DataHandler dl;
    List<Attendance> attendanceList;
    List<AttendanceData> attendanceDatas;
    int Datasize;
    int size;
    TableLayout tl;
    TableRow tr;
    int background;
    TextView dummy;
    float textSize;
    TextView textupdate;
    AttendanceData attendance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.student_update);

        dummy = (TextView) findViewById(R.id.textView);
        textupdate = (TextView) findViewById(R.id.textupdate);
        textSize = dummy.getTextSize();

        if (textSize > 20)
            textSize = 19;

        spinner = (Spinner) findViewById(R.id.spinner);

        dh = new DatabaseHandler(this);
        attendanceList = dh.getAllAttendance();

        dl = new DataHandler(this);
        attendanceDatas = dl.getAllAttendanceData();

        Datasize = attendanceDatas.size();
        size = attendanceList.size();

        al = new ArrayList<>();

        al.add(new String("Select Here"));

        for (int i=0; i<size; i++) {
            String s = attendanceList.get(i).getSection() + " : " + attendanceList.get(i).getRoll() + " : " + attendanceList.get(i).getName();
            al.add(s);
        }

        ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, al);
        spinner.setAdapter(ad);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cleanTable(tl);
                if (position>0) {
                    float f = 100;
                    if (Datasize != 0) {
                        f = ((attendanceList.get(position-1).getAttain())*100)/(Datasize);
                    }
                    textupdate.setText("Total days : " + Integer.toString(Datasize) + "  Total attendance : " + attendanceList.get(position-1).getAttain() + "  Percentage : " + f + "%");
                    addData(position-1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tl = (TableLayout) findViewById(R.id.student_table);
        addHeader();


    }

    public void addHeader() {
        tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
        TextView t1 = new TextView(this);
        //t1.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
        t1.setTextSize(textSize);
        t1.setText("DATE");
        t1.setTextColor(Color.WHITE);
        t1.setBackgroundResource(R.drawable.cell_shape);
        t1.setPadding(5, 0, 0, 0);
        t1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(t1); // Adding textView to tablerow.

        TextView t2 = new TextView(this);
        //t2.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
        t2.setTextSize(textSize);
        t2.setText("ATTENDANCE");
        t2.setTextColor(Color.WHITE);
        t2.setBackgroundResource(R.drawable.cell_shape);
        t2.setPadding(5, 0, 0, 0);
        t2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(t2); // Adding textView to tablerow.


        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }

    public void addData(int position) {

        for (int i=0; i<Datasize; i++) {
            attendance = attendanceDatas.get(i);
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.setBackgroundResource(R.drawable.cell_shape);

            /** Creating another textview **/
            TextView t1 = new TextView(this);
            //t1.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
            t1.setTextSize(textSize);
            t1.setText(attendance.getDate());
            t1.setTextColor(Color.WHITE);
            //t1.setBackgroundResource(R.drawable.cell_shape);
            t1.setPadding(5, 0, 0, 0);
            t1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(t1); // Adding textView to tablerow.

            TextView t2 = new TextView(this);
            //t2.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
            t2.setTextSize(textSize);
            if (attendance.getData().charAt(position) == '0')
                t2.setText("ABSENT");
            else
                t2.setText("PRESENT");
            t2.setTextColor(Color.WHITE);
            //t2.setBackgroundResource(R.drawable.cell_shape);
            t2.setPadding(5, 0, 0, 0);
            t2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(t2); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }
}
