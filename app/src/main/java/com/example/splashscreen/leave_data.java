package com.example.splashscreen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class leave_data extends AppCompatActivity {
    TextInputEditText leave_desc,leave_name, from_Date, to_Date;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    ProgressBar pgbar;
    int month = calendar.get(Calendar.MONTH);
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapteritems;
    String[] staff_id;
    String[] staff_name;
    Button submit;
    sessionForS SFS;
    static String Enrollment_No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_data);
        leave_name = findViewById(R.id.leave_name);
        from_Date = findViewById(R.id.from_date);
        to_Date = findViewById(R.id.to_date);
        pgbar = findViewById(R.id.pgbar);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        submit = findViewById(R.id.submit_button);
        leave_desc = findViewById(R.id.Leave_description);
        SFS = new sessionForS(getApplication());
        Enrollment_No = SFS.getEnrollment();
        //-----------------------------------GETTING STAFF NAME BELOW----------------------------------
        String URL = "https://stocky-baud.000webhostapp.com/getStaffName.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(leave_data.this);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            staff_name = new String[array.length()];
                            staff_id = new String[array.length()];
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                staff_name[i] = object.getString("staff_name");
                                staff_id[i] = object.getString("staff_id");
                            }
                            adapteritems = new ArrayAdapter<String>(leave_data.this, R.layout.leave_staffname_dropdown, staff_name);
                            autoCompleteTxt.setAdapter(adapteritems);
                            autoCompleteTxt.setOnItemClickListener((adapterView, view, i, l) -> {
                                String item = adapterView.getItemAtPosition(i).toString();
                                Toast.makeText(leave_data.this, item, Toast.LENGTH_SHORT).show();
                            });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(leave_data.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("enrollment", Enrollment_No);
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
        //-----------------------------------GETTING STAFF NAME ABOVE----------------------------------
        submit.setOnClickListener(v -> {
            pgbar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.INVISIBLE);
            String Leave_name = leave_name.getText().toString();
            String S_name = autoCompleteTxt.getText().toString();
            String from_DATE = from_Date.getText().toString();
            String to_DATE = to_Date.getText().toString();
            String LeaveD = leave_desc.getText().toString();
            String S_id;
            if (Arrays.asList(staff_name).indexOf(S_name) != -1) {
                S_id = staff_id[Arrays.asList(staff_name).indexOf(S_name)];
            } else {
                S_id = "-1";
            }
            if (Leave_name.equals("") || S_name.equals("") || from_DATE.equals("") || to_DATE.equals("") || LeaveD.equals("") || S_name.equals("")) {
                Toast.makeText(leave_data.this, "Enter all details", Toast.LENGTH_SHORT).show();
                pgbar.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.VISIBLE);
            } else {
                String to_year = "";
                String from_month = "";
                String from_date = "";
                String from_year = "";
                String to_date = "";
                String to_month = "";
                int dashFOUND = 0;
                int fromSize = from_DATE.length();
                int toSize = to_DATE.length();
                int i = 0;
                while (i < fromSize) {
                    if (dashFOUND == 0) {
                        if (from_DATE.charAt(i) == ' ') {
                            dashFOUND++;
                        } else {
                            from_date += from_DATE.charAt(i);
                        }
                    } else if (dashFOUND == 1) {
                        if (from_DATE.charAt(i) == ' ') {
                            dashFOUND++;
                        } else {
                            from_month += from_DATE.charAt(i);
                        }
                    } else {
                        if (from_DATE.charAt(i) == ' ') {
                            dashFOUND++;
                        } else {
                            from_year += from_DATE.charAt(i);
                        }
                    }
                    i++;
                }
                i = 0;
                dashFOUND = 0;
                while (i < toSize) {
                    if (dashFOUND == 0) {
                        if (to_DATE.charAt(i) == ' ') {
                            dashFOUND++;
                        } else {
                            to_date += to_DATE.charAt(i);
                        }
                    } else if (dashFOUND == 1) {
                        if (to_DATE.charAt(i) == ' ') {
                            dashFOUND++;
                        } else {
                            to_month += to_DATE.charAt(i);
                        }
                    } else {
                        if (to_DATE.charAt(i) == ' ') {
                            dashFOUND++;
                        } else {
                            to_year += to_DATE.charAt(i);
                        }
                    }
                    i++;
                }
                from_DATE = from_year + "-" + from_month + "-" + from_date;
                to_DATE = to_year + "-" + to_month + "-" + to_date;
                String URL1 = "https://stocky-baud.000webhostapp.com/setLeavetbDATA.php";
                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                RequestQueue queue1 = Volley.newRequestQueue(leave_data.this);
                //STRING REQUEST OBJECT INITIALIZATION
                String finalFrom_DATE = from_DATE;
                String finalTo_DATE = to_DATE;
                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String result = object.getString("RES");
                                    if (result.equals("1")) {
                                        Toast.makeText(leave_data.this, "Submitted Succesfully", Toast.LENGTH_SHORT).show();
                                        pgbar.setVisibility(View.INVISIBLE);
                                        submit.setVisibility(View.VISIBLE);
                                        Intent i = new Intent(leave_data.this, student_homescreen.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(leave_data.this, "Unknown ERROR", Toast.LENGTH_SHORT).show();
                                        pgbar.setVisibility(View.INVISIBLE);
                                        submit.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(leave_data.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                        pgbar.setVisibility(View.INVISIBLE);
                        submit.setVisibility(View.VISIBLE);
                    }
                }) {
                    @Override
                    //GIVING INPUT TO PHP API THROUGH MAP
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Lname", Leave_name);
                        params.put("fDATE", finalFrom_DATE);
                        params.put("tDATE", finalTo_DATE);
                        params.put("sID", S_id);
                        params.put("Ldesc", LeaveD);
                        params.put("enrollment", Enrollment_No);
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
            }
        });
        from_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    datepicker_fun(from_Date);
                }
            }
        });
        to_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    datepicker_fun(to_Date);
                }
            }
        });
    }

    private void datepicker_fun(EditText date_text) {
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i1, int i2, int i3) {
                int month = i2 + 1;
                date_text.setText(i3 + " " + month + " " + i1);
            }
        };
        DatePickerDialog d = new DatePickerDialog(leave_data.this, dpd, year, month, day);
        d.show();
    }
}