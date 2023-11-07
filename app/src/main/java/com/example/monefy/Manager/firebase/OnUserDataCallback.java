package com.example.monefy.Manager.firebase;

import com.google.firebase.firestore.DocumentSnapshot;

public interface OnUserDataCallback {
    void onUserDataReceived(DocumentSnapshot documentSnapshot);

    void onUserDataNotFound();

    void onUserDataError(Exception e);
}
