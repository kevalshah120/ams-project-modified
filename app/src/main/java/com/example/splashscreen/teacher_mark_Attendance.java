package com.example.splashscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

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
    Button Save;
    TextView timer_tv;
    ShimmerFrameLayout sf;
    ImageView timer_icon;
    RecyclerView recyclerView;
    LottieAnimationView LAV;
    public static TextView studentCount;
    String[] div_list;
    TextView OTP_code;
    public static int TotalStudents;
    public static String subject_name;
    public String location;
    boolean firstTime = true;
    sessionForT SFT;
    String temp_div;
    RadioGroup RG;
    int selectedID=-1;
    private static String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mark_attendance);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //DECLARATION AND DEFINITION (START)
        Save = findViewById(R.id.attend_save_btn);
        RG = findViewById(R.id.attendance_radio_btn);
        int expiry_time = Integer.parseInt(getIntent().getStringExtra("expiry_time")),i = 0;
        boolean smart_attend_switch = getIntent().getBooleanExtra("smart_attend_switch", false);
        String booleanString = Boolean.toString(smart_attend_switch);
        timer_tv = findViewById(R.id.timer_tv);
        timer_icon = findViewById(R.id.timer_icon);
        sf = findViewById(R.id.shimmer_layout);
        recyclerView = findViewById(R.id.teacher_mark_attend_rv);
        LAV = findViewById(R.id.no_Data_anim);
        studentCount = findViewById(R.id.student_Count);
        OTP_code = findViewById(R.id.attendance_code_val);
        SFT = new sessionForT(getApplication());
        ID = SFT.getLogin();
        //DECLARATION AND DEFINITION (END)
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //GENERATING OTP (START)
        String AlphaNumericString = "ABCDEFGHKMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghkmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(6);
        for (i = 0; i < 6; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        String OTPCODE = sb.toString();
        if(smart_attend_switch == true)
        {
            OTP_code.setText(OTPCODE);
        }
        else
        {
            OTP_code.setVisibility(View.INVISIBLE);
        }
        //GENERATING OTP (END)
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //GETTING DIVISIONS FROM PREVIOUS PAGE (START)
        temp_div = getIntent().getStringExtra("division");
        location = getIntent().getStringExtra("location");
        div_list = new String[((int) Math.floor(temp_div.length() / 3)) + 2];
        i=0;
        int  j = 0;
        while(i<temp_div.length())
        {
            if(i+2 <= temp_div.length()) {
                div_list[j] = temp_div.substring(i, i + 2);
            }
            i += 3;
            j++;
        }
        subject_name = getIntent().getStringExtra("subject");
        div_list[div_list.length-1] = getIntent().getStringExtra("subject");
        //GETTING DIVISIONS FROM PREVIOUS PAGE (END)
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //GUI RELATED CODE (START)
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
//        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                selectedID = RG.getCheckedRadioButtonId();
//                RadioButton rb = findViewById(selectedID);
//                if(rb.getText().toString().equals("All Absent"))
//                {
//                    RecyclerView recyclerView = findViewById(R.id.teacher_mark_attend_rv);
//                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
//
//                    for (int position = 0; position < adapter.getItemCount(); position++) {
//                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
//
//                        if (viewHolder != null) {
//                            ImageButton button = viewHolder.itemView.findViewById(R.id.absent_icon);
//                            button.performClick();
//                        } else {
//                            recyclerView.scrollToPosition(position);
//                            recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//                            recyclerView.layout(0, 0, recyclerView.getMeasuredWidth(), recyclerView.getMeasuredHeight());
//                            recyclerView.getAdapter().onBindViewHolder(recyclerView.findViewHolderForAdapterPosition(position), position);
//
//                            viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
//
//                            if (viewHolder != null) {
//                                ImageButton button = viewHolder.itemView.findViewById(R.id.absent_icon);
//                                button.performClick();
//                            }
//                        }
//                    }
//                }
//                else
//                {
////                    RecyclerView recyclerView = findViewById(R.id.teacher_mark_attend_rv);
////                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
////
////                    for (int position = 0; position < adapter.getItemCount(); position++) {
////                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
////
////                        if (viewHolder == null) {
////                            // Create a new view holder for the position if it's not currently bound
////                            viewHolder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(position));
////                            adapter.onBindViewHolder(viewHolder, position);
////                        }
////
////                        ImageButton button = viewHolder.itemView.findViewById(R.id.present_icon);
////                        button.performClick();
////                    }
//                    RecyclerView recyclerView = findViewById(R.id.teacher_mark_attend_rv);
//                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
//
//                    for (int position = 0; position < adapter.getItemCount(); position++) {
//                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
//
//                        if (viewHolder != null) {
//                            ImageButton button = viewHolder.itemView.findViewById(R.id.present_icon);
//                            button.performClick();
//                        } else {
//                            recyclerView.scrollToPosition(position);
//                            recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//                            recyclerView.layout(0, 0, recyclerView.getMeasuredWidth(), recyclerView.getMeasuredHeight());
//                            recyclerView.getAdapter().onBindViewHolder(recyclerView.findViewHolderForAdapterPosition(position), position);
//
//                            viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
//
//                            if (viewHolder != null) {
//                                ImageButton button = viewHolder.itemView.findViewById(R.id.present_icon);
//                                button.performClick();
//                            }
//                        }
//                    }
//
//                }
//            }
//        });
        //GUI RELATED CODE (END)
        //--------------------------------------------------------------------------------------------------------------------------------------------
        dataInitialize(); //THIS FUNCTION WILL GET ALL THE STUDENT DATA IN THE PAGE
        //CREATING HANDLE TO MAKE A THREAD THAT EXECUTES A dataRefesh() EVERY 10 SECONDS (START)
        Handler x = new Handler();
        x.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataInitialize();
                x.postDelayed(this,10000);
            }
        },10000);
        //CREATING HANDLE TO MAKE A THREAD THAT EXECUTES A dataRefesh() EVERY 10 SECONDS (END)
        createAttendenceSession(OTPCODE); //THIS WILL CREATE SESSION
        //--------------------------------------------------------------------------------------------------------------------------------------------
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = "https://stocky-baud.000webhostapp.com/saveTempAtdToAtdTb.php";
                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                RequestQueue queue = Volley.newRequestQueue(teacher_mark_Attendance.this);
                //STRING REQUEST OBJECT INITIALIZATION
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("saveTempAtdToAtdTb",response);
                                try {
                                    String result = new JSONObject(response).getString("result");
                                    if(result.equals("1"))
                                    {
                                        Intent intent = new Intent(teacher_mark_Attendance.this,teacher_homescreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(teacher_mark_Attendance.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
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
                        Map<String, String> params = new HashMap<>();
                        params.put("subject",subject_name);
                        params.put("staff_login",ID);
                        params.put("division",temp_div);
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
        });

    }
    //--------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------------
    private void dataRefresh() {
        String URL = "https://stocky-baud.000webhostapp.com/periodicUpdates.php";
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
                        Log.d("dataInitialize",response);
                        mark_attend_models = new ArrayList<>();
                        String enr;
                        String name;
                        int atdCount = 0;
                        try {
                            JSONArray array = new JSONArray(response);
                            if(array.length()==0)
                            {
                                LAV.setVisibility(View.VISIBLE);
                                Save.setVisibility(View.GONE);
                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                name = object.getString("std_name");
                                enr = object.getString("enr_no");
                                if(object.getString("attendance_status").equals("1"))
                                {
                                    atdCount++;
                                    mark_attend_models.add(new teacher_mark_attend_model(enr, name, true));
                                }
                                else {
                                    mark_attend_models.add(new teacher_mark_attend_model(enr, name, false));
                                }
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        TotalStudents = mark_attend_models.size();
                        String SC = String.valueOf(mark_attend_models.size());
                        studentCount.setText(String.valueOf(atdCount)+"/"+SC);
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
                Map<String, String> params = new HashMap<>();
                params.put("subject",subject_name);
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
    //--------------------------------------------------------------------------------------------------------------------------------------------
    private void createAttendenceSession(String OTP)
    {
        String URL = "https://stocky-baud.000webhostapp.com/createAttendenceSession.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(teacher_mark_Attendance.this);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("createAttendenceSession",response);
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
                Map<String, String> params = new HashMap<>();
                params.put("OTP",OTP);
                params.put("staff_login_id", ID);
//                Log.d("abc",teacher_login.ID);
                int i = 0;
                while(i<div_list.length-1)
                {
                    params.put("div"+String.valueOf(i+1),div_list[i]);
                    i++;
                }
                params.put("subject",div_list[i]);
                params.put("sizeOfDiv",String.valueOf(div_list.length-1));
                params.put("location",location);
                params.put("iDIv",temp_div);
                Log.d("Location",location);
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
    //--------------------------------------------------------------------------------------------------------------------------------------------
    private void dataInitialize() {
        if(firstTime == true) {
            firstTime = false;
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
                                if(!array.getJSONObject(0).isNull("result"))
                                {
                                    Log.d("getStudentDetails",response);
                                    if(array.getJSONObject(0).getString("result").equals("attendanceTaken")) {
                                        Toast.makeText(teacher_mark_Attendance.this, "Sub & Div Attendance already taken", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), teacher_homescreen.class));
                                        finish();
                                    }
                                    else {
                                        if (array.length() == 0) {
                                            LAV.setVisibility(View.VISIBLE);
                                            Save.setVisibility(View.GONE);
                                        }
                                        for (int i = 0; i < array.length() - 1; i++) {
                                            JSONObject object = array.getJSONObject(i);
                                            name = object.getString("std_name");
                                            enr = object.getString("enr_no");
                                            mark_attend_models.add(new teacher_mark_attend_model(enr, name, false));
                                        }
                                    }
                                }
                                else {
                                    Log.d("getStudentDetails",response);
                                    if (array.length() == 0) {
                                        LAV.setVisibility(View.VISIBLE);
                                        Save.setVisibility(View.GONE);
                                    }
                                    for (int i = 0; i < array.length() - 1; i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        name = object.getString("std_name");
                                        enr = object.getString("enr_no");
                                        mark_attend_models.add(new teacher_mark_attend_model(enr, name, false));
                                    }
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            TotalStudents = mark_attend_models.size();
                            String SC = String.valueOf(mark_attend_models.size());
                            studentCount.setText("0/" + SC);
                            recyclerView.setLayoutManager(new LinearLayoutManager(teacher_mark_Attendance.this));
                            recyclerView.setHasFixedSize(true);
                            teacher_mark_attend_adapter mark_attend_adapter = new teacher_mark_attend_adapter(teacher_mark_Attendance.this, mark_attend_models);
                            sf.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
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
                    Map<String, String> params = new HashMap<>();
                    int i = 0;
                    while (i < div_list.length - 1) {
                        params.put("div" + String.valueOf(i + 1), div_list[i]);
                        i++;
                    }
                    params.put("subject", div_list[i]);
                    params.put("sizeOfDiv", String.valueOf(div_list.length - 1));
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
        else
        {
            dataRefresh();
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(teacher_mark_Attendance.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String URL = "https://stocky-baud.000webhostapp.com/deleteSessionAndTempAtd.php";
                        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                        RequestQueue queue = Volley.newRequestQueue(teacher_mark_Attendance.this);
                        //STRING REQUEST OBJECT INITIALIZATION
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("BackButton",response);
                                        try {
                                            String result = new JSONObject(response).getString("result");
                                            if(result.equals("1"))
                                            {
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(teacher_mark_Attendance.this, result, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
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
                                Map<String, String> params = new HashMap<>();
                                params.put("subject",subject_name);
                                params.put("staff_login",ID);
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
//                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
