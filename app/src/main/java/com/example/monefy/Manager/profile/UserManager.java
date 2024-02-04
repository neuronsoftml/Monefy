package com.example.monefy.Manager.profile;

import android.util.Log;

import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.user.User;

public class UserManager {
    private static UserManager userManager;

    public static UserManager getUserManager(){
        if(userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }

    public UserManager() {
    }

    private static User user;
    public void loadGetUserData(DataLoadListener dataLoadListener){
        FirebaseManager firebaseManager = FirebaseManager.getFirebaseManager();
        firebaseManager.getUserPersonalData(new OnUserDataCallback() {
            @Override
            public void onUserDataReceived(User user) {
                updateUser(user);
                dataLoadListener.onDataLoaded();
            }

            @Override
            public void onDataNotFound() {
                Log.d("error","Відсутні дані");
            }

            @Override
            public void onDataError(Exception e) {
                Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
            }
        });
    }

    private void updateUser(User user){
        UserManager.user = user;
    }

    public static User getUser(){
        return user;
    }
}
