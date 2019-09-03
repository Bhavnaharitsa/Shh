package com.example.shh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationMonitoringService extends NotificationListenerService {
    public static String TAG = NotificationMonitoringService.class.getSimpleName();

    public static void setCondition(boolean condition) {
        NotificationMonitoringService.condition = condition;
    }

    public static boolean condition = false; // say your test is running
    ArrayList<StatusBarNotification> sbnList = new ArrayList<>();
    ArrayList<StatusBarNotification> retrievedList = new ArrayList<>();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        sbnList.add(sbn);
        try {
            InternalStorage.writeObject(this, "KEY", sbnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (condition)
            cancelAllNotifications();
        Log.d(TAG, "onNotificationPosted: sbnList: " + sbnList.size());
        Log.d(TAG, "onNotificationPosted: retrievedList" + retrievedList.size());
        Log.d(TAG, "CANCELLED NOTIFICATIONS");//Cancel all the notifications . No Disturbance
//this

        //else
        // nothing.
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null) {
                Log.d(TAG, "onNotificationPosted: " + condition);
                if (intent.getAction().equals("CLEAR_ALL")) {
                    condition = true;
                }
                else{
                    condition = false;
                }
            }
        }
    }
}
