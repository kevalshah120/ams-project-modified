package com.example.splashscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class    leave_rv_adapter extends RecyclerView.Adapter<leave_rv_adapter.MyViewHolder> {
    private List<leave_model_class> leave_data;
    Context context;
    public leave_rv_adapter(Context context,List<leave_model_class>leave_data)
    {
        this.context = context;
        this.leave_data = leave_data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.stu_leave_cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int tag = leave_data.get(position).getTag();
        int teacher_icon = leave_data.get(position).getTeacher_icon();
        String leave_name = leave_data.get(position).getLeave_name();
        String date = leave_data.get(position).getDate();
        String prof_name = leave_data.get(position).getProf_name();

        holder.setData(tag,teacher_icon,leave_name,date,prof_name);
    }

    @Override
    public int getItemCount() {
        return leave_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView_status_tag;
        private final ImageView imageView3_teacher_icon;
        private final TextView textView_leave_date;
        private final TextView textView2_leave_name;
        private final TextView textView3_leave_teacher_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_status_tag = itemView.findViewById(R.id.status_tag);
            imageView3_teacher_icon = itemView.findViewById(R.id.teacher_icon);
            textView_leave_date = itemView.findViewById(R.id.leave_date);
            textView2_leave_name = itemView.findViewById(R.id.leave_name);
            textView3_leave_teacher_name = itemView.findViewById(R.id.leave_teacher_name);

        }

        public void setData(int tag, int teacher_icon, String leave_name, String date, String prof_name) {
            imageView_status_tag.setImageResource(tag);
            imageView3_teacher_icon.setImageResource(teacher_icon);
            textView_leave_date.setText(date);
            textView2_leave_name.setText(leave_name);
            textView3_leave_teacher_name.setText(prof_name);
        }
    }
}
