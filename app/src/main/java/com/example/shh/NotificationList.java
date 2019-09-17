package com.example.shh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

public class NotificationList extends Fragment {


    private RecyclerView recyclerView;
    private FastAdapter<NotificationModel> fastAdapter;
    private ItemAdapter<NotificationModel> itemAdapter;

    private Context mContext;

    BroadcastReceiver broadcastReceiver;

    public static String TAG = "NotificationList";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification_list, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        itemAdapter = new ItemAdapter<>();
        fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(fastAdapter);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getStringExtra(getString(R.string.notification_text)) != null){
                    String text = intent.getStringExtra(context.getString(R.string.notification_text));
                    NotificationModel notificationEntity = new NotificationModel(text);
                    itemAdapter.add(notificationEntity);
                    fastAdapter.notifyAdapterDataSetChanged();
                    Toast.makeText(mContext, "notification: " + text , Toast.LENGTH_LONG).show();

                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(this.getString(R.string.intent_filter));
        mContext.registerReceiver(broadcastReceiver, intentFilter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContext.unregisterReceiver(broadcastReceiver);
    }
}
