package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
public class attendance_display_wv extends AppCompatActivity {
    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_display_wv);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        String selected_month = getIntent().getStringExtra("month");
//        String selected_month = "01";
        String selected_year = getIntent().getStringExtra("year");
//        String selected_year = "2023";
        String selected_subject = getIntent().getStringExtra("subject");
        String selected_division = getIntent().getStringExtra("division");
        String url = "https://stocky-baud.000webhostapp.com/testing.php";
        webView.loadUrl(url);
//        String postData = "selected_month=" + selected_month + "&selected_subject=" + selected_subject + "&selected_year=" + selected_year + "&selected_division=" + selected_division;
//        webView.postUrl(url, postData.getBytes());
    }
}

