package com.example.splashscreen;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class leave_data extends AppCompatActivity {

    EditText leave_name,from_Date,to_Date;
    String[] staff_name = {"Sanjay Bhalgama","Bhailal Limbasya","Shakti Parmar"};
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapteritems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_data);

        leave_name = findViewById(R.id.leave_name);
        from_Date = findViewById(R.id.from_date);
        to_Date = findViewById(R.id.to_date);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapteritems = new ArrayAdapter<String>(this,R.layout.leave_staffname_dropdown,staff_name);
        autoCompleteTxt.setAdapter(adapteritems);
        autoCompleteTxt.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(leave_data.this,item,Toast.LENGTH_SHORT).show();
        });

        from_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    datepicker_fun(from_Date);
                }
            }
        });
        to_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    datepicker_fun(to_Date);
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
        DatePickerDialog d = new DatePickerDialog(leave_data.this, dpd,year,month,day);
        d.show();
    }

}