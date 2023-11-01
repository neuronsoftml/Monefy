package com.example.monefy.tools.firebase;

import com.google.firebase.firestore.DocumentSnapshot;

public interface OnUserDataCallback {
    void onUserDataReceived(DocumentSnapshot documentSnapshot);

    void onUserDataNotFound();

    void onUserDataError(Exception e);
}
