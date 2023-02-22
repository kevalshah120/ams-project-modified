package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class teacher_mark_Attendance extends AppCompatActivity {
    List<teacher_mark_attend_model> mark_attend_models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mark_attendance);
        RecyclerView recyclerView = findViewById(R.id.teacher_mark_attend_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mark_attend_models.add(new teacher_mark_attend_model("206090307004","Keval Shah"));
        mark_attend_models.add(new teacher_mark_attend_model("206090307034","Henarth Agravat"));
        mark_attend_models.add(new teacher_mark_attend_model("206090307064","Yash Matariya"));
        recyclerView.setHasFixedSize(true);
        teacher_mark_attend_adapter mark_attend_adapter = new teacher_mark_attend_adapter(this, mark_attend_models);
        recyclerView.setAdapter(mark_attend_adapter);
    }
}