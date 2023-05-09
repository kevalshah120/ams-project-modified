package com.example.splashscreen;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class teacher_student_frag extends Fragment {
    List<teacher_student_model> student_model;
    private RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;

    List<teacher_student_model> filteredList;
    private SearchView student_searchView;
    LottieAnimationView LAV;
    FloatingActionButton float_add_btn;
    private student_list_adapter student_list_adapter;
    sessionForT SFT;
    private static String ID;

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
        SFT = new sessionForT(requireActivity());
        ID = SFT.getLogin();
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
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        recyclerView = view.findViewById(R.id.student_list_rv);
        LAV = view.findViewById(R.id.no_Data_anim);
        student_searchView = view.findViewById(R.id.searchview);
        float_add_btn = view.findViewById(R.id.float_add_Btn);
        dataInitialize();
        student_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredList = new ArrayList<teacher_student_model>();
                if (newText.length() > 0) {
                    for (int i = 0; i < student_model.size(); i++) {
                        if (student_model.get(i).getStu_name().toUpperCase().contains(newText.toUpperCase()) || student_model.get(i).getEnr_no().contains(newText.toUpperCase())) {
                            filteredList.add(student_model.get(i));
                        }
                    }
                } else {
                    filteredList.addAll(student_model);
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
        float_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), student_add.class);
                startActivity(i);
            }
        });
    }

    private void dataInitialize() {
        String URL = "https://stocky-baud.000webhostapp.com/fetchStudentProfile.php";
        if (student_model != null) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
            student_model.clear();
        }
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        student_model = new ArrayList<teacher_student_model>();
                        String enr;
                        String name;
                        String url;
                        try {
                            JSONArray array = new JSONArray(response);
                            if (array.length() == 0) {
                                LAV.setVisibility(View.VISIBLE);
                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                name = object.getString("std_name");
                                url = object.getString("image");
                                enr = object.getString("enr_no");
                                student_model.add(new teacher_student_model(enr, name,url));
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);
                        student_list_adapter = new student_list_adapter(getContext(), student_model);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(student_list_adapter);
                        student_list_adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireActivity(), "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("T_ID", ID);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}