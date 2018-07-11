package com.example.root.acadpro1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 29/9/17.
 */

public class GetAttendance extends AppCompatActivity {

    DatabaseHandler dh;
    List<Attendance> attendanceList;
    DataHandler dhl;
    TableLayout tl;
    TableRow tr;
    int textSizeInSp;
    Object tag;
    int backroundId;
    AppCompatCheckBox[] cb;
    String data;
    StringBuilder builder;
    String date;
    int size;
    boolean toggle;
    boolean isPresent;
    boolean saved;
    TextView dummy;
    float textSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.get_attendance);

        dummy = (TextView) findViewById(R.id.textView);
        textSize = dummy.getTextSize();
        if (textSize > 20)
            textSize = 19;

        tl = (TableLayout) findViewById(R.id.attain_table);
        //textSizeInSp = (int) getResources().getDimension(R.dimen.text_size);
        backroundId = R.drawable.cell_shape;
        saved = false;

        Bundle b = getIntent().getExtras();
        date = b.getString("date");
        isPresent = b.getBoolean("isPresent");

        dh = new DatabaseHandler(this);
        dhl = new DataHandler(this);

        attendanceList = dh.getAllAttendance();
        size = attendanceList.size();
        cb = new AppCompatCheckBox[size];
        toggle = false;

        addHeader();
        if (!isPresent)
            addData();
        else
            addAnotherData();

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

            tag = tr.getTag();

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

            final int j = i;
            AppCompatCheckBox c = new AppCompatCheckBox(this);
            c.setButtonDrawable(R.drawable.cb_selector);

            cb[j] = c;
            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //Toast.makeText(GetAttendance.this, attendanceList.get(j).getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tr.addView(c);

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }


    public void addAnotherData() {
        AttendanceData attendanceData = dhl.getAttendanceData(date);
        String str = attendanceData.getData();

        for (int i=0; i<size; i++) {
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.setBackgroundResource(R.drawable.cell_shape);

            tag = tr.getTag();

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

            final int j = i;
            AppCompatCheckBox c = new AppCompatCheckBox(this);
            c.setButtonDrawable(R.drawable.cb_selector);

            if (str.charAt(j) == '1')
                c.setChecked(true);
            cb[j] = c;
            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //Toast.makeText(GetAttendance.this, attendanceList.get(j).getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tr.addView(c);

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }


    public static float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            if (!toggle) {
                for (CheckBox c : cb) {
                    c.setChecked(true);
                }
                item.setTitle("UNSELECT ALL");
                toggle = true;
            } else {
                for (CheckBox c : cb) {
                    c.setChecked(false);
                }
                item.setTitle("SELECT ALL");
                toggle = false;
            }
            return true;
        } else if (id == R.id.item2) {
            builder = new StringBuilder(size);
            if (!isPresent && !saved) {
                int count = 0;
                for (CheckBox c : cb) {
                    if (c.isChecked()) {
                        builder.append("1");
                        dh.updateAttendace(count);
                    } else {
                        builder.append("0");
                    }
                    count++;
                }
            } else {
                AttendanceData attendanceData = dhl.getAttendanceData(date);
                String str = attendanceData.getData();

                for (int i=0; i<size; i++) {
                    if (str.charAt(i) == '1' && !(cb[i].isChecked())) {
                        dh.downgradeAttendace(i);
                    } else if (str.charAt(i) == '0' && cb[i].isChecked()) {
                        dh.updateAttendace(i);
                    }
                }

                for (CheckBox c : cb) {
                    if (c.isChecked()) {
                        builder.append("1");
                    } else {
                        builder.append("0");
                    }
                }
            }
            data = builder.toString();
            AttendanceData atten;
            atten = new AttendanceData(date, data);
            if (!isPresent && !saved)
                dhl.addAttendanceData(atten);
            else
                dhl.updateData(date,data);

            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
            saved = true;

            return true;
        } else if (id == R.id.item3) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
