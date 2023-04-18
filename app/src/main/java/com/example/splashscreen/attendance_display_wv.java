package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
public class attendance_display_wv extends AppCompatActivity {
    private WebView webView;
    private String class_name;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_display_wv);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        class_name = getIntent().getStringExtra("class_name");
        if(class_name.equals("teacher_class_attendance"))
        {
            String selected_subject,selected_division,from_date,to_date,is_lab;
            String url = "https://stocky-baud.000webhostapp.com/testing.php";
            from_date = "01/01/2023";
            to_date = "01/30/2023";
            selected_division = "6A";
            selected_subject="java";
            is_lab = "0";
            String postData = "selected_subject=" + selected_subject + "&selected_division=" + selected_division + "&from_date=" + from_date + "&to_date=" + to_date+ "&is_lab=" + is_lab;
            webView.postUrl(url, postData.getBytes());
        } else if (class_name.equals("teacher_student_attendance_view")) {
            String sel_enr_no,from_date,to_date;
            String url = "https://stocky-baud.000webhostapp.com/particular_student_attendance_view.php";
            from_date = "01/01/2023";
            to_date = "01/30/2023";
            sel_enr_no = "206090307011";
            String postData = "enrolmment_number=" + sel_enr_no + "&from_date=" + from_date + "&to_date=" + to_date;
            webView.postUrl(url, postData.getBytes());
        }
    }
}

