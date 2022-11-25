package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class teacher_login extends AppCompatActivity {
    Button back,login;
    private boolean passwordshowing = true;
    ImageView password_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        getSupportActionBar().hide();
        final String class_name = getLocalClassName();
        final EditText login_id = findViewById(R.id.login_id);
        final EditText password_field = findViewById(R.id.password_field);
        back = findViewById(R.id.back_button);
        login = findViewById(R.id.login_button);
        back.setOnClickListener(view -> {
            Intent i = new Intent(teacher_login.this,login_screen.class);
            startActivity(i);
            finish();
        });
        login.setOnClickListener(view -> {
            Intent i = new Intent(teacher_login.this,otp_verification.class);
            i.putExtra("class_name", class_name);
            startActivity(i);
            finish();
        });
        password_icon = findViewById(R.id.password_hide);
        password_icon.setOnClickListener(view -> {
            if(passwordshowing){
                passwordshowing = false;
                password_field.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                password_icon.setImageResource(R.drawable.password_show);
            }
            else{
                passwordshowing = true;
                password_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password_icon.setImageResource(R.drawable.password_hide);
            }
            password_field.setSelection(password_field.length());
        });
    }
}