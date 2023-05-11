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

        prefs.edit().putString("EnrStu", Enr).commit();
    }

    public String getEnrollment() {
        String Login = prefs.getString("EnrStu","");
        return Login;
    }
    public void setMobile(String Mobile) {

        prefs.edit().putString("setMobileStu", Mobile).commit();
    }
    public void setLocation(String LOC) {
    }
    public String getLocation() {
        String LOC = prefs.getString("setLocation","");
        return LOC;
    }
    public String getMobile() {
        String Pass = prefs.getString("setMobileStu","");
        return Pass;
    }
    public String getName() {
        String LOC = prefs.getString("Name","");
        return LOC;
    }
    public void setName(String name) {
        prefs.edit().putString("Name",name).commit();
    }
}
