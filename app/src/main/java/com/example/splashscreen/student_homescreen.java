package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
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

public class student_homescreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton float_refresh_btn,float_add_btn;
    Toolbar toolbar;
    ImageView wave_emoji;
    TextView toolbar_textview;
    ActionBarDrawerToggle toogle;
    sessionForS SFS;
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
        SFS = new sessionForS(getApplication());
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
            return true;
        });
        toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id==R.id.contact_us){
                Toast.makeText(getApplicationContext(),"Contact Us",Toast.LENGTH_SHORT).show();
            }
            else if(id==R.id.about_us)
            {
                Toast.makeText(getApplicationContext(),"About Us",Toast.LENGTH_SHORT).show();
            }
            else if(id==R.id.logout)
            {
                //LOGOUT (START)
                AlertDialog.Builder builder = new AlertDialog.Builder(student_homescreen.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SFS.setEnrollment("");
                                SFS.setMobile("");
                                startActivity(new Intent(getApplicationContext(),login_screen.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                //LOGOUT (END)
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

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

}