package com.example.splashscreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class student_details extends AppCompatActivity {
    Button edit_btn, save_btn , remove_btn;
    ProgressBar pgbar;

    TextInputEditText name_ti, enr_ti, stu_mob_ti, parent_mob_ti, sem_ti, div_ti, batch_ti;
    String enrollment, stu_name, contact_no, semester, division, batch, parents_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        edit_btn = findViewById(R.id.edit_button);
        remove_btn = findViewById(R.id.delete_button);
        save_btn = findViewById(R.id.save_button);
        save_btn.setVisibility(View.GONE);
        pgbar = findViewById(R.id.pgbar);
        name_ti = findViewById(R.id.name_ti);
        enr_ti = findViewById(R.id.enr_ti);
        stu_mob_ti = findViewById(R.id.stu_mob_ti);
        parent_mob_ti = findViewById(R.id.parent_mob_ti);
        sem_ti = findViewById(R.id.sem_ti);
        div_ti = findViewById(R.id.div_ti);
        batch_ti = findViewById(R.id.batch_ti);
        Intent i = getIntent();
        enrollment = i.getStringExtra("enrollment");
        enr_ti.setText(enrollment);
        //-------------------------------------------------------------------------------------------------------------
        // FETCHING DATA FOR SUBJECT NAMES AND DIVISIONS
        String URL = "https://stocky-baud.000webhostapp.com/allStudentDetails.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(student_details.this);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONArray(response).getJSONObject(0);
                            name_ti.setText(obj.getString("std_name"));
                            stu_mob_ti.setText(obj.getString("contact_no"));
                            sem_ti.setText(obj.getString("semester"));
                            div_ti.setText(obj.getString("division"));
                            batch_ti.setText(obj.getString("batch"));
                            parent_mob_ti.setText(obj.getString("parent_number"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(student_details.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("enrollment", enrollment);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
        //-------------------------------------------------------------------------------------------------------------
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit_btn.getText().toString().equals("Edit")) {
                    save_btn.setVisibility(View.VISIBLE);
                    name_ti.setEnabled(true);
                    stu_mob_ti.setEnabled(true);
                    sem_ti.setEnabled(true);
                    div_ti.setEnabled(true);
                    batch_ti.setEnabled(true);
                    parent_mob_ti.setEnabled(true);
                    edit_btn.setText("Cancel");
                } else if (edit_btn.getText().toString().equals("Cancel")) {
                    save_btn.setVisibility(View.INVISIBLE);
                    name_ti.setEnabled(false);
                    stu_mob_ti.setEnabled(false);
                    sem_ti.setEnabled(false);
                    div_ti.setEnabled(false);
                    batch_ti.setEnabled(false);
                    parent_mob_ti.setEnabled(false);
                    edit_btn.setText("Edit");
                }
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgbar.setVisibility(View.VISIBLE);
                save_btn.setVisibility(View.INVISIBLE);
                stu_name = name_ti.getText().toString();
                contact_no = stu_mob_ti.getText().toString();
                semester = sem_ti.getText().toString();
                division = div_ti.getText().toString();
                batch = batch_ti.getText().toString();
                parents_number = parent_mob_ti.getText().toString();
                if(stu_name.contains("'") || stu_name.contains(";") || stu_name.contains("$") || stu_name.contains("=") ||division.contains("'") || division.contains(";") || division.contains("$") ||division.contains("="))
                {
                    Toast.makeText(getApplicationContext(), "Special characters like  $ , ' , = , ; are not allowed",Toast.LENGTH_SHORT).show();
                    pgbar.setVisibility(View.INVISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    return;
                }
                boolean check = true;
                int space = stu_name.length() - stu_name.replaceAll(" ", "").length();
                if (space < 1) {
                    name_ti.setError("Must contain 1 space");
                    pgbar.setVisibility(View.INVISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    check = false;
                }
                if(contact_no.length() != 10)
                {
                    stu_mob_ti.setError("minimum length 10");
                    pgbar.setVisibility(View.INVISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    check = false;
                }
                if(parents_number.length() != 10)
                {
                    parent_mob_ti.setError("minimum length 10");
                    pgbar.setVisibility(View.INVISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    check = false;
                }
                if(semester.length() != 1)
                {
                    sem_ti.setError("minimum length 1");
                    pgbar.setVisibility(View.INVISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    check = false;
                }
                if(division.length() != 2)
                {
                    div_ti.setError("minimum length 2");
                    pgbar.setVisibility(View.INVISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    check = false;
                }
                if(batch.length() != 4)
                {
                    batch_ti.setError("minimum length 4");
                    pgbar.setVisibility(View.INVISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    check = false;
                }
                if (check) {
                    String UrL = "https://stocky-baud.000webhostapp.com/updateStudentDetails.php";
                    //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                    RequestQueue Queue = Volley.newRequestQueue(student_details.this);
                    //STRING REQUEST OBJECT INITIALIZATION
                    StringRequest StringRequest = new StringRequest(Request.Method.POST, UrL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response", response);
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        String res = obj.getString("result");
                                        if (res.equals("1")) {
                                            pgbar.setVisibility(View.INVISIBLE);
                                            save_btn.setVisibility(View.VISIBLE);
                                            Toast.makeText(student_details.this, "Details Updated", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(student_details.this, teacher_homescreen.class);
                                            startActivity(i);
                                        } else {
                                            pgbar.setVisibility(View.INVISIBLE);
                                            save_btn.setVisibility(View.VISIBLE);
                                            Toast.makeText(student_details.this, res, Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(student_details.this, student_details.class);
                                            i.putExtra("enrollment", enrollment);
                                            startActivity(i);
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pgbar.setVisibility(View.INVISIBLE);
                            save_btn.setVisibility(View.VISIBLE);
                            Toast.makeText(student_details.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        //GIVING INPUT TO PHP API THROUGH MAP
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
//                        Log.d("paramterz",enrollment);
                            params.put("enrollment", enrollment);
                            params.put("Sname", stu_name);
//                        Log.d("paramterz",name_ti.getText().toString());
                            params.put("Snumber", contact_no);
//                        Log.d("paramterz",stu_mob_ti.getText().toString());
                            params.put("Pnumber", parents_number);
//                        Log.d("paramterz",parent_mob_ti.getText().toString());
                            params.put("division", division);
//                        Log.d("paramterz",div_ti.getText().toString());
                            params.put("batch", batch);
//                        Log.d("paramterz",batch_ti.getText().toString());
                            params.put("semester", semester);
//                        Log.d("paramterz",sem_ti.getText().toString());
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/x-www-form-urlencoded");
                            return params;
                        }
                    };
                    Queue.add(StringRequest);
                }
            }
        });
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LOGOUT (START)
                AlertDialog.Builder builder = new AlertDialog.Builder(student_details.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to  delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String UrL = "https://stocky-baud.000webhostapp.com/removeStudent.php";
                                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                                RequestQueue Queue = Volley.newRequestQueue(student_details.this);
                                //STRING REQUEST OBJECT INITIALIZATION
                                StringRequest StringRequest = new StringRequest(Request.Method.POST, UrL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("response", response);
                                                try {
                                                    JSONObject obj = new JSONObject(response);
                                                    String res = obj.getString("result");
                                                    if (res.equals("1")) {
                                                        Toast.makeText(student_details.this, "Student Removed", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(student_details.this, teacher_homescreen.class);
                                                        startActivity(i);
                                                    } else {
                                                        Toast.makeText(student_details.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(student_details.this, student_details.class);
                                                        i.putExtra("enrollment", enrollment);
                                                        startActivity(i);
                                                    }
                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(student_details.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    //GIVING INPUT TO PHP API THROUGH MAP
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("enrollment", enrollment);
                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Content-Type", "application/x-www-form-urlencoded");
                                        return params;
                                    }
                                };
                                Queue.add(StringRequest);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                //LOGOUT (END)
            }
        });
    }
}