package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class teacher_class_attendance extends AppCompatActivity {
    AutoCompleteTextView month_year_auto;
    ArrayAdapter<String> month_year_adap;
    List<String> month_year_lists;
    TextInputEditText sub_et, div_et;
    Button generate_button;
    String selected_month_year, selected_subject, selected_division,selected_month,selected_year;
    String[] subject_list = new String[]{"Java","PPUD","NMA"};
    boolean[] checked_sub_list;
    ArrayList<Integer> sub_selected_pos = new ArrayList<>();
    String[] div_list = new String[]{"5A","5B"};
    boolean[] checked_divs;
    ArrayList<Integer> div_selected_pos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_attendance);
        sub_et = findViewById(R.id.sub_et);
        div_et = findViewById(R.id.div_et);
        month_year_auto = findViewById(R.id.month_auto_comp);
        generate_button = findViewById(R.id.generate_button);

        // Get the current year and month
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        // Generate a list of month and year strings
        month_year_lists = new ArrayList<>();
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.US);
        for (int year = 2022; year <= currentYear; year++) {
            int startMonth = (year == 2022) ? Calendar.JANUARY : 0;
            int endMonth = (year == currentYear) ? currentMonth : Calendar.DECEMBER;
            for (int month = startMonth; month <= endMonth; month++) {
                String monthName = dfs.getMonths()[month];
                String yearName = String.valueOf(year);
                String monthYear = monthName + " " + yearName;
                month_year_lists.add(monthYear);
            }
        }

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

        // Set up the adapter and set it to the AutoCompleteTextView
        month_year_adap = new ArrayAdapter<String>(this, R.layout.leave_staffname_dropdown, month_year_lists);
        month_year_auto.setAdapter(month_year_adap);

        month_year_auto.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(teacher_class_attendance.this,item,Toast.LENGTH_SHORT).show();
            month_year_auto.clearFocus();
            month_year_auto.setInputType(InputType.TYPE_NULL);
        });

        generate_button.setOnClickListener(v -> {
            selected_subject = sub_et.getText().toString();
            selected_division = div_et.getText().toString();
            selected_month_year = month_year_auto.getText().toString();
            String[] monthYearArray = selected_month_year.split(" ");
            selected_month = getMonthNumberFromName(monthYearArray[0]);
            selected_year = monthYearArray[1];
            Intent i = new Intent(teacher_class_attendance.this, attendance_display_wv.class);
            i.putExtra("subject", selected_subject);
            i.putExtra("division", selected_division);
            i.putExtra("month", selected_month);
            i.putExtra("year", selected_year);
            startActivity(i);
        });
    }
    public String getMonthNumberFromName(String monthName) {
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(monthName)) {
                return String.valueOf(i + 1);
            }
        }
        return "";
    }
}
