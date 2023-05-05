package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class stu_sub_attendance_view extends AppCompatActivity {
    private WebView webView;
    private String subject_code;
    private String enr_no;
    TextView present,absent;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_sub_attendance_view);
        present = findViewById(R.id.present_tv);
        absent = findViewById(R.id.absent_tv);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://stocky-baud.000webhostapp.com/student_attendance_P_A.php";
        Intent i = getIntent();
        subject_code = i.getStringExtra("subject_code");
        enr_no = i.getStringExtra("enr_no");
        String postData = "subject_code=" + subject_code + "&enrollment_number=" + enr_no + "&present_absent=" + "1";
        webView.postUrl(url, postData.getBytes());
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hexColorCode = "#FF0000";
                int color = Color.parseColor(hexColorCode);
                present.setBackgroundResource(R.drawable.switch_track);
                present.setTextColor(Color.WHITE);
                absent.setBackground(null);
                absent.setTextColor(color);
                String postData = "subject_code=" + subject_code + "&enrollment_number=" + enr_no + "&present_absent=" + "1";
                webView.postUrl(url, postData.getBytes());
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hexColorCode = "#25AF36";
                int color = Color.parseColor(hexColorCode);
                present.setBackground(null);
                present.setTextColor(color);
                absent.setBackgroundResource(R.drawable.switch_track);
                absent.setTextColor(Color.WHITE);
                String postData = "subject_code=" + subject_code + "&enrollment_number=" + enr_no + "&present_absent=" + "0";
                webView.postUrl(url, postData.getBytes());
            }
        });
    }
}