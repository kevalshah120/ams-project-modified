package com.example.splashscreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class teacher_student_frag extends Fragment{
    List<teacher_mark_attend_model> mark_attend_models;
    private RecyclerView recyclerView;
    List<teacher_mark_attend_model> filteredList;
    private SearchView student_searchView;
    private student_list_adapter student_list_adapter;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teacher_student_frag() {
        // Required empty public constructor
    }

    public static teacher_student_frag newInstance(String param1, String param2) {
        teacher_student_frag fragment = new teacher_student_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_student_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.student_list_rv);
        student_searchView = view.findViewById(R.id.searchview);
        mark_attend_models = new ArrayList<>();
        dataInitialize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        student_list_adapter = new student_list_adapter(getContext(), mark_attend_models);
        recyclerView.setAdapter(student_list_adapter);
        student_list_adapter.notifyDataSetChanged();
        student_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filteredList = new ArrayList<>();
                if (newText.length() > 0) {
                    for (int i = 0;i < mark_attend_models.size() ; i++) {
                        if (mark_attend_models.get(i).getStu_name().toUpperCase().contains(newText.toUpperCase()) || mark_attend_models.get(i).getEnr_no().contains(newText.toUpperCase())) {
                            filteredList.add(mark_attend_models.get(i));
                        }
                    }
                } else {
                    filteredList.addAll(mark_attend_models);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                student_list_adapter = new student_list_adapter(getContext(), filteredList);
                recyclerView.setAdapter(student_list_adapter);
                return true;
            }
        });
        student_searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_searchView.setIconified(false);
            }
        });
    }

    private void dataInitialize() {
        mark_attend_models.add(new teacher_mark_attend_model("206090307004", "Keval Shah"));
        mark_attend_models.add(new teacher_mark_attend_model("206090307034", "Henarth Agravat"));
        mark_attend_models.add(new teacher_mark_attend_model("206090307014", "Harsh Shah"));
        mark_attend_models.add(new teacher_mark_attend_model("206090307064", "Yash Matariya"));
    }
}