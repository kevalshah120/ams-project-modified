package com.example.splashscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class subjectlist_attend_adapter extends RecyclerView.Adapter<subjectlist_attend_adapter.MyViewHolder> {
    private List<subjectlist_attend_model> subject_Data;
    Context context;
    public subjectlist_attend_adapter(Context context, List<subjectlist_attend_model> subject_data) {
        this.context = context;
        this.subject_Data = subject_data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_attend_cv, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String subject_name = subject_Data.get(position).getSubject_name();
        String prof_name = subject_Data.get(position).getProf_name();
        String sub_percentage = subject_Data.get(position).getAttend_percentage();
        holder.setData(subject_name, prof_name,sub_percentage);
    }

    @Override
    public int getItemCount() {
        return subject_Data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_subject_name;
        private final TextView textView2_prof_name;
        private final TextView textView3_sub_precentage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_subject_name = itemView.findViewById(R.id.subject_name);
            textView2_prof_name = itemView.findViewById(R.id.prof_name);
            textView3_sub_precentage = itemView.findViewById(R.id.percentage_tv);
        }
        public void setData(String subject_name, String prof_name,String sub_percentage) {
            textView1_subject_name.setText(subject_name);
            textView2_prof_name.setText(prof_name);
            textView3_sub_precentage.setText(sub_percentage);
        }
    }
}
