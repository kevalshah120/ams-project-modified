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
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        recyclerView = view.findViewById(R.id.teacher_leave_recyclerview);
        dataInitialize();
    }

    private void dataInitialize() {
        tea_leave_data = new ArrayList<>();
        //URL FOR FETCHING API DATA
        String URL = "https://stocky-baud.000webhostapp.com/getLeaveDataForTeacher.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            String semester[] = new String[array.length()];
                            String Leave_name[] = new String[array.length()];
                            String from_date[] = new String[array.length()];
                            String to_date[] = new String[array.length()];
                            String std_name[] = new String[array.length()];
                            String finalDate[] = new String[array.length()];
                            String leave_id[] = new String[array.length()];
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                if (object.has("semester")) {
                                    semester[i] = object.getString("semester");
                                }
                                if (object.has("leave_name")) {
                                    Leave_name[i] = object.getString("leave_name");
                                }
                                if (object.has("from_date")) {
                                    from_date[i] = object.getString("from_date");
                                }
                                if (object.has("to_date")) {
                                    to_date[i] = object.getString("to_date");
                                }
                                if (object.has("std_name")) {
                                    std_name[i] = object.getString("std_name");
                                }
                                leave_id[i]= object.getString("leave_id");
                                finalDate[i] = stu_leave_fragement.dateConversion(from_date[i], to_date[i]);
                                tea_leave_data.add(new teacher_leave_model(Leave_name[i],finalDate[i],std_name[i] ,semester[i]+" SEM",leave_id[i]));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setHasFixedSize(true);
                            teacher_leave_adapter teacher_leave_adapter = new teacher_leave_adapter(getContext(), tea_leave_data);
                            recyclerView.setAdapter(teacher_leave_adapter);
                            teacher_leave_adapter.notifyDataSetChanged();
                        } catch (
                                JSONException e) {
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
                params.put("tID", teacher_login.ID);
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