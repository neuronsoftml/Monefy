package com.example.monefy.Manager.message;

import android.content.Context;
import android.widget.Toast;

public  class ToastManager {

    public static void showToastOnSuccessful(Context context, int idString) {
        Toast.makeText(context, context.getString(idString), Toast.LENGTH_SHORT).show();
    }

    public static void showToastOnFailure(Context context, int idString){
        Toast.makeText(context, context.getString(idString), Toast.LENGTH_SHORT).show();
    }

}
