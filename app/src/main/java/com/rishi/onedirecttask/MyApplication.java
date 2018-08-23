package com.rishi.onedirecttask;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.rishi.onedirecttask.db.MyDatabase;

public class MyApplication extends Application {

    public static MyDatabase myDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        myDatabase= Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"my.db").allowMainThreadQueries().build();
    }
}
