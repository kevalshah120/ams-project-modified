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
public class attendance_display_wv extends AppCompatActivity {
    private WebView webView;
    private String class_name;
    ProgressBar pgbar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_display_wv);
        webView = findViewById(R.id.web_view);
        pgbar = findViewById(R.id.pgbar);
        webView.getSettings().setJavaScriptEnabled(true);
        class_name = getIntent().getStringExtra("class_name");
        if(class_name.equals("teacher_class_attendance"))
        {
            String url = "https://stocky-baud.000webhostapp.com/testing.php";
            Intent i = getIntent();
            String subject = i.getStringExtra("subject");
            String division = i.getStringExtra("division");
            String to_date = i.getStringExtra("to_date");
            String from_date = i.getStringExtra("from_date");
            String postData = "selected_subject=" + subject + "&selected_division=" + division + "&from_date=" + from_date + "&to_date=" + to_date;
            webView.postUrl(url, postData.getBytes());
        } else if (class_name.equals("teacher_student_attendance_view")) {
            Intent i = getIntent();
            String semester = i.getStringExtra("Semester");
            String enrollment = i.getStringExtra("Enrollment");
            String subject = i.getStringExtra("Subject");
            String to_date = i.getStringExtra("to_date");
            String from_date = i.getStringExtra("from_date");
            String url = "https://stocky-baud.000webhostapp.com/particular_student_attendance_view.php";
            String postData = "enrolmment_number=" + enrollment + "&from_date=" + from_date + "&to_date=" + to_date+ "&subject=" + subject;
            webView.postUrl(url, postData.getBytes());
        }
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

