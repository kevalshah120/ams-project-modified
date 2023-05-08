package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class teacher_leave_history extends AppCompatActivity {
    RecyclerView recyclerView;
    List<leave_history_model> leave_history_model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_leave_history);
        recyclerView = findViewById(R.id.leave_history_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        leave_history_model.add(new leave_history_model(R.drawable.approved_tag,"barr javanu che","21 Apr 2023 - 23 Apr 2023","Keval Shah","6 SEM","01","keval shah is here"));
        leave_history_model.add(new leave_history_model(R.drawable.pending_tag,"Kaam che","23 Apr 2023 - 25 Apr 2023","Henarth Agravat","6 SEM","01","keval shah is here"));
        leave_history_model.add(new leave_history_model(R.drawable.rejected_tag,"Out of india che","25 Apr 2023 - 28 Apr 2023","Yash Matariya","6 SEM","01","keval shah is here"));
        recyclerView.setHasFixedSize(true);
        leave_history_adapter leave_history_adapter = new leave_history_adapter(this, leave_history_model);
        recyclerView.setAdapter(leave_history_adapter);
    }
}