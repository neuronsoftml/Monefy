package com.example.monefy.basic.functionality.Interface;

public interface OnDataCallback {
    void onDataNotFound();

    void onDataError(Exception e);
}
