package com.example.shh;

import android.content.Intent;
import android.os.AsyncTask;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import rjsv.circularview.CircleView;
import rjsv.circularview.CircleViewAnimation;
import rjsv.circularview.enumerators.AnimationStyle;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        String[] country = { "5 min", " 10 min", " 15 min", "30 min", "45 min", "1 hour"};
        private static final String TAG = "";
    Spinner spin;
    public static Switch shh;
    private ArrayList<StatusBarNotification> list = new ArrayList<>();
    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        shh=findViewById(R.id.Shh);
//        spin = findViewById(R.id.action_bar_spinner);
        circleView = findViewById(R.id.circular);
        CircleViewAnimation circleViewAnimation = new CircleViewAnimation(circleView)
                .setAnimationStyle(AnimationStyle.CONTINUOUS)
                .setDuration(circleView.getProgressValue())
                .setCustomAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation Starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation Ends
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }).setTimerOperationOnFinish(new Runnable() {
                    @Override
                    public void run() {
                        // Run when the duration reaches 0. Regardless of the AnimationLifecycle or main thread.
                        // Runs and triggers on background.
                    }
                })
                .setCustomInterpolator(new LinearInterpolator());
        circleView.setAnimation(circleViewAnimation);
//        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
//        spin.setAdapter(aa);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
