package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class student_leave_details_display extends AppCompatActivity {
    TextInputEditText prof_name,to_date,from_date,desc,leave_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_leave_details_display);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        prof_name = findViewById(R.id.staff_name);
        to_date = findViewById(R.id.to_date);
        from_date = findViewById(R.id.from_date);
        desc= findViewById(R.id.Leave_description);
        leave_name = findViewById(R.id.leave_name);
        Intent i = getIntent();
        desc.setText(i.getStringExtra("desc"));
        to_date.setText(i.getStringExtra("toDate"));
        from_date.setText(i.getStringExtra("fromDate"));
        prof_name.setText(i.getStringExtra("prof_name"));
        leave_name.setText(i.getStringExtra("leave_name"));
    }
}