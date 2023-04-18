package com.example.splashscreen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class teacher_class_attendance extends AppCompatActivity {
    TextInputEditText from_Date,to_Date;
    TextInputEditText sub_et, div_et;
    Button generate_button;
    String selected_subject, selected_division;
    String[] subject_list = new String[]{"Java","PPUD","NMA"};
    boolean[] checked_sub_list;
    ArrayList<Integer> sub_selected_pos = new ArrayList<>();
    String[] div_list = new String[]{"5A","5B"};
    boolean[] checked_divs;
    ArrayList<Integer> div_selected_pos = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_attendance);
        sub_et = findViewById(R.id.sub_et);
        div_et = findViewById(R.id.div_et);
        from_Date = findViewById(R.id.from_date);
        to_Date = findViewById(R.id.to_date);
        generate_button = findViewById(R.id.generate_button);
        sub_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bol) {
                if(bol)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_class_attendance.this);
                    builder.setTitle("Subject");
                    builder.setMultiChoiceItems(subject_list, checked_sub_list, (dialogInterface, i, b) -> {
                        if(b){
                            if(!sub_selected_pos.contains(i)){
                                sub_selected_pos.add(i);
                                sub_et.clearFocus();
                                sub_et.setInputType(InputType.TYPE_NULL);
                            }
                        }
                        else{
                            sub_selected_pos.remove((Integer) i);
                        }
                    });
                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
                        StringBuilder sub_selected_val = new StringBuilder();
                        for(int count_val = 0; count_val < sub_selected_pos.size() ; count_val++)
                        {
                            sub_selected_val.append(subject_list[sub_selected_pos.get(count_val)]);
                        }
                        sub_et.setText(sub_selected_val.toString() + " ");
                    });
                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.show();
                }
            }
        });
        div_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bol) {
                if(bol)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_class_attendance.this);
                    builder.setTitle("Division");
                    builder.setMultiChoiceItems(div_list, checked_divs, (dialogInterface, i, b) -> {
                        if(b){
                            if(!div_selected_pos.contains(i)){
                                div_selected_pos.add(i);
                                div_et.clearFocus();
                                div_et.setInputType(InputType.TYPE_NULL);
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
                        div_et.setText(div_selected_val.toString());
                    });
                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.show();
                }
            }
        });
        from_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    datepicker_fun(from_Date);
                    from_Date.clearFocus();
                    from_Date.setInputType(InputType.TYPE_NULL);
                }
            }
        });
        to_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    datepicker_fun(to_Date);
                    to_Date.clearFocus();
                    to_Date.setInputType(InputType.TYPE_NULL);
                }
            }
        });
        generate_button.setOnClickListener(v -> {
            selected_subject = sub_et.getText().toString();
            selected_division = div_et.getText().toString();
            final String class_name = getLocalClassName();
            Intent i = new Intent(teacher_class_attendance.this, attendance_display_wv.class);
            i.putExtra("subject", selected_subject);
            i.putExtra("division", selected_division);
            i.putExtra("class_name",class_name);
            startActivity(i);
        });
    }
    private void datepicker_fun(EditText date_text)
    {
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int month = i1+1;
                date_text.setText(i2+"/"+month+"/"+i);
            }
        };
        DatePickerDialog d = new DatePickerDialog(teacher_class_attendance.this, dpd,year,month,day);
        d.show();
    }
}
