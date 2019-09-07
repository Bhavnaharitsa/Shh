package com.example.shh;

import android.content.Intent;
import android.os.CountDownTimer;
import android.service.notification.StatusBarNotification;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import rjsv.circularview.CircleView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        String[] country = { "5", " 10", " 15", "30", "45", "60"};
        private static final String TAG = "";
        TextView ReadAboutUs;
    Spinner spin;
    public static Switch shh;
    private ArrayList<StatusBarNotification> list = new ArrayList<>();
    private CircleView circleView;
    private CountDownTimer timer;
    private TextView shhTextView;
    private boolean isShh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        shh=findViewById(R.id.Shh);
        ReadAboutUs=findViewById(R.id.AboutUs);
        ReadAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });

        spin = findViewById(R.id.action_bar_spinner);
        spin.setOnItemSelectedListener(this);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        shhTextView = findViewById(R.id.start_shhing);


        shhTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShh){
                    start(Integer.parseInt(spin.getSelectedItem().toString()));
                    isShh = true;
                }
                else{
                    cancel();
                    isShh = false;
                }
            }
        });

//        spin.setOnItemSelectedListener(this);



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
    private void start(int minutes){
        timer = new CountDownTimer(minutes * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                shhTextView.setText("" + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
               shhTextView.setText("Done!!!");
                NotificationMonitoringService.setCondition(false);
                shh.setChecked(false);
            }
        };
        shh.setChecked(true);
        NotificationMonitoringService.setCondition(true);

        timer.start();
    }

    private void cancel(){
        NotificationMonitoringService.setCondition(false);
        shh.setChecked(false);
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
