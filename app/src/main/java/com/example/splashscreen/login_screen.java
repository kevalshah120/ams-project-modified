package com.example.splashscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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