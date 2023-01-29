package com.example.splashscreen;
//https://www.youtube.com/watch?v=UBgXVGgTaHk&ab_channel=Foxandroid reference taken for binding recyclerview in fragements
//https://youtu.be/4cFL7CMd5QY recyclerview video

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
 * Use the {@link stu_leave_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stu_leave_fragement extends Fragment {
    public static String Response = "yash";
    private TextView b1, b2, b3, b4;
    List<leave_model_class> leave_data;
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

        View view = inflater.inflate(R.layout.fragment_stu_leave_fragement, container, false);
        recyclerView = view.findViewById(R.id.leave_recyclerview);
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
                Toast.makeText(getActivity(), "All", Toast.LENGTH_SHORT).show();
                dataInitialize("all");
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
                Toast.makeText(getActivity(), "pending", Toast.LENGTH_SHORT).show();
                dataInitialize("pen");
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
                Toast.makeText(getActivity(), "Approved", Toast.LENGTH_SHORT).show();
                dataInitialize("app");
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
                Toast.makeText(getActivity(), "rejected", Toast.LENGTH_SHORT).show();
                dataInitialize("rej");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize("all");
    }

    //recyclerview data passing
    private void dataInitialize(String clickedBUTTON) {
        //URL FOR FETCHING API DATA
        String URL = "http://192.168.29.237/mysql/getLeaveData.php";
        if (leave_data != null) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
            leave_data.clear();
        }
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        leave_data = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");
                            String status[] = new String[array.length()];
                            String Leave_name[] = new String[array.length()];
                            String from_date[] = new String[array.length()];
                            String to_date[] = new String[array.length()];
                            String Proof_name[] = new String[array.length()];
                            String finalDate[] = new String[array.length()];
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                if (object.has("status")) {
                                    status[i] = object.getString("status");
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
                                if (object.has("staff_name")) {
                                    Proof_name[i] = object.getString("staff_name");
                                }
                                finalDate[i] = dateConversion(from_date[i], to_date[i] );
                            }
                            if (clickedBUTTON == "all") {
                                for (int i = 0; i < array.length(); i++) {
                                    if (status[i].equals("approved")) {
                                        leave_data.add(new leave_model_class(R.drawable.approved_tag, Leave_name[i],
                                                finalDate[i], Proof_name[i], R.drawable.ic_teacher_24));
                                    } else if (status[i].equals("rejected")) {
                                        leave_data.add(new leave_model_class(R.drawable.rejected_tag, Leave_name[i],
                                                finalDate[i], Proof_name[i], R.drawable.ic_teacher_24));
                                    } else {
                                        leave_data.add(new leave_model_class(R.drawable.pending_tag, Leave_name[i],
                                                finalDate[i], Proof_name[i], R.drawable.ic_teacher_24));
                                    }
                                }
                            }
                            if (clickedBUTTON == "pen") {
                                for (int i = 0; i < array.length(); i++) {
                                    if (status[i].equals("pending")) {
                                        leave_data.add(new leave_model_class(R.drawable.pending_tag, Leave_name[i],
                                                finalDate[i], Proof_name[i], R.drawable.ic_teacher_24));
                                    }
                                }
                            }
                            if (clickedBUTTON == "app") {
                                for (int i = 0; i < array.length(); i++) {
                                    if (status[i].equals("approved")) {
                                        leave_data.add(new leave_model_class(R.drawable.approved_tag, Leave_name[i],
                                                finalDate[i], Proof_name[i], R.drawable.ic_teacher_24));
                                    }
                                }
                            }
                            if (clickedBUTTON == "rej") {
                                for (int i = 0; i < array.length(); i++) {
                                    if (status[i].equals("rejected")) {
                                        leave_data.add(new leave_model_class(R.drawable.rejected_tag, Leave_name[i],
                                                finalDate[i], Proof_name[i], R.drawable.ic_teacher_24));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
//                      catch (ParseException e) {
//                      throw new RuntimeException(e);
//                      }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);
                        leave_rv_adapter leave_adapter = new leave_rv_adapter(getContext(), leave_data);
                        recyclerView.setAdapter(leave_adapter);
                        leave_adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("enrollment", student_login.Enrollment_No);
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

    String dateConversion(String from, String to) {
        String finalSTR ;
        String to_year ="";
        String from_month="" ;
        String from_date="";
        String from_year="";
        String to_date ="";
        String to_month ="";
        int dashFOUND = 0;
        int fromSize = from.length();
        int toSize = to.length();
        int i = 0;
        while (i < fromSize) {
            if (dashFOUND == 0) {
                if (from.charAt(i) == '-') {
                    dashFOUND++;
                } else  {
                    from_year += from.charAt(i);
                }
            } else if (dashFOUND == 1) {
                if (from.charAt(i) == '-') {
                    dashFOUND++;
                } else  {
                    from_month += from.charAt(i);
                }
            } else {
                if (from.charAt(i) == '-') {
                    dashFOUND++;
                } else  {
                    from_date += from.charAt(i);
                }
            }
            i++;
        }
        i = 0;
        dashFOUND = 0;
        while (i < toSize) {
            if (dashFOUND == 0) {
                if (to.charAt(i) == '-') {
                    dashFOUND++;
                } else  {
                    to_year += to.charAt(i);
                }
            } else if (dashFOUND == 1) {
                if (to.charAt(i) == '-') {
                    dashFOUND++;
                } else {
                    to_month += to.charAt(i);
                }
            } else {
                if (to.charAt(i) == '-') {
                    dashFOUND++;
                } else  {
                    to_date += to.charAt(i);
                }
            }
            i++;
        }
        from_month = monNumToWord(from_month);
        to_month = monNumToWord(to_month);
        from = from_date + " " + from_month + " " + from_year;
        to = to_date + " " + to_month + " " + to_year;
        finalSTR = from +" - "+to;
        return finalSTR;
    }

    String monNumToWord(String date) {
        if (date.equals("01")) {
            date = "Jan";
        } else if (date.equals("02")) {
            date = "Feb";
        } else if (date.equals("03")) {
            date = "Mar";
        } else if (date.equals("04")) {
            date = "Apr";
        } else if (date.equals("05")) {
            date = "May";
        } else if (date.equals("06")) {
            date = "Jun";
        } else if (date.equals("07")) {
            date = "Jul";
        } else if (date.equals("08")) {
            date = "Aug";
        } else if (date.equals("09")) {
            date = "Sep";
        } else if (date.equals("10")) {
            date = "Oct";
        } else if (date.equals("11")) {
            date = "Nov";
        } else if (date.equals("12")) {
            date = "Dec";
        }
        return date;
    }
}