package com.example.monefy.Manager.message;

import com.example.monefy.Manager.OnDataCallback;
import com.example.monefy.basic.functionality.model.income.Income;
import com.example.monefy.basic.functionality.model.message.Message;

import java.util.List;

public interface OnMessageCallback extends OnDataCallback {
    void onMessageDataReceived(List<Message> messageList);
}
