package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class splash_screen extends AppCompatActivity {
    Animation topanim;
    TextView appname;
    ImageView logo;
    CharSequence cs;
    int index;
    long delay = 70;
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topanim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        Handler handler = new Handler();
        logo = findViewById(R.id.logo);
        appname=findViewById(R.id.appname);
//        YoYo.with(Techniques.StandUp).duration(2000).playOn(appname);
        logo.setAnimation(topanim);
        animatetext("Attendance Management System");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splash_screen.this,login_screen.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            appname.setText(cs.subSequence(0,index++));
            if(index <= cs.length())
            {
                h.postDelayed(run,delay);
            }
        }
    };

    public void animatetext(CharSequence chars)
    {
        cs = chars;
        index = 0;
        appname.setText("");
        h.removeCallbacks(run);
        h.postDelayed(run,delay);
    }
}