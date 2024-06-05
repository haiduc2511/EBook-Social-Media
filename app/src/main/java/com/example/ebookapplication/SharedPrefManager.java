package com.example.ebookapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "Preferences";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveData(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getData(String key) {
        return sharedPreferences.getString(key, "");
    }


}
