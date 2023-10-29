package com.example.monefy.tools.message;

import android.content.Context;
import android.widget.Toast;

public  class ToastManager {
    public static void showToastOnSuccessfulChanges(Context context) {
        Toast.makeText(context, "Успішно внесли зміни", Toast.LENGTH_SHORT).show();
    }

    public static void showToastOnFailureChanges(Context context){
        Toast.makeText(context, "Під час операції виникла помилка", Toast.LENGTH_SHORT).show();
    }
}
