package com.example.root.acadpro1;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private Button studentUpdate;
    private Button attendance;
    private Button report;
    private Button takenote;
    private SessionManager session;
    private SQLiteHandler db;
    private ProgressDialog progressDialog;
    private DatabaseHandler dh;
    private DataHandler dhl;
    Bundle b;
    private String sendname;
    private String date;
    private DatePickerDialog datePickerDialog;
    private Calendar dateSelected;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        View headerLayout = navigationView.inflateHeaderView(R.layout.navigation_header);
        TextView customView = (TextView) headerLayout.findViewById(R.id.header);
        customView.setTextColor(Color.WHITE);


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }




        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        dateSelected = Calendar.getInstance();

        dh = new DatabaseHandler(this);
        dhl = new DataHandler(this);

        b = getIntent().getExtras();
        Boolean databaseAlreadyCreated = b.getBoolean("databaseAlreadyCreated");
        sendname = b.getString("sendname");

        customView.setText(sendname);

        if (!databaseAlreadyCreated) {
            progressDialog.setMessage("Please wait while attendance is downloaded from the server");
            showDialog();
            createDatabase();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);



        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        attendance = (Button) findViewById(R.id.button);
        takenote = (Button) findViewById(R.id.button2);
        report = (Button) findViewById(R.id.button4);
        studentUpdate = (Button) findViewById(R.id.button3);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        studentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentUpdate.class);
                startActivity(intent);
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportAttendance.class);
                startActivity(intent);
            }
        });

        takenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notepad.class);
                startActivity(intent);
            }
        });

    }

    private void setDateTimeField() {
        Calendar newCalendar = dateSelected;
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                date = formatter.format(dateSelected.getTime());
                //Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
                AttendanceData attd = dhl.getAttendanceData(date);
                boolean isPresent = true;
                if (attd == null)
                    isPresent = false;
                Intent intent = new Intent(MainActivity.this, GetAttendance.class);
                intent.putExtra("date",date);
                intent.putExtra("isPresent",isPresent);
                startActivity(intent);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void logoutUser() {
        session.setLogin(false,"","");

        db.deleteUsers();
        dh.deleteDatabase(getApplicationContext());
        dhl.deleteDatabase(getApplicationContext());

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void createDatabase() {
        String tag_string_make = "make_req";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                LoginConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray peoples = jObj.getJSONArray("result");

                    // Now store the user in SQLite


                    for (int i=0; i<peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        dh.addAttendace(new Attendance(c.getInt("id"), c.getString("section"), c.getString("name"), c.getString("roll"), c.getInt("attain")));
                        if (c.getInt("id") == 21) {
                            Toast.makeText(MainActivity.this, "Download complete" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    hideDialog();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Check Internet Connection..." , Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", sendname);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_make);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            logoutUser();
        }


        return false;
    }
}
