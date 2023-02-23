package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class teacher_mark_Attendance extends AppCompatActivity {
    List<teacher_mark_attend_model> mark_attend_models = new ArrayList<>();
    TextView timer_tv;
    ImageView timer_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mark_attendance);
        int expiry_time = Integer.parseInt(getIntent().getStringExtra("expiry_time"));
        boolean smart_attend_switch = getIntent().getBooleanExtra("smart_attend_switch", false);
        String booleanString = Boolean.toString(smart_attend_switch);
        timer_tv = findViewById(R.id.timer_tv);
        timer_icon = findViewById(R.id.timer_icon);
        RecyclerView recyclerView = findViewById(R.id.teacher_mark_attend_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(!smart_attend_switch){
            timer_icon.setVisibility(View.GONE);
            timer_tv.setVisibility(View.GONE);
        }
        else{
            timer_icon.setVisibility(View.VISIBLE);
            timer_tv.setVisibility(View.VISIBLE);
            new CountDownTimer(expiry_time * 60 * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(minutes);
                    timer_tv.setText(String.format("%d:%02d", minutes, seconds));
                }
                public void onFinish() {

                }
            }.start();
        }
        mark_attend_models.add(new teacher_mark_attend_model("206090307004","Keval Shah"));
        mark_attend_models.add(new teacher_mark_attend_model("206090307034","Henarth Agravat"));
        mark_attend_models.add(new teacher_mark_attend_model("206090307064","Yash Matariya"));
        recyclerView.setHasFixedSize(true);
        teacher_mark_attend_adapter mark_attend_adapter = new teacher_mark_attend_adapter(this, mark_attend_models);
        recyclerView.setAdapter(mark_attend_adapter);
    }
}