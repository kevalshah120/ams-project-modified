package com.example.splashscreen;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class stu_lecture_adapter extends RecyclerView.Adapter<stu_lecture_adapter.MyViewHolder> {
    private List<stu_lecture_model> lecture_data;
    Dialog dialog;
    private String attend_code;
    Context context;

    public stu_lecture_adapter(Context context, List<stu_lecture_model> lecture_data) {
        this.context = context;
        this.lecture_data = lecture_data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.stu_lecture_cv, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String subject_name = lecture_data.get(position).getSubject_name();
        String prof_name = lecture_data.get(position).getProf_name();
        holder.setData(subject_name, prof_name);
        holder.markButton.setOnClickListener(view -> {
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.attendance_code_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            builder.setTitle("Attendance Code");
            Button submit = dialogView.findViewById(R.id.submitbutton);
            EditText attendance_code = dialogView.findViewById(R.id.attendance_code);
            dialog = builder.create();
            dialog.show();
            submit.setOnClickListener(view1 -> {
                attend_code = attendance_code.getText().toString();
                Toast.makeText(context, attend_code, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
