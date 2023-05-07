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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link stu_attendance_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stu_attendance_fragement extends Fragment {
    List<subjectlist_attend_model> subject_data;
    private RecyclerView recyclerView;
    String Enrollment_No;
    sessionForS SFS;
    TextView totalPresent,totalAbsent;
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
        SFS = new sessionForS(requireActivity());
        Enrollment_No = SFS.getEnrollment();
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
        totalAbsent = view.findViewById(R.id.Absent);
        totalPresent = view.findViewById(R.id.Present);
        updateTextView(totalAbsent,totalPresent);
        recyclerView = view.findViewById(R.id.subject_list_rv);
        dataInitialize();
    }

    private void updateTextView(TextView totalAbsent, TextView totalPresent) {
        String URL = "https://stocky-baud.000webhostapp.com/getTotalAttendanceData.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("hello",response);
                            JSONObject obj = new JSONArray(response).getJSONObject(0);
                            if(Integer.parseInt(obj.getString("studentATD")) <= 0 ||Integer.parseInt(obj.getString("totalATD")) <= 0 )
                            {
                                Toast.makeText(requireContext(),"No Lecture Taken",Toast.LENGTH_LONG).show();
                                totalAbsent.setText("100%");
                                totalPresent.setText("100%");
                            }
                            else
                            {
                                int totalATD = Integer.parseInt(obj.getString("totalATD"));
                                int studentATD = Integer.parseInt(obj.getString("studentATD"));
                                int PRE = (studentATD*100)/totalATD;
                                int ABS = 100 - PRE ;
                                totalAbsent.setText(ABS +"%");
                                totalPresent.setText(PRE +"%");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("enrollment",SFS.getEnrollment());
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

    private void dataInitialize() {
        String URL = "https://stocky-baud.000webhostapp.com/getSubjectAttendance.php";
        if (subject_data != null) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
            subject_data.clear();
        }
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("dataInitialize",response);
                        subject_data = new ArrayList<>();
                        //        subject_data = new ArrayList<>();
//        subject_data.add(new subjectlist_attend_model("PPUD (3360702)","86%"));
                        String sub_code,sub_name,lab,percentage;
                        int attended,totalLecture;
                        int atdCount = 0;
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                sub_code = object.getString("sub_code");
                                sub_name = object.getString("sub_name");
                                lab = object.getString("lab");
                                totalLecture = Integer.parseInt(object.getString("lecture_count"));
                                attended = Integer.parseInt(object.getString("count"));
                                percentage = String.valueOf((attended*100)/totalLecture);
                                if(lab.equals("1"))
                                {
                                    sub_name += " LAB";
                                }
                                subject_data.add(new subjectlist_attend_model(sub_name+" ("+sub_code+")",percentage+"%stu"));
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);
                        subjectlist_attend_adapter subjectlist_attend_adapter = new subjectlist_attend_adapter(getContext(),subject_data);
                        recyclerView.setAdapter(subjectlist_attend_adapter);
                        subjectlist_attend_adapter.notifyDataSetChanged();
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
                params.put("enrollment",Enrollment_No);
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
//        subject_data = new ArrayList<>();
//        subject_data.add(new subjectlist_attend_model("PPUD (3360702)","86%"));
//        subject_data.add(new subjectlist_attend_model("Ad.Java (3360701)","96%"));
//        subject_data.add(new subjectlist_attend_model("NMA (3360703)","75%"));
//        subject_data.add(new subjectlist_attend_model("OS (3360704)","52%"));
//        subject_data.add(new subjectlist_attend_model("DBMS (3360705)","88%"));
    }
}