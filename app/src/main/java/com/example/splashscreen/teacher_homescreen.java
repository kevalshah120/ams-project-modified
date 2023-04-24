package com.example.splashscreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class teacher_homescreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton float_new_attend_btn;
    Toolbar toolbar;
    TextView toolbar_textview;
    ActionBarDrawerToggle toogle;
    sessionForT SFT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_homescreen);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        float_new_attend_btn = findViewById(R.id.float_take_attendace_btn);
        toolbar_textview = findViewById(R.id.toolbar_text);
        bottomNavigationView = findViewById(R.id.teacher_bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.tea_home_menu);
        replaceFragment(new teacher_home_fragement());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.tea_home_menu:
                    toolbar_textview.setText("Home");
                    float_new_attend_btn.setVisibility(View.VISIBLE);
                    replaceFragment(new teacher_home_fragement());
                    break;
                case R.id.tea_attendance_menu:
                    toolbar_textview.setText("Attendance");
                    float_new_attend_btn.setVisibility(View.GONE);
                    replaceFragment(new teacher_attendance_fragement());
                    break;
                case R.id.tea_leave_menu:
                    toolbar_textview.setText("Leave");
                    float_new_attend_btn.setVisibility(View.GONE);
                    replaceFragment(new teacher_leave_frag());
                    break;
                case R.id.tea_student_menu:
                    toolbar_textview.setText("Student");
                    float_new_attend_btn.setVisibility(View.GONE);
                    replaceFragment(new teacher_student_frag());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(teacher_homescreen.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SFT = new sessionForT(getApplication());
                                SFT.setPass("");
                                SFT.setLogin("");
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
        float_new_attend_btn.setOnClickListener(view -> {
            Intent i = new Intent(teacher_homescreen.this,teacher_attend_details.class);
            startActivity(i);
        });
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
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body_container,fragment);
        fragmentTransaction.commit();
    }

}