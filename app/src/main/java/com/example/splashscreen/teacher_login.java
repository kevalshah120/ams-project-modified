package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class teacher_login extends AppCompatActivity {
    static EditText login_id;
    Button back, login;
    private boolean passwordshowing = true;
    ProgressBar pgbar;
    private static String ID;
    private static String PASS;
    ImageView password_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final String class_name = getLocalClassName();
        final String mobile_no;
        login_id = findViewById(R.id.login_id);
        final EditText password_field = findViewById(R.id.password_field);
        back = findViewById(R.id.back_button);
        login = findViewById(R.id.login_button);
        pgbar = findViewById(R.id.pgbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        back.setOnClickListener(view -> {
            Intent i = new Intent(teacher_login.this, login_screen.class);
            startActivity(i);
            finish();
        });
        login.setOnClickListener(view -> {
            pgbar.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            ID = login_id.getText().toString();
//            TEMP(class_name);
            PASS = password_field.getText().toString();
            if(ID.contains("'") || ID.contains(";") || ID.contains("$") || ID.contains("=") ||PASS.contains("'") || PASS.contains(";") || PASS.contains("$") || PASS.contains("="))
            {
                Toast.makeText(getApplicationContext(), "Special characters like  $ , ' , = , ; are not allowed",Toast.LENGTH_SHORT).show();
                pgbar.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
                return;
            }
            if (!(ID.trim().isEmpty()) && !(PASS.trim().isEmpty())) {
                //URL FOR FETCHING API DATA
                String URL = "https://stocky-baud.000webhostapp.com/checkForTeacher.php";
                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                RequestQueue queue = Volley.newRequestQueue(teacher_login.this);
                //STRING REQUEST OBJECT INITIALIZATION
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject Jobj = new JSONObject(response);
                                    /*
                                    IF RESULT IS 1 ThAT MEANS DATA IS PRESENT IN DATABASE
                                     */
                                    if (Jobj.getString("result").equalsIgnoreCase("1")) {
                                        OTP_ver(ID, PASS, Jobj.getString("contact_no"), class_name);
                                        pgbar.setVisibility(View.INVISIBLE);
                                        login.setVisibility(View.VISIBLE);
                                    }
                                    // ELSE THROW ERROR USING TOAST
                                    else {
                                        pgbar.setVisibility(View.INVISIBLE);
                                        login.setVisibility(View.VISIBLE);
                                        Toast.makeText(teacher_login.this, Jobj.getString("result"), Toast.LENGTH_LONG).show();
                                        if (Jobj.getString("result") == "Couldn't find ID") {
                                            login_id.setText("");
                                            password_field.setText("");
                                        } else {
                                            password_field.setText("");
                                        }
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(teacher_login.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        pgbar.setVisibility(View.INVISIBLE);
                        login.setVisibility(View.VISIBLE);
                    }
                }) {
                    @Override
                    //GIVING INPUT TO PHP API THROUGH MAP
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("ID", ID);
                        params.put("PASS", PASS);
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
            } else {
                if ((ID.trim().isEmpty()) && (PASS.trim().isEmpty())) {
                    Toast.makeText(teacher_login.this, "Please enter ID and Password", Toast.LENGTH_LONG).show();
                    pgbar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                } else if (ID.trim().isEmpty()) {
                    Toast.makeText(teacher_login.this, "Please enter your ID", Toast.LENGTH_LONG).show();
                    pgbar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(teacher_login.this, "Please enter the Password", Toast.LENGTH_LONG).show();
                    pgbar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }
            }
        });
        password_icon = findViewById(R.id.password_hide);
        password_icon.setOnClickListener(view -> {
            if (passwordshowing) {
                passwordshowing = false;
                password_field.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                password_icon.setImageResource(R.drawable.password_show);
            } else {
                passwordshowing = true;
                password_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password_icon.setImageResource(R.drawable.password_hide);
            }
            password_field.setSelection(password_field.length());
        });
    }
    private void OTP_ver(String ID, String PASSS, String mobile_no, String class_name) {
        Intent i = new Intent(teacher_login.this, otp_verification.class);
        i.putExtra("MOBILE", mobile_no);
        i.putExtra("class_name", class_name);
        i.putExtra("ID", ID);
        i.putExtra("PASS", PASS);
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