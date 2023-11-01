package com.example.monefy.tools.message;

import android.content.Context;
import android.widget.Toast;

import com.example.monefy.R;

public  class ToastManager {

    public static void showToastOnSuccessful(Context context, int idString) {
        Toast.makeText(context, context.getString(idString), Toast.LENGTH_SHORT).show();
    }

    public static void showToastOnFailure(Context context, int idString){
        Toast.makeText(context, context.getString(idString), Toast.LENGTH_SHORT).show();
    }

}
