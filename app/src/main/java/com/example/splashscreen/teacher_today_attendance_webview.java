package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class teacher_today_attendance_webview extends AppCompatActivity {
    private WebView webView;
    private String subject_name;
    ProgressBar pgbar;
    private String division;
    private String current_date;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_today_attendance_webview);
        webView = findViewById(R.id.today_attendance_display_wv);
        pgbar = findViewById(R.id.pgbar);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://stocky-baud.000webhostapp.com/today_attendance.php";
        Intent i = getIntent();
        String scode = i.getStringExtra("subject_name");
        subject_name = scode.substring(0,scode.indexOf('(')).trim();
        division = i.getStringExtra("div").substring(0,2).trim();
        Log.d("KHIKHI",subject_name+" "+division);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        current_date = dateFormat.format(calendar.getTime());
        String postData = "subject_name=" + subject_name
                + "&division=" + division
                + "&today_date=" + current_date;
        webView.postUrl(url, postData.getBytes());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pgbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pgbar.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }
        });
    }
}