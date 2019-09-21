package com.example.shh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

public class Welcome extends AppCompatActivity {
    private ViewPager viewPager;
    private int layouts[]={R.layout.intro_1,R.layout.intro_2};
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(Build.VERSION.SDK_INT>19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        viewPager=findViewById(R.id.ViewPager);
        viewPagerAdapter=new ViewPagerAdapter(layouts,this);
        viewPager.setAdapter(viewPagerAdapter);


    }
}
