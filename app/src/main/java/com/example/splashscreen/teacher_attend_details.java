package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class teacher_attend_details extends AppCompatActivity {
    TextView sub_name_tv;
    TextView div_tv;
    String[] sub_name = {"Ad java","DBMS","DBMS Lab"};
    String[] div_list = new String[]{"5A","5B"};
    boolean[] checked_divs;
    SeekBar expiry_time_slider;
    TextView expiry_time_tv;
    LinearLayout seekbar_layout;
    RelativeLayout loc_check_layout;
    Switch smart_attend_swi,loc_check_swi;
    boolean smart_attend_bol,loc_check_bol;
    ArrayList<Integer> div_selected_pos = new ArrayList<>();
    String subject_selected;
    Button take_attend_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attend_details);
        sub_name_tv = findViewById(R.id.sub_name_tv);
        div_tv = findViewById(R.id.div_tv);
        expiry_time_slider = findViewById(R.id.expiry_time_seekbar);
        expiry_time_tv = findViewById(R.id.expiry_time_tv);
        smart_attend_swi = findViewById(R.id.smart_attend_switch);
        loc_check_swi = findViewById(R.id.location_check_switch);
        seekbar_layout = findViewById(R.id.seekbar_layout);
        loc_check_layout = findViewById(R.id.loc_check_layout);
        seekbar_layout.setVisibility(View.GONE);
        loc_check_layout.setVisibility(View.GONE);
        checked_divs = new boolean[div_list.length];
        take_attend_button = findViewById(R.id.take_attend_btn);
        sub_name_tv.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(teacher_attend_details.this);
            builder.setTitle("Subjects");
            builder.setSingleChoiceItems(sub_name, 0, (dialogInterface, i) -> subject_selected = sub_name[i]);
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                sub_name_tv.setText(subject_selected);
                dialogInterface.dismiss();
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        });
        div_tv.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(teacher_attend_details.this);
            builder.setTitle("Division");
            builder.setMultiChoiceItems(div_list, checked_divs, (dialogInterface, i, b) -> {
                if(b){
                    if(!div_selected_pos.contains(i)){
                        div_selected_pos.add(i);
                    }
                }
                else{
                    div_selected_pos.remove((Integer) i);
                }
            });
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                StringBuilder div_selected_val = new StringBuilder();
                for(int count_val = 0; count_val < div_selected_pos.size() ; count_val++)
                {
                    div_selected_val.append(div_list[div_selected_pos.get(count_val)]);
                }
                div_tv.setText(div_selected_val.toString());
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        });
        expiry_time_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                expiry_time_tv.setText(String.valueOf(i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        smart_attend_swi.setOnClickListener(view -> {
            if(smart_attend_swi.isChecked())
            {
                smart_attend_bol = true;
                seekbar_layout.setVisibility(View.VISIBLE);
                loc_check_layout.setVisibility(View.VISIBLE);
            }
            else
            {
                smart_attend_bol = false;
                seekbar_layout.setVisibility(View.GONE);
                loc_check_layout.setVisibility(View.GONE);
            }
        });
        loc_check_swi.setOnClickListener(view -> {
            loc_check_bol = loc_check_swi.isChecked();
        });

        take_attend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(teacher_attend_details.this,teacher_mark_Attendance.class);
                i.putExtra("expiry_time", String.valueOf(expiry_time_tv.getText()));
                i.putExtra("smart_attend_switch",smart_attend_bol);
                startActivity(i);
                finish();
            }
        });
    }
}