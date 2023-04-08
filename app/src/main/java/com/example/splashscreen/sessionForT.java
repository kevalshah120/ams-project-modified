package com.example.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sessionForT {
    private SharedPreferences prefs;

    public sessionForT(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setLogin(String login) {

        prefs.edit().putString("login", login).commit();
    }

    public String getLogin() {
        String Login = prefs.getString("login","");
        return Login;
    }
    public void setPass(String Pass) {

        prefs.edit().putString("Pass", Pass).commit();
    }

    public String getPass() {
        String Pass = prefs.getString("Pass","");
        return Pass;
    }
}
