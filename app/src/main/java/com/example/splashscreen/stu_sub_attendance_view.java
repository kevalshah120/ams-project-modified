package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class stu_sub_attendance_view extends AppCompatActivity {
    private WebView webView;
    private String subject_code,subject_string,subject_name;
    ProgressBar pgbar;
    private String enr_no;
    TextView present,absent;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_sub_attendance_view);
        present = findViewById(R.id.present_tv);
        pgbar = findViewById(R.id.pgbar);
        absent = findViewById(R.id.absent_tv);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://stocky-baud.000webhostapp.com/student_attendance_P_A.php";
        Intent i = getIntent();
        subject_string = i.getStringExtra("subject_name");
        int size = subject_string.length();
        int n = 0;
        StringBuilder sname,scode;
        sname = new StringBuilder();
        scode = new StringBuilder();
        boolean b = true;
        while(n<size)
        {
            if(b)
            {
                sname.append(subject_string.charAt(n));
                if(subject_string.charAt(n+1) == '(')
                {
                    b = false;
                    n++;
                }
            }
            else
            {
                if(subject_string.charAt(n) != ')')
                {
                    scode.append(subject_string.charAt(n));
                }
            }
            n++;
        }
        Log.d("nameandcode",sname.toString().trim()+scode.toString().trim());
        subject_code = scode.toString().trim();
        subject_name = sname.toString().trim();
        enr_no = i.getStringExtra("enr_no");
        String postData = "subject_code=" + subject_code +"&subject_name="+subject_name+"&enrollment_number=" + enr_no + "&present_absent=" + "1";
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
                String postData = "subject_code=" + subject_code
                        +"&subject_name="+subject_name
                        +"&enrollment_number=" + enr_no
                        + "&present_absent=" + "1";
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
                String postData = "subject_code=" + subject_code +"&subject_name="+subject_name+"&enrollment_number=" + enr_no + "&present_absent=" + "0";
                webView.postUrl(url, postData.getBytes());
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                pgbar.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pgbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
        });
    }
}