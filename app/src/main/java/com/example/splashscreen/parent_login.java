package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class parent_login extends AppCompatActivity {
    Button back,login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);
        final String class_name = getLocalClassName();
        final EditText mobile_no = findViewById(R.id.mob_no);
        final EditText enr_no = findViewById(R.id.enr_no);
        back = findViewById(R.id.back_button);
        login_btn = findViewById(R.id.par_login_button);
        back.setOnClickListener(view -> {
            Intent i = new Intent(parent_login.this,login_screen.class);
            startActivity(i);
            finish();
        });
        login_btn.setOnClickListener(view -> {
            final String getmobile_num = mobile_no.getText().toString();
            final String getenr_num = enr_no.getText().toString();
            if(!getmobile_num.trim().isEmpty()) {
                if (getmobile_num.trim().length() != 10) {
                    Toast.makeText(parent_login.this, "Invalid mobile number", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(parent_login.this, otp_verification.class);
                    i.putExtra("mobile", getmobile_num);
                    i.putExtra("class_name", class_name);
                    startActivity(i);
                    finish();
                }
            }
            else{
                Toast.makeText(parent_login.this,"Enter mobile number",Toast.LENGTH_LONG).show();
            }
        });
    }
}