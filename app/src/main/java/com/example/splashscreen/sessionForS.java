package com.example.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sessionForS {
    private SharedPreferences prefs;

    public sessionForS(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setEnrollment(String Enr) {
        prefs.edit().putString("Enrollment", Enr).commit();
    }

    public String getEnrollment() {
        String Login = prefs.getString("Enrollment","");
        return Login;
    }
    public void setMobile(String Mobile) {

        prefs.edit().putString("setMobile", Mobile).commit();
    }

    public String getMobile() {
        String Pass = prefs.getString("setMobile","");
        return Pass;
    }
}
