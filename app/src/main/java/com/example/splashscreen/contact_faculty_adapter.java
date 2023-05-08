package com.example.splashscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class contact_faculty_adapter extends RecyclerView.Adapter<contact_faculty_adapter.MyViewHolder>{
    private List<contact_faculty_model> contact_faculty_data;
    Context context;

    public contact_faculty_adapter(Context context, List<contact_faculty_model> contact_faculty_data) {
        this.context = context;
        this.contact_faculty_data = contact_faculty_data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_faculty_cv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String prof_name = contact_faculty_data.get(position).getProf_name();
        String prof_num = contact_faculty_data.get(position).getProf_num();

        holder.setData(prof_name,prof_num);
    }

    @Override
    public int getItemCount() {
        return contact_faculty_data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_prof_name;
        private final TextView textView2_prof_num;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_prof_name = itemView.findViewById(R.id.prof_name);
            textView2_prof_num = itemView.findViewById(R.id.prof_num);
        }

        public void setData(String prof_name, String prof_num) {
            textView1_prof_name.setText(prof_name);
            textView2_prof_num.setText(prof_num);
        }
    }
}
