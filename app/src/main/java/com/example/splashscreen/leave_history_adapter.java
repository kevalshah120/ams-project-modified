package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class leave_history_adapter extends RecyclerView.Adapter<leave_history_adapter.MyViewHolder>{
    private List<leave_history_model> leave_history_data;
    Context context;
    String stu_name,leave_name,description,sem_no,date;
    public leave_history_adapter(Context context, List<leave_history_model> leave_history_data) {
        this.context = context;
        this.leave_history_data = leave_history_data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.leave_history_cv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        stu_name = leave_history_data.get(position).getStu_name();
        date = leave_history_data.get(position).getDate();
        sem_no = leave_history_data.get(position).getSem_no();
        leave_name = leave_history_data.get(position).getLeave_name();
        description = leave_history_data.get(position).getDescription();
        int tag = leave_history_data.get(position).getTag();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.showDesc(holder);
            }
        });
        holder.setData(stu_name,date,sem_no,leave_name,description,tag);
    }

    @Override
    public int getItemCount() {
        return leave_history_data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1_leave_name;
        private final TextView textView2_stu_name;
        private final TextView textView3_date;
        private final TextView textView4_sem_no;

        private final ImageView Iamgeview_tag;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1_leave_name = itemView.findViewById(R.id.leave_name);
            textView2_stu_name = itemView.findViewById(R.id.student_name);
            textView3_date = itemView.findViewById(R.id.leave_date);
            textView4_sem_no = itemView.findViewById(R.id.sem_no);
            Iamgeview_tag = itemView.findViewById(R.id.status_tag);

        }

        public void setData(String student_name, String date, String sem_no, String leave_name, String description, int tag) {
            textView1_leave_name.setText(leave_name);
            textView2_stu_name.setText(student_name);
            textView3_date.setText(date);
            textView4_sem_no.setText(sem_no);
            Iamgeview_tag.setImageResource(tag);
        }
        private void showDesc(leave_history_adapter.MyViewHolder holder)
        {
            Intent i = new Intent(holder.itemView.getContext(),leave_details_display.class);
            i.putExtra("leave_name",leave_name);
            i.putExtra("Date",date);
            i.putExtra("desc",description);
            i.putExtra("stu_name",stu_name);
            holder.itemView.getContext().startActivity(i);
        }
    }
}
