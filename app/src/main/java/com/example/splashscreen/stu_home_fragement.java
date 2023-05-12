package com.example.splashscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link stu_home_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stu_home_fragement extends Fragment {

    List<stu_lecture_model> lecture_data;
    ShimmerFrameLayout shimmerFrameLayout;
    LottieAnimationView LAV;

    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    sessionForS SFS;
    static String Enrollment_No;


    public stu_home_fragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment stu_home_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static stu_home_fragement newInstance(String param1, String param2) {
        stu_home_fragement fragment = new stu_home_fragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SFS = new sessionForS(requireActivity());
        Enrollment_No = SFS.getEnrollment();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stu_home_fragement, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.lecture_recyclerview);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        LAV = view.findViewById(R.id.no_Data_anim);
        shimmerFrameLayout.startShimmer();
        dataInitialize();
    }

    public void dataInitialize() {
        //URL FOR FETCHING API DATA
        String URL = "https://stocky-baud.000webhostapp.com/getAttendacneData.php";
        if (lecture_data != null) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
            lecture_data.clear();
        }
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lecture_data = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            String subject_code;
                            String subject_name;
                            String staff_name;
                            String LAB,location;
                            int staff_id;
                            if(array.length()==0)
                            {
                                LAV.setVisibility(View.VISIBLE);
                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                subject_code= object.getString("sub_code");
                                subject_name= object.getString("sub_name");
                                staff_name= object.getString("staff_name");
                                staff_id = Integer.parseInt(object.getString("staff_id"));
                                LAB = object.getString("lab");
                                location = object.getString("location");
                                if(LAB.equals("1"))
                                {
                                    subject_name = subject_name + " LAB";
                                }
                                lecture_data.add(new stu_lecture_model(subject_name+" ("+subject_code+")",
                                        "Prof "+staff_name,staff_id,subject_code,LAB
                                        ,location));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setHasFixedSize(true);
                            stu_lecture_adapter lecture_adapter = new stu_lecture_adapter(getContext(),lecture_data);
                            recyclerView.setAdapter(lecture_adapter);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            lecture_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
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
                params.put("enrollment", Enrollment_No);
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