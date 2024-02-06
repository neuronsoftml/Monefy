package com.example.monefy.basic.functionality.Interface.message;

import com.example.monefy.basic.functionality.Interface.OnDataCallback;
import com.example.monefy.basic.functionality.model.message.Message;

import java.util.List;

public interface OnMessageCallback extends OnDataCallback {
    void onMessageDataReceived(List<Message> messageList);
}
