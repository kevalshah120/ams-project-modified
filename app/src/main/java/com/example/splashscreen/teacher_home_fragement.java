package com.example.splashscreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
 * Use the {@link teacher_home_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teacher_home_fragement extends Fragment {
    List<today_attendance_taken_model> today_attendance_taken_data;
    ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    sessionForT SFT;
    String staff_login;
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
        SFT = new sessionForT(requireActivity());
        staff_login = SFT.getLogin();
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
        recyclerView = view.findViewById(R.id.today_attendance_taken_rv);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        dataInitialize();
    }

    private void dataInitialize() {
        String URL = "https://stocky-baud.000webhostapp.com/getTodayAttendanceDetail.php";
        if (today_attendance_taken_data != null) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
            today_attendance_taken_data.clear();
        }
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("dataInitialize",response);
                        today_attendance_taken_data = new ArrayList<>();
                        //        today_attendance_taken_data = new ArrayList<>();
                        String sub_code,sub_name,lab,percentage,division;
                        int attended,totalLecture;
                        int atdCount = 0;
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                sub_code = object.getString("sub_code");
                                sub_name = object.getString("sub_name");
                                lab = object.getString("lab");
                                division = object.getString("division");
                                totalLecture = Integer.parseInt(object.getString("studentCNT"));
                                attended = Integer.parseInt(object.getString("totalATD"));
//                                percentage = String.valueOf((attended*100)/totalLecture);
                                if(lab.equals("1"))
                                {
                                    sub_name += " LAB";
                                }
                                today_attendance_taken_data.add(
                                        new today_attendance_taken_model
                                                (sub_name+" ("+sub_code+")",""+attended+"/"+totalLecture,division));
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);
                        today_attendance_taken_adapter today_attendance_taken_adapter = new today_attendance_taken_adapter(getContext(),today_attendance_taken_data);
                        recyclerView.setAdapter(today_attendance_taken_adapter);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        today_attendance_taken_adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login_id",staff_login);
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