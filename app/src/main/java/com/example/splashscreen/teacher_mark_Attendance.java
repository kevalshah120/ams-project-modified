package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class teacher_mark_Attendance extends AppCompatActivity {
    List<teacher_mark_attend_model> mark_attend_models = new ArrayList<>();
    TextView timer_tv;
    ImageView timer_icon;
    RecyclerView recyclerView;
    TextView studentCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mark_attendance);
        int expiry_time = Integer.parseInt(getIntent().getStringExtra("expiry_time"));
        boolean smart_attend_switch = getIntent().getBooleanExtra("smart_attend_switch", false);
        String booleanString = Boolean.toString(smart_attend_switch);
        timer_tv = findViewById(R.id.timer_tv);
        timer_icon = findViewById(R.id.timer_icon);
        recyclerView = findViewById(R.id.teacher_mark_attend_rv);
        studentCount = findViewById(R.id.student_Count);
        if(!smart_attend_switch){
            timer_icon.setVisibility(View.GONE);
            timer_tv.setVisibility(View.GONE);
        }
        else{
            timer_icon.setVisibility(View.VISIBLE);
            timer_tv.setVisibility(View.VISIBLE);
            new CountDownTimer(expiry_time * 60 * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(minutes);
                    timer_tv.setText(String.format("%d:%02d", minutes, seconds));
                }
                public void onFinish() {

                }
            }.start();
        }
        dataInitialize();
    }
    private void dataInitialize() {
//        mark_attend_models.add(new teacher_mark_attend_model("206090307004", "Keval Shah"));
//        mark_attend_models.add(new teacher_mark_attend_model("206090307034", "Henarth Agravat"));
//        mark_attend_models.add(new teacher_mark_attend_model("206090307014", "Harsh Shah"));
//        mark_attend_models.add(new teacher_mark_attend_model("206090307064", "Yash Matariya"));
        String URL = "https://stocky-baud.000webhostapp.com/getStudentDetailsForTeacher.php";
        if (mark_attend_models != null) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
            mark_attend_models.clear();
        }
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(teacher_mark_Attendance.this);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mark_attend_models = new ArrayList<>();
                        String enr;
                        String name;
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                name = object.getString("std_name");
                                enr = object.getString("enr_no");
                                mark_attend_models.add(new teacher_mark_attend_model(enr,name));
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        String SC = String.valueOf(mark_attend_models.size());
                        studentCount.setText("0/"+SC);
                        recyclerView.setLayoutManager(new LinearLayoutManager(teacher_mark_Attendance.this));
                        recyclerView.setHasFixedSize(true);
                        teacher_mark_attend_adapter mark_attend_adapter = new teacher_mark_attend_adapter(teacher_mark_Attendance.this, mark_attend_models);
                        recyclerView.setAdapter(mark_attend_adapter);
                        mark_attend_adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(teacher_mark_Attendance.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("T_ID", teacher_login.ID);
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
}