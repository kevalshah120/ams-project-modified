package com.example.splashscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

public class teacher_leave_adapter extends RecyclerView.Adapter<teacher_leave_adapter.MyViewHolder> {
    private List<teacher_leave_model> tea_leave_data;
    Context context;
    Activity activity;

    public teacher_leave_adapter(Activity activity , Context context, List<teacher_leave_model> tea_leave_data) {
        this.context = context;
        this.tea_leave_data = tea_leave_data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.teacher_leave_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String leave_name ,stu_name ,date ,sem_no ,leave_id ,desc;
         leave_name = tea_leave_data.get(position).getLeave_name();
         stu_name = tea_leave_data.get(position).getStu_name();
         date = tea_leave_data.get(position).getDate();
         sem_no = tea_leave_data.get(position).getSem_no();
         leave_id = tea_leave_data.get(position).getLeave_id();
        desc = tea_leave_data.get(position).getDescription();
        holder.setData(leave_name, stu_name, date, sem_no);
        holder.leave_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDesc(holder,leave_name,date,desc,sem_no);
            }
        });

        holder.approve_button.setOnClickListener(view -> {
            aORr(true, holder.approve_button.getContext(), leave_id);
        });

        holder.reject_button.setOnClickListener(view -> {
            aORr(false, holder.approve_button.getContext(), leave_id);
        });
    }

    private boolean aORr(boolean action, Context ct, String lID) {
        boolean result = false;
        //URL FOR FETCHING API DATA
        String URL = "https://stocky-baud.000webhostapp.com/leaveAppOrRej.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(ct);
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Jobj = new JSONObject(response);
                            String res = Jobj.getString("result");
                            if (res.equals("1")) {
                                Toast.makeText(ct, "DONE", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context,teacher_homescreen.class);
                                activity.startActivity(intent);
                                activity.finish();
                            } else {
                                Toast.makeText(ct, res + lID , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ct, "JSON ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct, "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Log.d("Leave_id_adapter",lID);
                params.put("id", lID);
                params.put("action", String.valueOf(action));
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
        return result;
    }

    private void showDesc(MyViewHolder holder,String leave_name,String date,String desc,String stu_name)
    {
        Intent i = new Intent(holder.itemView.getContext(),leave_details_display.class);
        i.putExtra("leave_name",leave_name);
        i.putExtra("Date",date);
        i.putExtra("desc",desc);
        i.putExtra("stu_name",stu_name);
        holder.itemView.getContext().startActivity(i);
    }
    @Override
    public int getItemCount() {
        return tea_leave_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_leave_name;
        private final TextView textView2_stu_name;
        private final TextView textView3_date;
        private final TextView textView4_sem_no;
        private final ImageButton approve_button;
        private final ImageButton reject_button;
        private final CardView leave_cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_leave_name = itemView.findViewById(R.id.leave_name);
            textView2_stu_name = itemView.findViewById(R.id.student_name);
            textView3_date = itemView.findViewById(R.id.leave_date);
            textView4_sem_no = itemView.findViewById(R.id.sem_no);
            approve_button = itemView.findViewById(R.id.approve_icon);
            reject_button = itemView.findViewById(R.id.reject_icon);
            leave_cv = itemView.findViewById(R.id.teacher_leave_cardview);
        }

        public void setData(String leave_name, String stu_name, String date, String sem_no) {
            textView1_leave_name.setText(leave_name);
            textView2_stu_name.setText(stu_name);
            textView3_date.setText(date);
            textView4_sem_no.setText(sem_no);
        }
    }
}
