package com.example.monefy.basic.functionality.UI;

import android.content.res.Resources;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.monefy.R;

public class UpdateUI {
    public static void resetStyleSelect(LinearLayout object, Resources resources){
        object.setBackground(resources.getDrawable(R.drawable.select_input));
    }

    public static void resetStyleSelect(EditText object, Resources resources){
        object.setBackground(resources.getDrawable(R.drawable.selector_input));
    }
}
