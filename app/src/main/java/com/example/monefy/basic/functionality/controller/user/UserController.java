package com.example.monefy.basic.functionality.controller.user;

import android.util.Log;

import com.example.monefy.basic.functionality.Interface.user.OnUserDataCallback;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;
import com.example.monefy.basic.functionality.Interface.DataLoadListener;
import com.example.monefy.basic.functionality.model.user.User;

public class UserController {
    private static UserController userController;

    public static UserController getUserManager(){
        if(userController == null){
            userController = new UserController();
        }
        return userController;
    }

    public UserController() {
    }

    private static User user;
    public void loadGetUserData(DataLoadListener dataLoadListener){
        FirebaseController firebaseController = FirebaseController.getFirebaseManager();
        firebaseController.getUserPersonalData(new OnUserDataCallback() {
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
        UserController.user = user;
    }

    public static User getUser(){
        return user;
    }
}
