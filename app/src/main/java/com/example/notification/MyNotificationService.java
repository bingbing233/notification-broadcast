package com.example.notification;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;


public class MyNotificationService  extends NotificationListenerService {
final String TAG = "MyNotificationService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Notification notification = sbn.getNotification();
        String content = notification.extras.getString(Notification.EXTRA_TEXT);

        //语音播报
        if(MyApplication.getPreferences().getBoolean("tts",false)&&
        MyApplication.getPreferences().getBoolean(sbn.getPackageName(),false)){
            Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
            new MyTextToSpeech(MyApplication.getContext(),content);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "onNotificationRemoved: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
