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
 * Use the {@link stu_attendance_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stu_attendance_fragement extends Fragment {
    List<subjectlist_attend_model> subject_data;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public stu_attendance_fragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment stu_attendance_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static stu_attendance_fragement newInstance(String param1, String param2) {
        stu_attendance_fragement fragment = new stu_attendance_fragement();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stu_attendance_fragement, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView = view.findViewById(R.id.subject_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        subjectlist_attend_adapter subjectlist_attend_adapter = new subjectlist_attend_adapter(getContext(),subject_data);
        recyclerView.setAdapter(subjectlist_attend_adapter);
        subjectlist_attend_adapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        subject_data = new ArrayList<>();
        subject_data.add(new subjectlist_attend_model("PPUD (3360702)","Prof Pratik Parmar","86%"));
        subject_data.add(new subjectlist_attend_model("Ad.Java (3360701)","Prof K. G. Patel","96%"));
        subject_data.add(new subjectlist_attend_model("NMA (3360703)","Prof Bhailal Limbasiya","75%"));
        subject_data.add(new subjectlist_attend_model("OS (3360704)","Prof Shakti Sinh Parmar","52%"));
        subject_data.add(new subjectlist_attend_model("DBMS (3360705)","Prof Uresh Parmar","88%"));

    }
}