package com.example.shh;

import android.content.Intent;
import android.os.AsyncTask;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    public static Switch shh;
    private ArrayList<StatusBarNotification> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shh=findViewById(R.id.Shh);

        shh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    NotificationMonitoringService.setCondition(true);
                    /**Intent intent = new Intent();
                    intent.setAction("CLEAR_ALL");
                    sendBroadcast(intent);
                     **/
                }
                else{
                    NotificationMonitoringService.setCondition(false);
                    /**Intent intent = new Intent();
                    intent.setAction("SET_FALSE");
                    sendBroadcast(intent);
                     **/
                    try {
                        list = (ArrayList<StatusBarNotification>)InternalStorage.readObject(MainActivity.this, "KEY");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onCheckedChanged: " + list.toString());
                    Toast.makeText(MainActivity.this, list.get(0).getPackageName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
