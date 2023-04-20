package com.example.splashscreen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class teacher_class_attendance extends AppCompatActivity {
    TextInputEditText from_Date,to_Date;
    TextInputEditText sub_et, div_et;
    Button generate_button;
    String selected_subject, selected_division;
    String[] subject_list ;
    boolean[] checked_sub_list;
    ArrayList<Integer> sub_selected_pos = new ArrayList<>();
    String[] div_list ;
    boolean[] checked_divs;
    ArrayList<Integer> div_selected_pos = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    sessionForT SFT;
    String Subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_attendance);
        sub_et = findViewById(R.id.sub_et);
        div_et = findViewById(R.id.div_et);
        from_Date = findViewById(R.id.from_date);
        to_Date = findViewById(R.id.to_date);
        generate_button = findViewById(R.id.generate_button);
        div_et.setEnabled(false);
        sub_et.setEnabled(false);
        div_et.setInputType(InputType.TYPE_NULL);
        sub_et.setInputType(InputType.TYPE_NULL);
        SFT = new sessionForT(getApplicationContext());
        //-------------------------------------------------------------------------------------------------------------
        // FETCHING DATA FOR SUBJECT NAMES AND DIVISIONS
        String URL = "https://stocky-baud.000webhostapp.com/getSubForTCA.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(teacher_class_attendance.this);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            sub_et.setEnabled(true);
                            sub_et.setText("");
                            subject_list = null;
                            JSONArray array = new JSONArray(response);
                            int AL = array.length(),j=0;
                            subject_list = new String[AL];
                            for (j = 0; j < AL; j++) {
                                JSONObject object = array.getJSONObject(j);
                                Log.d("HAHAHAHAHA",object.getString("sub_name"));
                                if(object.getString("lab").equals("1"))
                                {
                                    subject_list[j] = object.getString("sub_name");
                                    AL++;
                                    j++;
                                    subject_list = Arrays.copyOf(subject_list,subject_list.length+1);
                                    subject_list[j] = object.getString("sub_name") + " LAB";
                                }
                                else {
                                    subject_list[j] = object.getString("sub_name");
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sub_et.setEnabled(false);
                Toast.makeText(teacher_class_attendance.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Tlogin", SFT.getLogin());
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
        sub_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bol) {
                if(bol)
                {
                    div_et.setText("");
                    sub_et.setText("");
                    sub_selected_pos.clear();
                    div_selected_pos.clear();
                    //-------------------------------------------------------------------------------------------------------------
                    // FETCHING DATA FOR SUBJECT NAMES AND DIVISIONS
                    String URL = "https://stocky-baud.000webhostapp.com/getSubForTCA.php";
                    //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                    RequestQueue queue = Volley.newRequestQueue(teacher_class_attendance.this);
                    //STRING REQUEST OBJECT INITIALIZATION
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        div_et.setEnabled(true);
                                        subject_list = null;
                                        JSONArray array = new JSONArray(response);
                                        int AL = array.length(),j=0;
                                        subject_list = new String[AL];
                                        for (j = 0; j < AL; j++) {
                                            JSONObject object = array.getJSONObject(j);
                                            if(object.getString("lab").equals("1"))
                                            {
                                                subject_list[j] = object.getString("sub_name");
                                                AL++;
                                                j++;
                                                subject_list = Arrays.copyOf(subject_list,subject_list.length+1);
                                                subject_list[j] = object.getString("sub_name") + " LAB";
                                            }
                                            else {
                                                subject_list[j] = object.getString("sub_name");
                                            }
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_class_attendance.this);
                                    builder.setTitle("Subjects");
                                    builder.setSingleChoiceItems(subject_list, -1, (dialogInterface, i) -> selected_subject = subject_list[i]);
                                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                        sub_et.setText(selected_subject);
                                        dialogInterface.dismiss();
                                    });
                                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                                    builder.show();
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_class_attendance.this);
//                                    builder.setTitle("Subject");
//                                    builder.setMultiChoiceItems(subject_list, checked_sub_list, (dialogInterface, i, b) -> {
//                                        if(b){
//                                            if(!sub_selected_pos.contains(i)){
//                                                sub_selected_pos.add(i);
//                                                sub_et.clearFocus();
//                                                sub_et.setInputType(InputType.TYPE_NULL);
//                                            }
//                                        }
//                                        else{
//                                            sub_selected_pos.remove((Integer) i);
//                                        }
//                                    });
//                                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
//                                        StringBuilder sub_selected_val = new StringBuilder();
//                                        for(int count_val = 0; count_val < sub_selected_pos.size() ; count_val++)
//                                        {
//                                            sub_selected_val.append(subject_list[sub_selected_pos.get(count_val)]);
//                                        }
//                                        sub_et.setText(sub_selected_val.toString());
//                                        Subject = sub_selected_val.toString();
//                                        Log.d("HAHAHAHAHA",sub_selected_val.toString());
//                                    });
//                                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
//                                    builder.show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sub_et.setEnabled(false);
                            Toast.makeText(teacher_class_attendance.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        //GIVING INPUT TO PHP API THROUGH MAP
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Tlogin", SFT.getLogin());
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
                }
            }
        });
        div_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bol) {
                if(bol)
                {
                    div_et.setText("");
                    div_selected_pos.clear();
                    //-------------------------------------------------------------------------------------------------------------
                    // FETCHING DATA FOR SUBJECT NAMES AND DIVISIONS
                    String URL = "https://stocky-baud.000webhostapp.com/getDivForTeacher.php";
                    //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                    RequestQueue queue = Volley.newRequestQueue(teacher_class_attendance.this);
                    //STRING REQUEST OBJECT INITIALIZATION
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("Response",response);
                                        div_et.setEnabled(true);
                                        JSONArray array = new JSONArray(response);
                                        div_list = new String[array.length()];
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject object = array.getJSONObject(i);
                                            div_list[i] = object.getString("division");
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Log.d("Response",response);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_class_attendance.this);
                                    builder.setTitle("Division");
                                    builder.setMultiChoiceItems(div_list, checked_divs, (dialogInterface, i, b) -> {
                                        if(b){
                                            if(!div_selected_pos.contains(i)){
                                                div_selected_pos.add(i);
                                                div_et.clearFocus();
                                                div_et.setInputType(InputType.TYPE_NULL);
                                            }
                                        }
                                        else{
                                            div_selected_pos.remove((Integer) i);
                                        }
                                    });
                                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                        StringBuilder div_selected_val = new StringBuilder();
                                        for(int count_val = 0; count_val < div_selected_pos.size() ; count_val++)
                                        {
                                            div_selected_val.append(div_list[div_selected_pos.get(count_val)]);
                                            if (count_val + 1 != div_selected_pos.size()) {
                                                div_selected_val.append(",");
                                            }
                                        }
                                        div_et.setText(div_selected_val.toString());
                                    });
                                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                                    builder.show();
                                    div_selected_pos.clear();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Response","fuck");
                            Toast.makeText(teacher_class_attendance.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        //GIVING INPUT TO PHP API THROUGH MAP
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("subject",Subject);
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
                }
            }
        });
        from_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    datepicker_fun(from_Date);
                    from_Date.clearFocus();
                    from_Date.setInputType(InputType.TYPE_NULL);
                }
            }
        });
        to_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    datepicker_fun(to_Date);
                    to_Date.clearFocus();
                    to_Date.setInputType(InputType.TYPE_NULL);
                }
            }
        });
        generate_button.setOnClickListener(v -> {
            selected_subject = sub_et.getText().toString();
            selected_division = div_et.getText().toString();
            final String class_name = getLocalClassName();
            Intent i = new Intent(teacher_class_attendance.this, attendance_display_wv.class);
            i.putExtra("subject", selected_subject);
            i.putExtra("division", selected_division);
            i.putExtra("from_date", from_Date.getText().toString());
            i.putExtra("to_date", to_Date.getText().toString());
            i.putExtra("class_name",class_name);
            startActivity(i);
        });
    }
    private void datepicker_fun(EditText date_text)
    {
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int month = i1+1;
                if(i2<10)
                {
                    if(month<10)
                    {
                        date_text.setText("0"+month+"/"+"0"+i2+"/"+i);
                    }
                    else
                    {
                        date_text.setText(month+"/"+"0"+i2+"/"+i);
                    }
                }
                else
                {
                    if(month<10)
                    {
                        date_text.setText("0"+month+"/"+i2+"/"+i);
                    }
                    else
                    {
                        date_text.setText(month+"/"+i2+"/"+i);
                    }
                }
            }
        };
        DatePickerDialog d = new DatePickerDialog(teacher_class_attendance.this, dpd,year,month,day);
        d.show();
    }
}
