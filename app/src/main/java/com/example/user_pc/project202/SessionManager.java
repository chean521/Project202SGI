package com.example.user_pc.project202;

import android.content.*;
import android.content.SharedPreferences.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User-PC on 12/3/2017.
 */

public class SessionManager {

    SharedPreferences Pref;
    Editor editor;
    Context ctx;

    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NAME = "student_ID";
    private static final String KEY_DESC = "student_name";
    private static final String KEY_ROLE = "student_role";

    public SessionManager(Context ctx){
        this.ctx = ctx;
        this.Pref = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = this.Pref.edit();
    }

    public void CreateSession(String PID, String Role, String Name){
        editor.putString(IS_LOGIN, "true");
        editor.putString(KEY_NAME, PID);
        editor.putString(KEY_ROLE, Role);
        editor.putString(KEY_DESC, Name);
        editor.commit();
    }

    public List<String> getUserDetails(){
        List<String> Data = new ArrayList<String>();

        Data.add(Pref.getString(KEY_NAME, null));
        Data.add(Pref.getString(KEY_ROLE, null));
        Data.add(Pref.getString(KEY_DESC, null));

        return Data;
    }

    public void LogOut(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        try {
            if (Pref.getString(IS_LOGIN, null).equals("true"))
                return true;
            else
                return false;
        }
        catch(NullPointerException npe){
            return false;
        }
    }
}
