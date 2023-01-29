package com.example.splashscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class teacher_leave_adapter extends RecyclerView.Adapter<teacher_leave_adapter.MyViewHolder> {
    private List<teacher_leave_model> tea_leave_data;
    Context context;

    public teacher_leave_adapter(Context context, List<teacher_leave_model> tea_leave_data) {
        this.context = context;
        this.tea_leave_data = tea_leave_data;
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
        String leave_name = tea_leave_data.get(position).getLeave_name();
        String stu_name = tea_leave_data.get(position).getStu_name();
        String date = tea_leave_data.get(position).getDate();
        String sem_no = tea_leave_data.get(position).getSem_no();
        holder.setData(leave_name, stu_name,date,sem_no);
        holder.approve_button.setOnClickListener(view -> {
            Toast.makeText(context, "approved", Toast.LENGTH_SHORT).show();

        });

        holder.reject_button.setOnClickListener(view -> {
            Toast.makeText(context, "rejected", Toast.LENGTH_SHORT).show();
        });
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_leave_name = itemView.findViewById(R.id.leave_name);
            textView2_stu_name = itemView.findViewById(R.id.student_name);
            textView3_date = itemView.findViewById(R.id.leave_date);
            textView4_sem_no = itemView.findViewById(R.id.sem_no);
            approve_button = itemView.findViewById(R.id.approve_icon);
            reject_button = itemView.findViewById(R.id.reject_icon);

        }

        public void setData(String leave_name, String stu_name,String date,String sem_no) {
            textView1_leave_name.setText(leave_name);
            textView2_stu_name.setText(stu_name);
            textView3_date.setText(date);
            textView4_sem_no.setText(sem_no);
        }
    }
}
