package com.example.monefy.basic.functionality.controller.activity;

import android.app.Activity;
import android.content.Intent;

public class ActivityController {

    //Перезапускає активність
    public static void resetActivity(Activity currentActivity){
        if (currentActivity != null) {
            Intent intent = new Intent(currentActivity, currentActivity.getClass());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            currentActivity.finish();
            currentActivity.startActivity(intent);
        }
    }
}
