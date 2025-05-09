package com.example.splashscreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class leave_details_display extends AppCompatActivity {
    TextInputEditText leave_name, from_date, to_date, leave_desc,stu_name;
    String leave_nameS, descS, to_dateS, from_dateS, DATE,student_name;
    StringBuilder toS,fromS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_details_display);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        leave_name = findViewById(R.id.leave_name);
        from_date = findViewById(R.id.from_date);
        to_date = findViewById(R.id.to_date);
        leave_desc = findViewById(R.id.Leave_description);
        stu_name = findViewById(R.id.stu_name);
        Intent intent = getIntent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        leave_nameS = intent.getStringExtra("leave_name");
        DATE = intent.getStringExtra("Date");
        descS = intent.getStringExtra("desc");
        student_name = intent.getStringExtra("stu_name");
        fromS = new StringBuilder();
        toS = new StringBuilder();
        int i = 0,j=0;
        while(i<DATE.length())
        {
            if(DATE.charAt(i) == '-')
            {
                j++;
                i++;
            }
            else
            {
                if(j==0)
                {
                    fromS.append(DATE.charAt(i));
                }
                else
                {
                    toS.append(DATE.charAt(i));
                }
            }
            i++;
        }
        from_dateS= fromS.toString();
        to_dateS = toS.toString();
        leave_name.setText(leave_nameS);
        to_date.setText(to_dateS);
        from_date.setText(from_dateS);
        leave_desc.setText(descS);
        stu_name.setText(student_name);
    }
}