package com.example.root.acadpro1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 3/10/17.
 */

public class ReportAttendance extends AppCompatActivity {

    DatabaseHandler dh;
    List<Attendance> attendanceList;
    TableLayout tl;
    TableRow tr;
    int textSizeInSp;
    int backroundId;
    int size;
    float textSize;
    TextView dummy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.report_attendance);

        dummy = (TextView) findViewById(R.id.textView);
        textSize = dummy.getTextSize();

        if (textSize > 20)
            textSize = 19;
        tl = (TableLayout) findViewById(R.id.report_table);
        //textSizeInSp = (int) getResources().getDimension(R.dimen.text_size);
        backroundId = R.drawable.cell_shape;

        dh = new DatabaseHandler(this);
        attendanceList = dh.getAllAttendance();
        size = attendanceList.size();

        addHeader();
        addData();
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
        t1.setText("ID");
        t1.setTextColor(Color.WHITE);
        t1.setBackgroundResource(R.drawable.cell_shape);
        t1.setPadding(5, 0, 0, 0);
        t1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(t1); // Adding textView to tablerow.

        TextView t2 = new TextView(this);
        //t2.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
        t2.setTextSize(textSize);
        t2.setText("SECTION");
        t2.setTextColor(Color.WHITE);
        t2.setBackgroundResource(R.drawable.cell_shape);
        t2.setPadding(5, 0, 0, 0);
        t2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(t2); // Adding textView to tablerow.

        TextView t3 = new TextView(this);
        //t3.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
        t3.setTextSize(textSize);
        t3.setText("NAME");
        t3.setTextColor(Color.WHITE);
        t3.setBackgroundResource(R.drawable.cell_shape);
        t3.setPadding(5, 0, 0, 0);
        t3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(t3); // Adding textView to tablerow.

        TextView t4 = new TextView(this);
        //t4.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
        t4.setTextSize(textSize);
        t4.setText("ROLL");
        t4.setTextColor(Color.WHITE);
        t4.setBackgroundResource(R.drawable.cell_shape);
        t4.setPadding(5, 0, 0, 0);
        t4.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(t4); // Adding textView to tablerow.

        TextView t5 = new TextView(this);
        //t5.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
        t5.setTextSize(textSize);
        t5.setText("Attendance");
        t5.setTextColor(Color.WHITE);
        t5.setBackgroundResource(R.drawable.cell_shape);
        t5.setPadding(5, 0, 0, 0);
        t5.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(t5); // Adding textView to tablerow.


        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


    }

    public void addData() {

        for (int i=0; i<size; i++) {
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.setBackgroundResource(R.drawable.cell_shape);

            /** Creating another textview **/
            TextView t1 = new TextView(this);
            //t1.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
            t1.setTextSize(textSize);
            t1.setText(String.valueOf(attendanceList.get(i).getId()));
            t1.setTextColor(Color.WHITE);
            //t1.setBackgroundResource(R.drawable.cell_shape);
            t1.setPadding(5, 0, 0, 0);
            t1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(t1); // Adding textView to tablerow.

            TextView t2 = new TextView(this);
            //t2.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
            t2.setTextSize(textSize);
            t2.setText(attendanceList.get(i).getSection());
            t2.setTextColor(Color.WHITE);
            //t2.setBackgroundResource(R.drawable.cell_shape);
            t2.setPadding(5, 0, 0, 0);
            t2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(t2); // Adding textView to tablerow.

            TextView t3 = new TextView(this);
            //t3.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
            t3.setTextSize(textSize);
            t3.setText(attendanceList.get(i).getName());
            t3.setTextColor(Color.WHITE);
            //t3.setBackgroundResource(R.drawable.cell_shape);
            t3.setPadding(5, 0, 0, 0);
            t3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(t3); // Adding textView to tablerow.

            TextView t4 = new TextView(this);
            //t4.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
            t4.setTextSize(textSize);
            t4.setText(attendanceList.get(i).getRoll());
            t4.setTextColor(Color.WHITE);
            //t4.setBackgroundResource(R.drawable.cell_shape);
            t4.setPadding(5, 0, 0, 0);
            t4.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(t4); // Adding textView to tablerow.

            TextView t5 = new TextView(this);
            //t5.setTextSize(convertSpToPixels(textSizeInSp , getApplicationContext()));
            t5.setTextSize(textSize);
            t5.setText(String.valueOf(attendanceList.get(i).getAttain()));
            t5.setTextColor(Color.WHITE);
            //t4.setBackgroundResource(R.drawable.cell_shape);
            t5.setPadding(5, 0, 0, 0);
            t5.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(t5); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public static float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

}
