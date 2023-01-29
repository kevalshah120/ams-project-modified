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
 * Use the {@link teacher_leave_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teacher_leave_frag extends Fragment {
    List<teacher_leave_model> tea_leave_data;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teacher_leave_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teacher_leave_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static teacher_leave_frag newInstance(String param1, String param2) {
        teacher_leave_frag fragment = new teacher_leave_frag();
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
        return inflater.inflate(R.layout.fragment_teacher_leave_frag, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView = view.findViewById(R.id.teacher_leave_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        teacher_leave_adapter teacher_leave_adapter = new teacher_leave_adapter(getContext(),tea_leave_data);
        recyclerView.setAdapter(teacher_leave_adapter);
        teacher_leave_adapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        tea_leave_data = new ArrayList<>();
        tea_leave_data.add(new teacher_leave_model("Leave application name","26 Jan 2023 - 28 JAN 2023","Keval Shah","3SEM"));
        tea_leave_data.add(new teacher_leave_model("Kidney Attack","26 Jan 2023 - 28 JAN 2023","Yash Matariya","3SEM"));
    }
}