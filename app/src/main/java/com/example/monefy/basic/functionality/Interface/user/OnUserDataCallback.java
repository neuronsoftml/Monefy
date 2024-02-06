package com.example.monefy.basic.functionality.Interface.user;


import com.example.monefy.basic.functionality.Interface.OnDataCallback;
import com.example.monefy.basic.functionality.model.user.User;

public interface OnUserDataCallback extends OnDataCallback {
    void onUserDataReceived(User user);
}
