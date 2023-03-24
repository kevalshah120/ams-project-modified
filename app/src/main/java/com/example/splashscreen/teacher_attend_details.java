package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class teacher_attend_details extends AppCompatActivity {
    TextView sub_name_tv;
    TextView div_tv;
    String[] sub_name;
    String[] sub_code;
    String[] div_list;
    boolean[] checked_divs;
    SeekBar expiry_time_slider;
    TextView expiry_time_tv;
    LinearLayout seekbar_layout;
    RelativeLayout loc_check_layout;
    Switch smart_attend_swi, loc_check_swi;
    boolean smart_attend_bol, loc_check_bol;
    ArrayList<Integer> div_selected_pos = new ArrayList<>();
    String subject_selected;
    Button take_attend_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attend_details);
        sub_name_tv = findViewById(R.id.sub_name_tv);
        div_tv = findViewById(R.id.div_tv);
        expiry_time_slider = findViewById(R.id.expiry_time_seekbar);
        expiry_time_tv = findViewById(R.id.expiry_time_tv);
        smart_attend_swi = findViewById(R.id.smart_attend_switch);
        loc_check_swi = findViewById(R.id.location_check_switch);
        seekbar_layout = findViewById(R.id.seekbar_layout);
        loc_check_layout = findViewById(R.id.loc_check_layout);
        seekbar_layout.setVisibility(View.GONE);
        loc_check_layout.setVisibility(View.GONE);
        take_attend_button = findViewById(R.id.take_attend_btn);
        // FETCHING DATA FOR SUBJECT NAMES AND DIVISIONS
        String URL = "https://stocky-baud.000webhostapp.com/getSubjectsForTeacher.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(teacher_attend_details.this);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            sub_name = new String[array.length()];
                            sub_code = new String[array.length()];
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                sub_name[i] = object.getString("sub_name");
                                sub_code[i] = object.getString("sub_code");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(teacher_attend_details.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("T_ID", "san12");
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
        sub_name_tv.setOnClickListener(view -> {
            if (sub_name == null) {
                // FETCHING DATA FOR SUBJECT NAMES AND DIVISIONS
                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                RequestQueue qqueue = Volley.newRequestQueue(teacher_attend_details.this);
                //STRING REQUEST OBJECT INITIALIZATION
                StringRequest sstringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    sub_name = new String[array.length()];
                                    sub_code = new String[array.length()];
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        sub_name[i] = object.getString("sub_name");
                                        sub_code[i] = object.getString("sub_code");
                                    }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_attend_details.this);
                                    builder.setTitle("Subjects");
                                    builder.setSingleChoiceItems(sub_name, -1, (dialogInterface, i) -> subject_selected = sub_name[i]);
                                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                        sub_name_tv.setText(subject_selected);
                                        dialogInterface.dismiss();
                                    });
                                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                                    builder.show();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(teacher_attend_details.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    //GIVING INPUT TO PHP API THROUGH MAP
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("T_ID", "san12");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                qqueue.add(sstringRequest);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(teacher_attend_details.this);
                builder.setTitle("Subjects");
                builder.setSingleChoiceItems(sub_name, -1, (dialogInterface, i) -> subject_selected = sub_name[i]);
                builder.setPositiveButton("OK", (dialogInterface, i) -> {
                    sub_name_tv.setText(subject_selected);
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            }
        });
        div_tv.setOnClickListener(view -> {
            if (sub_name_tv.getText().toString() != "") {
                div_tv.setText("");
                String UrL = "https://stocky-baud.000webhostapp.com/getDivForTeacher.php";
                //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                RequestQueue Queue = Volley.newRequestQueue(teacher_attend_details.this);
                //STRING REQUEST OBJECT INITIALIZATION
                StringRequest StringRequest = new StringRequest(Request.Method.POST, UrL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    div_list = new String[array.length()];
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        div_list[i] = object.getString("division");
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(teacher_attend_details.this);
                                builder.setTitle("Division");
                                builder.setMultiChoiceItems(div_list, checked_divs, (dialogInterface, i, b) -> {
                                    if (b) {
                                        if (!div_selected_pos.contains(i)) {
                                            div_selected_pos.add(i);
                                        }
                                    } else {
                                        div_selected_pos.remove((Integer) i);
                                    }
                                });
                                builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                    StringBuilder div_selected_val = new StringBuilder();
                                    for (int count_val = 0; count_val < div_selected_pos.size(); count_val++) {
                                        div_selected_val.append(div_list[div_selected_pos.get(count_val)]);
                                        if (count_val + 1 != div_selected_pos.size()) {
                                            div_selected_val.append(",");
                                        }
                                    }

                                    div_tv.setText(div_selected_val.toString());
                                });
                                builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                                builder.show();
                                div_selected_pos.clear();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(teacher_attend_details.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    //GIVING INPUT TO PHP API THROUGH MAP
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        int i = Arrays.asList(sub_name).indexOf(sub_name_tv.getText().toString());
                        params.put("s_code", sub_code[i]);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                Queue.add(StringRequest);
            }
        });
        expiry_time_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                expiry_time_tv.setText(String.valueOf(i + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        smart_attend_swi.setOnClickListener(view -> {
            if (smart_attend_swi.isChecked()) {
                smart_attend_bol = true;
                seekbar_layout.setVisibility(View.VISIBLE);
                loc_check_layout.setVisibility(View.VISIBLE);
            } else {
                smart_attend_bol = false;
                seekbar_layout.setVisibility(View.GONE);
                loc_check_layout.setVisibility(View.GONE);
            }
        });
        loc_check_swi.setOnClickListener(view -> {
            loc_check_bol = loc_check_swi.isChecked();
        });

        take_attend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sub_name_tv.getText().toString() != "" && div_tv.getText().toString() != "") {
                Intent i = new Intent(teacher_attend_details.this, teacher_mark_Attendance.class);
                i.putExtra("expiry_time", String.valueOf(expiry_time_tv.getText()));
                i.putExtra("smart_attend_switch", smart_attend_bol);
                i.putExtra("location",loc_check_bol);
                i.putExtra("subject", sub_name_tv.getText().toString());
                i.putExtra("division", div_tv.getText().toString());
                startActivity(i);
                finish();
            }
            }
        });
    }
}