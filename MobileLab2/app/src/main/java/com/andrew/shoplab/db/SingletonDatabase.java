package com.andrew.shoplab.db;


import android.arch.persistence.room.Room;
import android.content.Context;

public class SingletonDatabase {

    private static AppDatabase sDatabase;

    private SingletonDatabase() {
    }

    public static AppDatabase getInstance(Context context){
        synchronized (AppDatabase.class){
            if(sDatabase == null){
                sDatabase = Room.databaseBuilder(context,
                        AppDatabase.class,
                        "app_db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return sDatabase;
    }
}
