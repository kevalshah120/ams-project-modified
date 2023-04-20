package com.example.splashscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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

public class teacher_student_attendance_view extends AppCompatActivity {
    TextInputEditText from_Date,to_Date,subject;
    AutoCompleteTextView stu_auto_comp,sem_auto_comp;
    ArrayAdapter<String> stu_adap_items;
    ArrayAdapter<String> sem_adap_items;
    String[] subject_list ;
    boolean[] checked_sub_list;
    Button generate_button;
    String[] student_enr ;
    String[] sem_val ;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    ArrayList<Integer> sub_selected_pos = new ArrayList<>();
    int month = calendar.get(Calendar.MONTH);
    String SelectedSem,SelectedEnr,SelectedSubject;
    sessionForT SFT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_attendance_view);
        from_Date = findViewById(R.id.from_date);
        to_Date = findViewById(R.id.to_date);
        subject = findViewById(R.id.sub_et);
        generate_button = findViewById(R.id.generate_button);
        stu_auto_comp = findViewById(R.id.stu_auto_comp);
        sem_auto_comp = findViewById(R.id.sem_auto_comp);
        SFT = new sessionForT(getApplicationContext());
        stu_auto_comp.setEnabled(false);
        sem_auto_comp.setEnabled(false);
        subject.setEnabled(false);
        subject.setInputType(InputType.TYPE_NULL);
        //-------------------------------------------------------------------------------------------------------------
        // FETCHING DATA FOR SUBJECT NAMES AND DIVISIONS
        String URL1 = "https://stocky-baud.000webhostapp.com/getSemFromTSA.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(teacher_student_attendance_view.this);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            sem_auto_comp.setEnabled(true);
                            JSONArray array = new JSONArray(response);
                            int AL = array.length(),j=0;
                            sem_val = new String[AL];
                            for (j = 0; j < AL; j++) {
                                JSONObject object = array.getJSONObject(j);
                                sem_val[j] = object.getString("sem");
                            }
                            sem_adap_items = new ArrayAdapter<String>(teacher_student_attendance_view.this,R.layout.leave_staffname_dropdown,sem_val);
                            sem_auto_comp.setAdapter(sem_adap_items);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(teacher_student_attendance_view.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
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
        sem_auto_comp.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            SelectedSem = adapterView.getItemAtPosition(i).toString();
            String URL2 = "https://stocky-baud.000webhostapp.com/getEnrForTSA.php";
            //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
            RequestQueue queue1 = Volley.newRequestQueue(teacher_student_attendance_view.this);
            //STRING REQUEST OBJECT INITIALIZATION
            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                stu_auto_comp.setEnabled(true);
                                JSONArray array = new JSONArray(response);
                                int AL = array.length(),j=0;
                                student_enr = new String[AL];
                                for (j = 0; j < AL; j++) {
                                    JSONObject object = array.getJSONObject(j);
                                    student_enr[j] = object.getString("enr_no");
                                }
                                stu_adap_items = new ArrayAdapter<String>(teacher_student_attendance_view.this,R.layout.leave_staffname_dropdown, student_enr);
                                stu_auto_comp.setAdapter(stu_adap_items);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(teacher_student_attendance_view.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                //GIVING INPUT TO PHP API THROUGH MAP
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("sem", SelectedSem);
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue1.add(stringRequest1);
            sem_auto_comp.clearFocus();

        });
        stu_auto_comp.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            SelectedEnr = adapterView.getItemAtPosition(i).toString();
            String URL3 = "https://stocky-baud.000webhostapp.com/getSubForTSA.php";
            //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
            RequestQueue queue2 = Volley.newRequestQueue(teacher_student_attendance_view.this);
            //STRING REQUEST OBJECT INITIALIZATION
            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL3,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                subject.setEnabled(true);
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
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(teacher_student_attendance_view.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                //GIVING INPUT TO PHP API THROUGH MAP
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    Log.d("ABCDEF",SelectedSem+" "+SFT.getLogin());
                    params.put("sem", SelectedSem);
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
            queue2.add(stringRequest2);
            stu_auto_comp.clearFocus();
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
        subject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bol) {
                if(bol)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_student_attendance_view.this);
                    builder.setTitle("Subject");
                    builder.setMultiChoiceItems(subject_list, checked_sub_list, (dialogInterface, i, b) -> {
                        if(b){
                            if(!sub_selected_pos.contains(i)){
                                sub_selected_pos.add(i);
                                subject.clearFocus();
                                subject.setInputType(InputType.TYPE_NULL);
                            }
                        }
                        else{
                            sub_selected_pos.remove((Integer) i);
                        }
                    });
                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
                        StringBuilder sub_selected_val = new StringBuilder();
                        for(int count_val = 0; count_val < sub_selected_pos.size() ; count_val++)
                        {
                            sub_selected_val.append(subject_list[sub_selected_pos.get(count_val)]);
                            if (count_val + 1 != sub_selected_pos.size()) {
                                sub_selected_val.append(",");
                            }
                        }
                        subject.setText(sub_selected_val.toString());
                        SelectedSubject = subject.getText().toString();
                    });
                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.show();
                    sub_selected_pos.clear();
                }
            }
        });
        generate_button.setOnClickListener(v -> {
            final String class_name = getLocalClassName();
            Intent i = new Intent(teacher_student_attendance_view.this, attendance_display_wv.class);
            i.putExtra("class_name",class_name);
            i.putExtra("Semester",SelectedSem);
            i.putExtra("Enrollment",SelectedEnr);
            i.putExtra("Subject",SelectedSubject);
            i.putExtra("to_date",to_Date.getText().toString());
            i.putExtra("from_date",from_Date.getText().toString());
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
        DatePickerDialog d = new DatePickerDialog(teacher_student_attendance_view.this, dpd,year,month,day);
        d.show();
    }
}
