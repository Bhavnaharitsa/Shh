package com.example.shh.Model;

import android.service.notification.StatusBarNotification;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SbnModel extends RealmObject {
    @PrimaryKey
    private long id;
    private StatusBarNotification sbn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SbnModel(StatusBarNotification sbn) {
        this.id = sbn.getId();
        this.sbn = sbn;
    }

    public StatusBarNotification getSbn() {
        return sbn;
    }

    public void setSbn(StatusBarNotification sbn) {
        this.sbn = sbn;
    }
}
