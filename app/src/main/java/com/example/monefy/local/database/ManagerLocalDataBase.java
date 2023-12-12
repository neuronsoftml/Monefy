package com.example.monefy.local.database;

import android.content.Context;

import com.example.monefy.local.database.model.User;

public class ManagerLocalDataBase {


    public static User getUserToId(Context context, int userId){
       return connectDB(context).userDao().getUserById(userId);
    }

    public static void createUserToId(Context context, String email, String password, boolean autoLogin){
        connectDB(context).userDao().insert(new User(email, password, autoLogin));
    }

    public static void deleteUserToId(Context context, int userId){
        connectDB(context).userDao().deleteUserById(userId);
    }

    private static AppDatabase connectDB(Context context){
        return AppDatabase.getObInstance(context);
    }
}
