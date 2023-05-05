package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class today_attendance_taken_adapter extends RecyclerView.Adapter<today_attendance_taken_adapter.MyViewHolder> {
    private List<today_attendance_taken_model> Today_Attendance_Taken_Data;
    Context context;
    public today_attendance_taken_adapter(Context context, List<today_attendance_taken_model> Today_Attendance_Taken_Data) {
        this.context = context;
        this.Today_Attendance_Taken_Data = Today_Attendance_Taken_Data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.teacher_today_attendance_taken_cv, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String subject_name = Today_Attendance_Taken_Data.get(position).getSubject_name();
        String student_present_count = Today_Attendance_Taken_Data.get(position).getStudent_present_count();
        String division = Today_Attendance_Taken_Data.get(position).getDivision();
        holder.setData(subject_name, student_present_count,division);
        // Make changes in this onclicklistener and pass the appropriate data qccording to the cardview that is clicked
        holder.today_attendance_taken_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "clicked" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Today_Attendance_Taken_Data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_subject_name;
        private final TextView textView2_student_count;
        private final TextView textView3_division;

        private final CardView today_attendance_taken_cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_subject_name = itemView.findViewById(R.id.subject_name);
            textView2_student_count = itemView.findViewById(R.id.student_present_count);
            textView3_division = itemView.findViewById(R.id.div);
            today_attendance_taken_cv = itemView.findViewById(R.id.today_attendance_taken_cv);
        }
        public void setData(String subject_name, String student_present_count,String division) {
            textView1_subject_name.setText(subject_name);
            textView2_student_count.setText(student_present_count);
            textView3_division.setText(division);
        }
    }
}
