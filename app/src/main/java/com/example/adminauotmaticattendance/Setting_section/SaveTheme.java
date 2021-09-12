package com.example.adminauotmaticattendance.Setting_section;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveTheme {
    Context context;
    SharedPreferences sharedPreferences;

    public SaveTheme(Context context) {
        this.context = context;
       sharedPreferences=context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
    }

    public void setTheme(boolean bval)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("bkey",bval);
        editor.apply();
    }

    public  boolean getTheme()
    {
        return sharedPreferences.getBoolean("bkey",false);
    }
}
