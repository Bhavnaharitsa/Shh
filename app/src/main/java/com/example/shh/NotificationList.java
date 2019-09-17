package com.example.shh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

public class NotificationList extends AppCompatActivity {


    private RecyclerView recyclerView;
    private FastAdapter<NotificationModel> fastAdapter;
    private ItemAdapter<NotificationModel> itemAdapter;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        recyclerView = findViewById(R.id.recycler);
        itemAdapter = new ItemAdapter<>();
        fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(fastAdapter);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getStringExtra(getString(R.string.notification_text)) != null){
                    String text = intent.getStringExtra(context.getString(R.string.notification_text));
                    NotificationModel notificationEntity = new NotificationModel(text);
                    itemAdapter.add(notificationEntity);
                    fastAdapter.notifyAdapterDataSetChanged();
                    Toast.makeText(NotificationList.this, "notification: " + text , Toast.LENGTH_LONG).show();

                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(this.getString(R.string.intent_filter));
        this.registerReceiver(broadcastReceiver, intentFilter);

    }
}
