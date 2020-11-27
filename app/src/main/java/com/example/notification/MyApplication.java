package com.example.notification;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application {
    private  static Context context;
    private  static SharedPreferences preferences;
    private  static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferences = getSharedPreferences("data",MODE_PRIVATE);
        editor = getSharedPreferences("data",MODE_PRIVATE).edit();
    }

  public static   Context getContext(){
        return context;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static SharedPreferences.Editor getEditor() {
        return editor;
    }
}
