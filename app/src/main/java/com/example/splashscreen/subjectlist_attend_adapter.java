package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class subjectlist_attend_adapter extends RecyclerView.Adapter<subjectlist_attend_adapter.MyViewHolder> {
    private List<subjectlist_attend_model> subject_Data;
    Context context;
    String Enrollment_no;
    sessionForS SFS;
    public subjectlist_attend_adapter(Context context, List<subjectlist_attend_model> subject_data) {
        this.context = context;
        this.subject_Data = subject_data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_attend_cv, parent, false);
        SFS = new sessionForS(view.getContext());
        Enrollment_no = SFS.getEnrollment();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String subject_name = subject_Data.get(position).getSubject_name();
        String sub_percentage = subject_Data.get(position).getAttend_percentage();
        // Make changes in this onclicklistener and pass the appropriate data qccording to the cardview that is clicked
        holder.setData(subject_name,sub_percentage);
        holder.subject_attend_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(),stu_sub_attendance_view.class);
                i.putExtra("subject_name",subject_name);
                i.putExtra("enr_no",Enrollment_no);
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subject_Data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_subject_name;
        private final TextView textView3_sub_precentage;

        private final CardView subject_attend_cv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_subject_name = itemView.findViewById(R.id.subject_name);
            textView3_sub_precentage = itemView.findViewById(R.id.percentage_tv);
            subject_attend_cv = itemView.findViewById(R.id.subject_attend_cv);
        }
        public void setData(String subject_name,String sub_percentage) {
            textView1_subject_name.setText(subject_name);
            textView3_sub_precentage.setText(sub_percentage);
        }
    }
}
