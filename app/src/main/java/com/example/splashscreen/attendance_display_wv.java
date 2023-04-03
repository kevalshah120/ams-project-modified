package com.example.splashscreen;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
public class attendance_display_wv extends AppCompatActivity {
    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_display_wv);
        webView = findViewById(R.id.web_view);
        String selected_month_year = getIntent().getStringExtra("selected_month_year");
        String selected_subject = getIntent().getStringExtra("selected_subject");
        String selected_division = getIntent().getStringExtra("selected_division");
        String url = "https://stocky-baud.000webhostapp.com/testing.php?month_year=" + selected_month_year + "&subject=" + selected_subject + "&division=" + selected_division;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
