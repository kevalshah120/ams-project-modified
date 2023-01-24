package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class student_homescreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton float_refresh_btn,float_add_btn;
    Toolbar toolbar;
    ImageView wave_emoji;
    TextView toolbar_textview;
    ActionBarDrawerToggle toogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homescreen);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        float_add_btn = findViewById(R.id.float_add_leave_btn);
        float_refresh_btn = findViewById(R.id.float_refresh_Btn);
        toolbar_textview = findViewById(R.id.toolbar_text);
        wave_emoji = findViewById(R.id.wave_emoji);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home_menu);
        float_add_btn.setVisibility(View.GONE);
        replaceFragment(new stu_home_fragement());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_menu:
                    float_refresh_btn.setVisibility(View.VISIBLE);
                    float_add_btn.setVisibility(View.GONE);
                    toolbar_textview.setText("Hi Keval");
                    wave_emoji.setVisibility(View.VISIBLE);
                    replaceFragment(new stu_home_fragement());
                    break;
                case R.id.attendance_menu:
                    float_add_btn.setVisibility(View.GONE);
                    float_refresh_btn.setVisibility(View.GONE);
                    toolbar_textview.setText("Attendance");
                    wave_emoji.setVisibility(View.GONE);
                    replaceFragment(new stu_attendance_fragement());
                    break;
                case R.id.leave_menu:
                    float_add_btn.setVisibility(View.VISIBLE);
                    float_refresh_btn.setVisibility(View.GONE);
                    toolbar_textview.setText("Leave");
                    wave_emoji.setVisibility(View.GONE);
                    replaceFragment(new stu_leave_fragement());
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        float_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(student_homescreen.this,leave_data.class);
                startActivity(i);
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body_container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.contact_us:
                Toast.makeText(this,"Contact Us",Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_us:
                Toast.makeText(this,"About Us",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}