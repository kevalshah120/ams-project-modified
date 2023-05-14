package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class teacher_leave_history extends AppCompatActivity {
    RecyclerView recyclerView;
    String ID;
    sessionForT SFT;
    List<leave_history_model> leave_history_model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_leave_history);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerView = findViewById(R.id.leave_history_recyclerview);
        SFT = new sessionForT(getApplication());
        ID = SFT.getLogin();
        String URL = "https://stocky-baud.000webhostapp.com/leaveHistoryForTeacher.php";
        //QUEUE FOR REQUESTING DATA USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //STRING REQUEST OBJECT INITIALIZATION
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            String leave_name,to_date,from_date,stu_name,sem,leave_id,desc,status,final_date;
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                leave_name = object.getString("leave_name");
                                to_date = object.getString("to_date");
                                from_date = object.getString("from_date");
                                stu_name = object.getString("std_name");
                                sem = object.getString("semester");
                                desc = object.getString("description");
                                status = object.getString("status");
                                leave_id = object.getString("leave_id");
                                final_date = dateConversion(from_date,to_date);
                                if(status.equals("approved"))
                                {
                                    leave_history_model.add(new leave_history_model(R.drawable.approved_tag,
                                            leave_name,final_date,
                                            stu_name,sem+" SEM",leave_id,
                                            desc));
                                }
                                else
                                {
                                    leave_history_model.add(new leave_history_model(R.drawable.rejected_tag,
                                            leave_name,final_date,
                                            stu_name,sem+" SEM",leave_id,
                                            desc));
                                }

                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            leave_history_adapter leave_history_adapter = new leave_history_adapter(getApplicationContext(), leave_history_model);
                            recyclerView.setAdapter(leave_history_adapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //GIVING INPUT TO PHP API THROUGH MAP
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tID", ID);
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
//        leave_history_model.add(new leave_history_model(R.drawable.approved_tag,
//        "barr javanu che","21 Apr 2023 - 23 Apr 2023","Keval Shah","6 SEM","01","keval shah is here"));
//        leave_history_model.add(new leave_history_model(R.drawable.rejected_tag,"Kaam che","23 Apr 2023 - 25 Apr 2023","Henarth Agravat","6 SEM","01","keval shah is here"));
//        leave_history_model.add(new leave_history_model(R.drawable.rejected_tag,"Out of india che","25 Apr 2023 - 28 Apr 2023","Yash Matariya","6 SEM","01","keval shah is here"));
    }
    public static String dateConversion(String from, String to) {
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

    static String monNumToWord(String date) {
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