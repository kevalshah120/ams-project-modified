package com.example.splashscreen;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class student_homescreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton float_refresh_btn,float_add_btn;
    Toolbar toolbar;
    ImageView wave_emoji;
    TextView toolbar_textview;
    ActionBarDrawerToggle toogle;

    CircleImageView profile_image;

    sessionForS SFS;
    FusedLocationProviderClient mFusedLocationClient;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;
    private static final int PERMISSION_ID = 44;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homescreen);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        profile_image = findViewById(R.id.profile_image);
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
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
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
                builder.setMessage("Do you want to Logout?")
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
        float_refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float_refresh_btn.setVisibility(View.VISIBLE);
                float_add_btn.setVisibility(View.GONE);
                toolbar_textview.setText("Hi Keval");
                wave_emoji.setVisibility(View.VISIBLE);
                replaceFragment(new stu_home_fragement());
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(student_homescreen.this)
                        .crop(100,100)
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
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
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                getLastLocation();
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }
    //    private void disableEditText(EditText T)
//    {
//        T.setFocusable(false);
//        T.setCursorVisible(false);
//    }
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @SuppressLint({"SetTextI18n", "MissingPermission"})
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {
                                List<Address> add = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                Address obj = add.get(0);
                                String Locality = obj.getSubLocality();
                                sessionForS SFS = new sessionForS(getApplication());
                                SFS.setLocation(Locality);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on " + " your location... ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermission();
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest;
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && data != null)
        {
            Uri image = data.getData();
            profile_image.setImageURI(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}