package com.example.monefy.basic.functionality.UI;

import android.content.res.Resources;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.monefy.R;

public class UpdateUIError {
    public static void setStyleError(LinearLayout object, Resources resources){
        object.setBackground(resources.getDrawable(R.drawable.select_input_error));
    }

    public static void setStyleError(EditText object, Resources resources){
        object.setBackground(resources.getDrawable(R.drawable.selector_error_input));
    }
}
