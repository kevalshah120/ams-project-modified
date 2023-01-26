package com.example.splashscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class stu_lecture_adapter extends RecyclerView.Adapter<stu_lecture_adapter.MyViewHolder> {
    private List<stu_lecture_model> lecture_data;
    Context context;
    public stu_lecture_adapter(Context context,List<stu_lecture_model>lecture_data)
    {
        this.context = context;
        this.lecture_data = lecture_data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.stu_lecture_cv,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String subject_name = lecture_data.get(position).getSubject_name();
        String prof_name = lecture_data.get(position).getProf_name();
        holder.setData(subject_name,prof_name);
    }

    @Override
    public int getItemCount() {
        return lecture_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_subject_name;
        private final TextView textView2_prof_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_subject_name = itemView.findViewById(R.id.subject_name);
            textView2_prof_name = itemView.findViewById(R.id.prof_name);
        }

        public void setData(String subject_name,String prof_name) {
            textView1_subject_name.setText(subject_name);
            textView2_prof_name.setText(prof_name);
        }
    }
}
