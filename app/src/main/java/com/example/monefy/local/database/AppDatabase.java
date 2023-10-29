package com.example.monefy.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.monefy.local.database.DAO.UserDao;
import com.example.monefy.local.database.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getObInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder
                    (context.getApplicationContext(), AppDatabase.class,"database-monefy-app")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}