package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class login_screen extends AppCompatActivity {
    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b1.setOnClickListener(view -> {
            Intent i = new Intent(login_screen.this,student_login.class);
            startActivity(i);
            finish();
        });
        b2.setOnClickListener(view -> {
            Intent i = new Intent(login_screen.this,teacher_login.class);
            startActivity(i);
            finish();
        });
        b3.setOnClickListener(view -> {
            Intent i = new Intent(login_screen.this,parent_login.class);
            startActivity(i);
            finish();
        });
    }
    public void onBackPressed() {
    }
}