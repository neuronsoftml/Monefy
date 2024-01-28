package com.example.monefy.Manager.profile;


import com.example.monefy.Manager.OnDataCallback;
import com.example.monefy.basic.functionality.model.user.User;

public interface OnUserDataCallback extends OnDataCallback {
    void onUserDataReceived(User user);
}
