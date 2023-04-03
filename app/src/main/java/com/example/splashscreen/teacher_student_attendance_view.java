package com.example.splashscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class teacher_student_attendance_view extends AppCompatActivity {
    TextInputEditText from_Date,to_Date,subject;
    AutoCompleteTextView stu_auto_comp,sem_auto_comp;
    ArrayAdapter<String> stu_adap_items;
    ArrayAdapter<String> sem_adap_items;
    String[] subject_list = new String[]{"Java","PPUD","NMA"};
    boolean[] checked_sub_list;
    String[] student_name = {"keval shah","henarth agravat","yash matariya","Harshal prajapati","Milan","Harshid","Prem","Vatsal","Manan","sumeet","Jimit","samarth","dev mehta","stavan"};
    String[] sem_val = {"5","6"};
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    ArrayList<Integer> sub_selected_pos = new ArrayList<>();
    int month = calendar.get(Calendar.MONTH);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_attendance_view);
        from_Date = findViewById(R.id.from_date);
        to_Date = findViewById(R.id.to_date);
        subject = findViewById(R.id.sub_et);
        stu_auto_comp = findViewById(R.id.stu_auto_comp);
        sem_auto_comp = findViewById(R.id.sem_auto_comp);
        stu_adap_items = new ArrayAdapter<String>(this,R.layout.leave_staffname_dropdown,student_name);
        stu_auto_comp.setAdapter(stu_adap_items);
        stu_auto_comp.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(teacher_student_attendance_view.this,item,Toast.LENGTH_SHORT).show();
            stu_auto_comp.clearFocus();
            stu_auto_comp.setInputType(InputType.TYPE_NULL);
        });
        sem_adap_items = new ArrayAdapter<String>(this,R.layout.leave_staffname_dropdown,sem_val);
        sem_auto_comp.setAdapter(sem_adap_items);
        sem_auto_comp.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(teacher_student_attendance_view.this,item,Toast.LENGTH_SHORT).show();
            sem_auto_comp.clearFocus();
            sem_auto_comp.setInputType(InputType.TYPE_NULL);
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
        subject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bol) {
                if(bol)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(teacher_student_attendance_view.this);
                    builder.setTitle("Division");
                    builder.setMultiChoiceItems(subject_list, checked_sub_list, (dialogInterface, i, b) -> {
                        if(b){
                            if(!sub_selected_pos.contains(i)){
                                sub_selected_pos.add(i);
                                subject.clearFocus();
                                subject.setInputType(InputType.TYPE_NULL);
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
                        subject.setText(sub_selected_val.toString() + " ");
                    });
                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.show();
                }
            }
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
        DatePickerDialog d = new DatePickerDialog(teacher_student_attendance_view.this, dpd,year,month,day);
        d.show();
    }
}