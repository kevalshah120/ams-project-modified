package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
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


public class splash_screen extends AppCompatActivity {
    Animation topanim;
    TextView appname;
    ImageView logo;
    CharSequence cs;
    int index;
    sessionForT SFT;
    sessionForS SFS;
    sessionForP SFP;
    long delay = 70;
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topanim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        Handler handler = new Handler();
        logo = findViewById(R.id.logo);
        appname=findViewById(R.id.appname);
//        YoYo.with(Techniques.StandUp).duration(2000).playOn(appname);
        logo.setAnimation(topanim);
        animatetext("Attendance Management System");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SFT = new sessionForT(getApplication());
                SFS = new sessionForS(getApplication());
                if (!SFT.getLogin().isEmpty() && !SFT.getPass().isEmpty()) {
                    //CLEAR DATA FROM OTHER LOGINS (START)
                    SFS.setMobile("");
                    SFS.setEnrollment("");
                    //CLEAR DATA FROM OTHER LOGINS (END)
                    String ID = SFT.getLogin();
                    String PASS = SFT.getPass();
                    Log.d("TID", ID);
                    Log.d("PASS", PASS);
                    String URL = "https://stocky-baud.000webhostapp.com/checkForTeacher.php";
                    //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                    RequestQueue queue = Volley.newRequestQueue(getApplication());
                    //STRING REQUEST OBJECT INITIALIZATION
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject Jobj = new JSONObject(response);
                                        if (Jobj.getString("result").equalsIgnoreCase("1")) {
                                            Intent i = new Intent(getApplication(), teacher_homescreen.class);
                                            startActivity(i);
                                            finish();
                                        }
                                        // ELSE THROW ERROR USING TOAST
                                        else {
                                            Intent i = new Intent(getApplication(), login_screen.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplication(), "Connectivity Error", Toast.LENGTH_SHORT).show();
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
                }
                else if (!SFS.getMobile().isEmpty() && !SFS.getEnrollment().isEmpty())
                {
                    String URL = "https://stocky-baud.000webhostapp.com/CheckforStudent.php";
                    //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                    RequestQueue queue = Volley.newRequestQueue(splash_screen.this);
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
                                            Intent i = new Intent(getApplication(), student_homescreen.class);
                                            startActivity(i);
                                            finish();
                                        }
                                        // ELSE THROW ERROR USING TOAST
                                        else
                                        {
                                            Intent i = new Intent(getApplication(), login_screen.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(splash_screen.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        //GIVING INPUT TO PHP API THROUGH MAP
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("enrollment",SFS.getEnrollment());
                            params.put("mobile_no",SFS.getMobile());
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
                else {
                    Intent i = new Intent(splash_screen.this, login_screen.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            appname.setText(cs.subSequence(0,index++));
            if(index <= cs.length())
            {
                h.postDelayed(run,delay);
            }
        }
    };

    public void animatetext(CharSequence chars)
    {
        cs = chars;
        index = 0;
        appname.setText("");
        h.removeCallbacks(run);
        h.postDelayed(run,delay);
    }
}