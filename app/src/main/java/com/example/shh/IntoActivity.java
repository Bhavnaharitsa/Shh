package com.example.shh;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntoActivity extends AppCompatActivity {
    public static final int splash_screen_timer = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }, splash_screen_timer);
    }
}
