package com.example.splashscreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class student_login extends AppCompatActivity {
    private static String  Mobile_No ;
    private static String Enrollment_No ;
    ProgressBar pgbar;
    Button back,login;
    EditText mobile_no,enr_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        final String class_name = getLocalClassName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mobile_no = findViewById(R.id.mob_no);
        enr_no = findViewById(R.id.enr_no);
        back = findViewById(R.id.back_button);
        login = findViewById(R.id.login_button);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        pgbar = findViewById(R.id.pgbar);
        back.setOnClickListener(view -> {
            Intent i = new Intent(student_login.this,login_screen.class);
            startActivity(i);
            finish();
        });
        /*
        -------------------------------------------------------------------------------------------
        KEVAL DO NOT TOUCH THIS CODE
        -------------------------------------------------------------------------------------------
         */
        login.setOnClickListener(view -> {
            pgbar.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            Mobile_No = mobile_no.getText().toString();
            Enrollment_No = enr_no.getText().toString();
            if(Mobile_No.trim().length() == 10 && Enrollment_No.trim().length() == 12)
            {
                //URL FOR FETCHING API DATA
                String URL = "https://stocky-baud.000webhostapp.com/CheckforStudent.php";
                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                RequestQueue queue = Volley.newRequestQueue(student_login.this);
                //STRING REQUEST OBJECT INITIALIZATION
                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject Jobj = new JSONArray(response).getJSONObject(0);

                                    /*
                                    IF RESULT IS 1 ThAT MEANS DATA IS PRESENT IN DATABASE
                                     */
                                    if(Jobj.getString("result").equalsIgnoreCase("1"))
                                    {
                                        otp_verpage(Enrollment_No,Mobile_No,class_name,Jobj.getString("name"));
                                        pgbar.setVisibility(View.INVISIBLE);
                                        login.setVisibility(View.VISIBLE);
                                    }
                                    // ELSE THROW ERROR USING TOAST
                                    else
                                    {
                                        pgbar.setVisibility(View.INVISIBLE);
                                        login.setVisibility(View.VISIBLE);
                                        Toast.makeText(student_login.this,Jobj.getString("result"), Toast.LENGTH_LONG).show();
                                        if(Jobj.getString("result") == "Enrollment not present.")
                                        {
                                            enr_no.setText("");
                                            mobile_no.setText("");
                                        }
                                        else
                                        {
                                            mobile_no.setText("");
                                        }
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(student_login.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        pgbar.setVisibility(View.INVISIBLE);
                        login.setVisibility(View.VISIBLE);
                    }
                }){
                    @Override
                    //GIVING INPUT TO PHP API THROUGH MAP
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("enrollment",Enrollment_No);
                        params.put("mobile_no",Mobile_No);
                        return params;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
            else{
                if(Enrollment_No.trim().length() != 12 && Mobile_No.trim().length() != 10)
                {
                    Toast.makeText(student_login.this,"Incorrect Mobile No and Enrollment No",Toast.LENGTH_LONG).show();
                    pgbar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }
                else if(Mobile_No.trim().length() != 10)
                {
                    Toast.makeText(student_login.this,"Incorrect Mobile No",Toast.LENGTH_LONG).show();
                    pgbar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(student_login.this,"Incorrect Enrollment No",Toast.LENGTH_LONG).show();
                    pgbar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    //DIRECTING TO OTP VERIFICATION PAGE

    private void otp_verpage(String ENRNO,String Mobile_No,String class_name,String student_name) {
        Intent i = new Intent(student_login.this, otp_verification.class);
        Log.d("Name",student_name);
        i.putExtra("ENROLLMENT",ENRNO);
        i.putExtra("MOBILE", Mobile_No);
        i.putExtra("STUDENT",student_name);
        i.putExtra("class_name", class_name);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, login_screen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}