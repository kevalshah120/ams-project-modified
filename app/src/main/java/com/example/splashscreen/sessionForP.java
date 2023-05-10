package com.example.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sessionForP {
    private SharedPreferences prefs;

    public sessionForP(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setEnrollment(String Enr) {

        prefs.edit().putString("EnrPar", Enr).commit();
    }

    public String getEnrollment() {
        String Login = prefs.getString("EnrPar","");
        return Login;
    }
    public void setMobile(String Mobile) {

        prefs.edit().putString("MobilePar", Mobile).commit();
    }
    public String getMobile() {
        String Pass = prefs.getString("MobilePar","");
        return Pass;
    }
}
