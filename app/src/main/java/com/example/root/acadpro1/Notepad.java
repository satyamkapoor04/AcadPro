package com.example.root.acadpro1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 11/11/17.
 */

public class Notepad extends AppCompatActivity {

    EditText et1;
    Button bt1;
    Button bt2;
    NoteHandler nh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad);

        et1 = (EditText) findViewById(R.id.editText5);
        bt1 = (Button) findViewById(R.id.button8);
        bt2 = (Button) findViewById(R.id.button9);

        nh = new NoteHandler(this);

        et1.setText(nh.getData());

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = et1.getText().toString();
                nh.addData(data);
                Toast.makeText(Notepad.this, "Note Saved", Toast.LENGTH_SHORT).show();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("");
            }
        });

    }
}
