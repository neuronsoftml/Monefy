package com.example.monefy.basic.functionality.fragment.dialogModal;

public interface DialogCallback{
    void onSuccess(String data);
    void onFailure(Exception exception) throws Exception;
}
