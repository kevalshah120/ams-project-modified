package com.example.splashscreen;
//https://www.youtube.com/watch?v=UBgXVGgTaHk&ab_channel=Foxandroid reference taken for binding recyclerview in fragements
//https://youtu.be/4cFL7CMd5QY recyclerview video
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link stu_leave_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stu_leave_fragement extends Fragment {
    private TextView b1,b2,b3,b4;
    List<leave_model_class>leave_data;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public stu_leave_fragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment stu_leave_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static stu_leave_fragement newInstance(String param1, String param2) {
        stu_leave_fragement fragment = new stu_leave_fragement();
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

        View view =  inflater.inflate(R.layout.fragment_stu_leave_fragement, container, false);
        TextView b1 = view.findViewById(R.id.all_button);
        TextView b2 = view.findViewById(R.id.pending_button);
        TextView b3 = view.findViewById(R.id.approved_button);
        TextView b4 = view.findViewById(R.id.rejected_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setBackgroundResource(R.drawable.corner_left_focused);
                b2.setBackgroundResource(R.drawable.pending_btn_bg);
                b3.setBackgroundResource(R.drawable.approved_btn_bg);
                b4.setBackgroundResource(R.drawable.corner_right);
                b1.setTextColor(getResources().getColor(R.color.txt_color_focused));
                b2.setTextColor(getResources().getColor(R.color.txt_color));
                b3.setTextColor(getResources().getColor(R.color.txt_color));
                b4.setTextColor(getResources().getColor(R.color.txt_color));
                Toast.makeText(getActivity(),"All",Toast.LENGTH_SHORT).show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setBackgroundResource(R.drawable.corner_left);
                b2.setBackgroundResource(R.drawable.pending_btn_bg_focused);
                b3.setBackgroundResource(R.drawable.approved_btn_bg);
                b4.setBackgroundResource(R.drawable.corner_right);
                b1.setTextColor(getResources().getColor(R.color.txt_color));
                b2.setTextColor(getResources().getColor(R.color.txt_color_focused));
                b3.setTextColor(getResources().getColor(R.color.txt_color));
                b4.setTextColor(getResources().getColor(R.color.txt_color));
                Toast.makeText(getActivity(),"pending",Toast.LENGTH_SHORT).show();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setBackgroundResource(R.drawable.corner_left);
                b2.setBackgroundResource(R.drawable.pending_btn_bg);
                b3.setBackgroundResource(R.drawable.approved_btn_bg_focused);
                b4.setBackgroundResource(R.drawable.corner_right);
                b1.setTextColor(getResources().getColor(R.color.txt_color));
                b2.setTextColor(getResources().getColor(R.color.txt_color));
                b3.setTextColor(getResources().getColor(R.color.txt_color_focused));
                b4.setTextColor(getResources().getColor(R.color.txt_color));
                Toast.makeText(getActivity(),"Approved",Toast.LENGTH_SHORT).show();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setBackgroundResource(R.drawable.corner_left);
                b2.setBackgroundResource(R.drawable.pending_btn_bg);
                b3.setBackgroundResource(R.drawable.approved_btn_bg);
                b4.setBackgroundResource(R.drawable.corner_right_focused);
                b1.setTextColor(getResources().getColor(R.color.txt_color));
                b2.setTextColor(getResources().getColor(R.color.txt_color));
                b3.setTextColor(getResources().getColor(R.color.txt_color));
                b4.setTextColor(getResources().getColor(R.color.txt_color_focused));
                Toast.makeText(getActivity(),"rejected",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView = view.findViewById(R.id.leave_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        leave_rv_adapter leave_adapter = new leave_rv_adapter(getContext(),leave_data);
        recyclerView.setAdapter(leave_adapter);
        leave_adapter.notifyDataSetChanged();
    }
//recyclerview data passing
    private void dataInitialize() {
        leave_data = new ArrayList<>();
        leave_data.add(new leave_model_class(R.drawable.approved_tag,R.drawable.description_tag,"Leave Application Name","Mon 16 Aug - Wed 18 Aug","Prof Snajay Bhalgama",R.drawable.ic_teacher_24));
        leave_data.add(new leave_model_class(R.drawable.pending_tag,R.drawable.description_tag,"Marriage Function","Wed 1 Aug - Fri 3 Aug","Prof Shakti Parmar",R.drawable.ic_teacher_24));
        leave_data.add(new leave_model_class(R.drawable.rejected_tag,R.drawable.description_tag,"Out Of Station","Tue 16 Aug - Tue 22 Aug","Prof Bhailal Limbasiya",R.drawable.ic_teacher_24));
    }
}