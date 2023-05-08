package com.example.splashscreen;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class stu_lecture_adapter extends RecyclerView.Adapter<stu_lecture_adapter.MyViewHolder> {
    private List<stu_lecture_model> lecture_data;
    Dialog dialog;
    public String attend_code;
    ProgressBar pgbar;
    Context context;
    sessionForS SFS;
    static String Enrollment_No;
    public stu_lecture_adapter(Context context, List<stu_lecture_model> lecture_data) {
        this.context = context;
        this.lecture_data = lecture_data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SFS = new sessionForS(context.getApplicationContext());
        Enrollment_No = SFS.getEnrollment();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.stu_lecture_cv, parent, false);
         SFS = new sessionForS(view.getContext());
//        MyViewHolder viewHolder = new MyViewHolder(view);
//        return viewHolder;
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String subject_name = lecture_data.get(position).getSubject_name();
        String prof_name = lecture_data.get(position).getProf_name();
        int staff_id = lecture_data.get(position).getStaff_id();
        String subject_code = lecture_data.get(position).getSubject_code();
        String LAB = lecture_data.get(position).getLAB();
        String location = lecture_data.get(position).getLocation();
        holder.setData(subject_name, prof_name);
        holder.markButton.setOnClickListener(view -> {
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.attendance_code_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            builder.setTitle("Attendance Code");
            Button submit = dialogView.findViewById(R.id.submitbutton);
            pgbar = dialogView.findViewById(R.id.pgbar);
            EditText attendance_code = dialogView.findViewById(R.id.attendance_code);
            dialog = builder.create();
            dialog.show();
            submit.setOnClickListener(view1 -> {
                pgbar.setVisibility(View.VISIBLE);
                holder.markButton.setVisibility(View.INVISIBLE);
                attend_code = attendance_code.getText().toString();
                String URL = "https://stocky-baud.000webhostapp.com/markAttendanceWithOTP.php";
                if(location.equals("1"))
                {
                    String currentLocation = SFS.getLocation();
                    if(currentLocation.equals("Navarang Society")) {
                        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                        //STRING REQUEST OBJECT INITIALIZATION
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("RESULT", response);
                                            JSONArray Jarry = new JSONArray(response);
                                            JSONObject Jobj = Jarry.getJSONObject(0);
                                            Log.d("RESULT", response);
                                            String RESULT = Jobj.getString("RESULT");
                                            pgbar.setVisibility(View.INVISIBLE);
                                            holder.markButton.setVisibility(View.VISIBLE);
                                            Toast.makeText(context, RESULT, Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pgbar.setVisibility(View.INVISIBLE);
                                holder.markButton.setVisibility(View.VISIBLE);
                                Toast.makeText(context.getApplicationContext(), "Connectivity Error", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            //GIVING INPUT TO PHP API THROUGH MAP
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("enrollment", Enrollment_No);
                                params.put("staff_id", String.valueOf(staff_id));
                                params.put("sub_code", subject_code);
                                params.put("sub_name", subject_name);
                                params.put("OTP", attendance_code.getText().toString());
                                params.put("LAB", LAB);
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
                        Toast.makeText(context, "Location Mismatched", Toast.LENGTH_SHORT).show();
                        pgbar.setVisibility(View.INVISIBLE);
                        holder.markButton.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                }
                else {
                    //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
                    RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                    //STRING REQUEST OBJECT INITIALIZATION
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("RESULT", response);
                                        JSONArray Jarry = new JSONArray(response);
                                        JSONObject Jobj = Jarry.getJSONObject(0);
                                        Log.d("RESULT", response);
                                        String RESULT = Jobj.getString("RESULT");
                                        pgbar.setVisibility(View.INVISIBLE);
                                        holder.markButton.setVisibility(View.VISIBLE);
                                        Toast.makeText(context, RESULT, Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pgbar.setVisibility(View.INVISIBLE);
                            holder.markButton.setVisibility(View.VISIBLE);
                            Toast.makeText(context.getApplicationContext(), "Connectivity Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        //GIVING INPUT TO PHP API THROUGH MAP
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("enrollment", Enrollment_No);
                            params.put("staff_id", String.valueOf(staff_id));
                            params.put("sub_code", subject_code);
                            params.put("sub_name", subject_name);
                            params.put("OTP", attendance_code.getText().toString());
                            params.put("LAB", LAB);
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

        });
    }

    @Override
    public int getItemCount() {
        return lecture_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_subject_name;
        private final TextView textView2_prof_name;
        private final Button markButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_subject_name = itemView.findViewById(R.id.subject_name);
            textView2_prof_name = itemView.findViewById(R.id.prof_name);
            markButton = itemView.findViewById(R.id.mark_button);
        }

        public void setData(String subject_name, String prof_name) {
            textView1_subject_name.setText(subject_name);
            textView2_prof_name.setText(prof_name);
        }
    }

}
