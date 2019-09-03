package com.example.shh.Realm;


import com.example.shh.Model.SbnModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController  {

    private static RealmController instance;
    private final Realm realm;


    public RealmController() {

        realm = Realm.getDefaultInstance();
    } //The class is designed in such a way that when a new instance of RealmController is created,
    // a new instance of Realm is created as well

    public static RealmController with() {

        if(instance == null)
        {
            instance = new RealmController();
        }

        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    } //We can call this if we are sure that instance != null. Else, we should call with() method

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm instance
    public void refresh() {

        realm.refresh();

    }

    //Clear all objects of Idea.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(SbnModel.class);
        realm.commitTransaction();

    }

    //Return an arraylist consisting of all the objects of Idea.class
    public RealmResults<SbnModel> getAllBooks() {

        return realm.where(SbnModel.class).findAll();
    }

    public boolean isEmpty() {

        return !realm.allObjects(SbnModel.class).isEmpty();

    }

    public RealmResults<SbnModel> sampleQuery() {

        return realm.where(SbnModel.class)
                .contains("tag", "app")
                .or()
                .contains("name", "IdeaPad")
                .findAll();
    }
}