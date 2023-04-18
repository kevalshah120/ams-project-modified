package com.example.splashscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class student_add extends AppCompatActivity {
    EditText Name,Enrollment,Mobile,ParentMobile,Semester,Division,Batch;
    Button Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);
        Name = findViewById(R.id.name_ti);
        Enrollment = findViewById(R.id.enr_ti);
        Mobile = findViewById(R.id.stu_mob_ti);
        ParentMobile = findViewById(R.id.parent_mob_ti);
        Semester = findViewById(R.id.sem_ti);
        Division = findViewById(R.id.div_ti);
        Batch = findViewById(R.id.batch_ti);
        Save = findViewById(R.id.submit_button);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nm,Enr,Mb,ParentMb,Sem,Div,BatchYear;
                Boolean SaveOrNot = true;
                Nm=Name.getText().toString();
                Enr=Enrollment.getText().toString();
                Mb = Mobile.getText().toString();
                ParentMb = ParentMobile.getText().toString();
                Sem = Semester.getText().toString();
                Div = Division.getText().toString();
                BatchYear = Batch.getText().toString();
                int spaces = Nm.length() - Nm.replaceAll(" ", "").length();
                if(spaces < 1)
                {
                    Log.d("1","1");
                    Name.setText("");
                    SaveOrNot = false;
                }
                if(Enr.length() != 12)
                {
                    Log.d("1","2");
                    Enrollment.setText("");
                    SaveOrNot = false;
                }
                if(Mb.length() != 10)
                {
                    Log.d("1","3");
                    Mobile.setText("");
                    SaveOrNot = false;
                }
                if(ParentMb.length() != 10)
                {
                    Log.d("1","4");
                    ParentMobile.setText("");
                    SaveOrNot = false;
                }
                if(Sem.length() > 2)
                {
                    Log.d("1","5");
                    Semester.setText("");
                    SaveOrNot = false;
                }
                if(Div.length() != 2)
                {
                    Log.d("1",String.valueOf(Div.length()));
                    Division.setText("");
                    SaveOrNot = false;
                }
                if(BatchYear.length() != 4)
                {
                    Log.d("1","7");
                    Batch.setText("");
                    SaveOrNot = false;
                }
                if(SaveOrNot)
                {
                    String UrL = "https://stocky-baud.000webhostapp.com/InsertStudent.php";
                    //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                    RequestQueue Queue = Volley.newRequestQueue(student_add.this);
                    //STRING REQUEST OBJECT INITIALIZATION
                    StringRequest StringRequest = new StringRequest(Request.Method.POST, UrL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        String RES = obj.getString("RESULT");
                                        if(RES.equals("1"))
                                        {
                                            Toast.makeText(student_add.this, "Student Added", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplication(),teacher_homescreen.class));
                                        }
                                        else
                                        {
                                            Toast.makeText(student_add.this, "Student insert Failed", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplication(),teacher_homescreen.class));
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(student_add.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        //GIVING INPUT TO PHP API THROUGH MAP
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Name",Name.getText().toString());
                            params.put("Enrollment",Enrollment.getText().toString());
                            params.put("Mobile",Mobile.getText().toString());
                            params.put("ParentMobile",ParentMobile.getText().toString());
                            params.put("Semester",Semester.getText().toString());
                            params.put("Division",Division.getText().toString());
                            params.put("Batch",Batch.getText().toString());
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
    }
}