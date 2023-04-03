package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.Map;

public class teacher_mark_attend_adapter extends RecyclerView.Adapter<teacher_mark_attend_adapter.MyViewHolder>{
    private List<teacher_mark_attend_model> teacher_mark_attend_models;
    Context context;

    public teacher_mark_attend_adapter(Context context, List<teacher_mark_attend_model> teacher_mark_attend_models) {
        this.context = context;
        this.teacher_mark_attend_models = teacher_mark_attend_models;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.teacher_mark_attend_cv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String stu_name = teacher_mark_attend_models.get(position).getStu_name();
        String enr_no = teacher_mark_attend_models.get(position).getEnr_no();
        boolean status = teacher_mark_attend_models.get(position).getAtdStatus();
        String subject_name = teacher_mark_Attendance.subject_name;
        holder.setData(stu_name,enr_no,status);
        holder.absent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.absent_button.isSelected())
                {
                    setAbsent(subject_name, enr_no, holder);
                }
//                Toast.makeText(context,"absent",Toast.LENGTH_SHORT).show();
//                holder.absent_button.setImageResource(R.drawable.absent_focused);
//                holder.present_button.setImageResource(R.drawable.present);
            }
        });
        holder.present_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.present_button.isSelected())
                {
                    setPresent(subject_name,enr_no,holder);
                }

//                Toast.makeText(context,"present",Toast.LENGTH_SHORT).show();
//                holder.absent_button.setImageResource(R.drawable.absent);
//                holder.present_button.setImageResource(R.drawable.present_focused);
            }
        });
    }
    private void setPresent(String sub_name,String enrollment,MyViewHolder holder)
    {
        String URL = "https://stocky-baud.000webhostapp.com/setPresentButton.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("setPresent",response);
                        try {
                            String currentP = teacher_mark_Attendance.studentCount.getText().toString();
                            StringBuilder SB = new StringBuilder(2);
                            int i = 0;
                            while(i<currentP.length())
                            {
                                if(currentP.charAt(i) == '/')
                                {
                                    break;
                                }
                                SB.append(currentP.charAt(i));
                                i++;
                            }
                            i = Integer.valueOf(SB.toString())+1;
                            teacher_mark_Attendance.studentCount.setText(String.valueOf(i)+"/"+String.valueOf(teacher_mark_Attendance.TotalStudents));
                            String result = new JSONObject(response).getString("result");
                            if(result.equals("1"))
                            {
                                holder.absent_button.setSelected(false);
                                holder.present_button.setSelected(true);
                                holder.absent_button.setImageResource(R.drawable.absent);
                                holder.present_button.setImageResource(R.drawable.present_focused);
                            }
                            else
                            {
                                Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sub_name",sub_name);
                params.put("enrollment",enrollment);
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
    private void setAbsent(String sub_name,String enrollment,MyViewHolder holder)
    {
        String URL = "https://stocky-baud.000webhostapp.com/setAbsentButton.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        Log.d("setAbsent",response);
                        try {
                            String currentP = teacher_mark_Attendance.studentCount.getText().toString();
                            StringBuilder SB = new StringBuilder(2);
                            int i = 0;
                            while(i<currentP.length())
                            {
                                if(currentP.charAt(i) == '/')
                                {
                                    break;
                                }
                                SB.append(currentP.charAt(i));
                                i++;
                            }
                            i = Integer.parseInt(SB.toString())-1;
                            teacher_mark_Attendance.studentCount.setText(i +"/"+String.valueOf(teacher_mark_Attendance.TotalStudents));
                            String result = new JSONObject(response).getString("result");
                            if(result.equals("1"))
                            {
                                holder.absent_button.setSelected(true);
                                holder.present_button.setSelected(false);
                                holder.absent_button.setImageResource(R.drawable.absent_focused);
                                holder.present_button.setImageResource(R.drawable.present);
                            }
                            else
                            {
                                Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sub_name",sub_name);
                params.put("enrollment",enrollment);
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
    @Override
    public int getItemCount() {
        return teacher_mark_attend_models.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_stu_name;
        private final TextView textView2_enr_no;
        private final ImageButton present_button;
        private final ImageButton absent_button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_stu_name = itemView.findViewById(R.id.student_name);
            textView2_enr_no = itemView.findViewById(R.id.student_enr_no);
            present_button = itemView.findViewById(R.id.present_icon);
            absent_button = itemView.findViewById(R.id.absent_icon);
        }

        public void setData(String student_name, String enr_no, boolean status) {
            textView1_stu_name.setText(student_name);
            textView2_enr_no.setText(enr_no);
            if(status == true)
            {
                absent_button.setSelected(false);
                absent_button.setImageResource(R.drawable.absent);
                present_button.setSelected(true);
                present_button.setImageResource(R.drawable.present_focused);
            }
            else
            {
                present_button.setSelected(false);
                present_button.setImageResource(R.drawable.present);
                absent_button.setSelected(true);
                absent_button.setImageResource(R.drawable.abs_imgbtn_state);
            }
        }
    }
}
