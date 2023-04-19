package com.example.splashscreen;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class student_list_adapter extends RecyclerView.Adapter<student_list_adapter.MyViewHolder>{
    private List<teacher_student_model> teacher_mark_attend_models;
    Context context;

    public student_list_adapter(Context context, List<teacher_student_model> teacher_mark_attend_models) {
        this.context = context;
        this.teacher_mark_attend_models = teacher_mark_attend_models;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_list_cv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String stu_name = teacher_mark_attend_models.get(position).getStu_name();
        String enr_no = teacher_mark_attend_models.get(position).getEnr_no();
        holder.setData(stu_name,enr_no);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(),student_details.class);
                i.putExtra("enrollment",enr_no);
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacher_mark_attend_models.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_stu_name;
        private final TextView textView2_enr_no;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_stu_name = itemView.findViewById(R.id.student_name);
            textView2_enr_no = itemView.findViewById(R.id.student_enr_no);
        }

        public void setData(String student_name, String enr_no) {
            textView1_stu_name.setText(student_name);
            textView2_enr_no.setText(enr_no);
        }
    }
}
