package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class student_login extends AppCompatActivity {
    Button back,login;
    EditText mobile_no,enr_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        final String class_name = getLocalClassName();
        mobile_no = findViewById(R.id.mob_no);
        enr_no = findViewById(R.id.enr_no);
        back = findViewById(R.id.back_button);
        login = findViewById(R.id.login_button);
        back.setOnClickListener(view -> {
            Intent i = new Intent(student_login.this,login_screen.class);
            startActivity(i);
            finish();
        });
        login.setOnClickListener(view -> {
            final String  Mobile_No = mobile_no.getText().toString();
            final String Enrollment_No = enr_no.getText().toString();
            if(Mobile_No.trim().length() == 10 && Enrollment_No.trim().length() == 12)
            {
//                presentInDataBase[0] = 0;
//                ToastText[0] = "0";
                String URL = "http://192.168.29.237/mysql/CheckforStudent.php";
                RequestQueue queue = Volley.newRequestQueue(student_login.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject Jobj = new JSONObject(response);

                                    if(Jobj.getString("result").equalsIgnoreCase("1"))
                                    {
                                        otp_verpage(Mobile_No,class_name);
                                    }
                                    else
                                    {
                                        Toast.makeText(student_login.this,Jobj.getString("result"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(student_login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
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
                }
                else if(Mobile_No.trim().length() != 10)
                {
                    Toast.makeText(student_login.this,"Incorrect Mobile No",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(student_login.this,"Incorrect Enrollment No",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void otp_verpage(String Mobile_No,String class_name) {
        Intent i = new Intent(student_login.this, otp_verification.class);
        i.putExtra("mobile", Mobile_No);
        i.putExtra("class_name", class_name);
        startActivity(i);
        finish();
    }
}