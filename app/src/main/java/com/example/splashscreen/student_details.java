package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class student_details extends AppCompatActivity {
    Button edit_btn,save_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        edit_btn = findViewById(R.id.edit_button);
        save_btn = findViewById(R.id.save_button);
        save_btn.setVisibility(View.GONE);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_btn.setVisibility(View.VISIBLE);
            }
        });
    }
}