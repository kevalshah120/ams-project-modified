package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class teacher_today_attendance_webview extends AppCompatActivity {
    private WebView webView;
    private String subject_code;
    private String division;
    private String current_date;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_today_attendance_webview);
        webView = findViewById(R.id.today_attendance_display_wv);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://stocky-baud.000webhostapp.com/today_attendance.php";
        Intent i = getIntent();
        subject_code = i.getStringExtra("subject_code");
        division = i.getStringExtra("div");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        current_date = dateFormat.format(calendar.getTime());
        String postData = "subject_code=" + subject_code + "&division=" + division + "&today_date=" + current_date;
        webView.postUrl(url, postData.getBytes());
    }
}