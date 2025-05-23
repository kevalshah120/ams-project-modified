package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class parent_login extends AppCompatActivity {
    static String  PMobile_No ;
    static String Enrollment_No ;
    ProgressBar pgbar;
    Button back,login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);
        final String class_name = getLocalClassName();
        final EditText mobile_no = findViewById(R.id.mob_no);
        final EditText enr_no = findViewById(R.id.enr_no);
        pgbar = findViewById(R.id.pgbar);
        back = findViewById(R.id.back_button);
        login_btn = findViewById(R.id.par_login_button);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        back.setOnClickListener(view -> {
            Intent i = new Intent(parent_login.this,login_screen.class);
            startActivity(i);
            finish();
        });
        login_btn.setOnClickListener(view -> {
            pgbar.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.INVISIBLE);
            PMobile_No = mobile_no.getText().toString();
            Enrollment_No = enr_no.getText().toString();
            if(PMobile_No.trim().length() == 10 && Enrollment_No.trim().length() == 12)
            {
                //URL FOR FETCHING API DATA
                String URL = "https://stocky-baud.000webhostapp.com/CheckforParent.php";
                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                RequestQueue queue = Volley.newRequestQueue(parent_login.this);
                //STRING REQUEST OBJECT INITIALIZATION
                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject Jobj = new JSONObject(response);
                                    /*
                                    IF RESULT IS 1 ThAT MEANS DATA IS PRESENT IN DATABASE
                                     */
                                    if(Jobj.getString("result").equalsIgnoreCase("1"))
                                    {
                                        otp_verpage(PMobile_No,class_name);
                                        pgbar.setVisibility(View.INVISIBLE);
                                        login_btn.setVisibility(View.VISIBLE);
                                    }
                                    // ELSE THROW ERROR USING TOAST
                                    else
                                    {
                                        Toast.makeText(parent_login.this,Jobj.getString("result"), Toast.LENGTH_LONG).show();
                                        if(Jobj.getString("result") == "Enrollment not present.")
                                        {
                                            enr_no.setText("");
                                            mobile_no.setText("");
                                            pgbar.setVisibility(View.INVISIBLE);
                                            login_btn.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            mobile_no.setText("");
                                            pgbar.setVisibility(View.INVISIBLE);
                                            login_btn.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pgbar.setVisibility(View.INVISIBLE);
                        login_btn.setVisibility(View.VISIBLE);
                        Toast.makeText(parent_login.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    //GIVING INPUT TO PHP API THROUGH MAP
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("enrollment",Enrollment_No);
                        params.put("mobile_no",PMobile_No);
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
                if(Enrollment_No.trim().length() != 12 && PMobile_No.trim().length() != 10)
                {
                    pgbar.setVisibility(View.INVISIBLE);
                    login_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(parent_login.this,"Please enter proper Mobile No and Enrollment No",Toast.LENGTH_LONG).show();
                }
                else if(PMobile_No.trim().length() != 10)
                {
                    pgbar.setVisibility(View.INVISIBLE);
                    login_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(parent_login.this,"Please enter proper Mobile No",Toast.LENGTH_LONG).show();
                }
                else {
                    pgbar.setVisibility(View.INVISIBLE);
                    login_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(parent_login.this,"Please enter proper Enrollment No",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void otp_verpage(String PMobile_No,String class_name) {
        Intent i = new Intent(parent_login.this, otp_verification.class);
        i.putExtra("ENROLLMENT",Enrollment_No);
        i.putExtra("MOBILE", PMobile_No);
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