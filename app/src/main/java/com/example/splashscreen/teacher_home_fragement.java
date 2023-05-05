package com.example.splashscreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teacher_home_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teacher_home_fragement extends Fragment {
    List<today_attendance_taken_model> today_attendance_taken_data;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teacher_home_fragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teacher_home_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static teacher_home_fragement newInstance(String param1, String param2) {
        teacher_home_fragement fragment = new teacher_home_fragement();
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
        View view = inflater.inflate(R.layout.fragment_teacher_home_fragement, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView = view.findViewById(R.id.today_attendance_taken_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        today_attendance_taken_adapter today_attendance_taken_adapter = new today_attendance_taken_adapter(getContext(),today_attendance_taken_data);
        recyclerView.setAdapter(today_attendance_taken_adapter);
        today_attendance_taken_adapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        today_attendance_taken_data = new ArrayList<>();
        today_attendance_taken_data.add(new today_attendance_taken_model("PPUD (3360702)","90 / 100","6A"));
        today_attendance_taken_data.add(new today_attendance_taken_model("Ad.Java (3360701)","86 / 100","6A"));
        today_attendance_taken_data.add(new today_attendance_taken_model("NMA (3360703)","94 / 100","6B"));
        today_attendance_taken_data.add(new today_attendance_taken_model("OS (3360704)","82 / 100","6B"));
    }
}