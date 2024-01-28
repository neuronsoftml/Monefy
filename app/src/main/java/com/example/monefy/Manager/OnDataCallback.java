package com.example.monefy.Manager;

public interface OnDataCallback {
    void onDataNotFound();

    void onDataError(Exception e);
}
