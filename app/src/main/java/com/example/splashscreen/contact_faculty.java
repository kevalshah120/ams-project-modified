package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class contact_faculty extends AppCompatActivity {
    RecyclerView recyclerView;
    List<contact_faculty_model> contact_faculty_model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_faculty);
        recyclerView = findViewById(R.id.contact_faculty_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contact_faculty_model.add(new contact_faculty_model("Prof Sanjay Bhalgama","+91 8460209843"));
        contact_faculty_model.add(new contact_faculty_model("Prof Bhailal Limbasiya","+91 8000068515"));
        contact_faculty_model.add(new contact_faculty_model("Prof Uresh Parmar","+91 9925206242"));
        recyclerView.setHasFixedSize(true);
        contact_faculty_adapter contact_faculty_adapter = new contact_faculty_adapter(this, contact_faculty_model);
        recyclerView.setAdapter(contact_faculty_adapter);
    }
}