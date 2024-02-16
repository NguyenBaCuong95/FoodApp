package com.example.foodorderapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String MY_PREFERENCES = "MY_PREFERENCES";
    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }
public void putStringValue (String key, String s){
    SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, s).apply();
}
    public String getStringValue(String key) {
     SharedPreferences pref = context.getSharedPreferences(
               MY_PREFERENCES, 0);
        return pref.getString(key, "");
    }
}
