package com.example.monefy.basic.functionality.UI;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.monefy.R;

public class UpdateUI {
    public static void resetStyleSelect(LinearLayout object, Resources resources){
        object.setBackground(resources.getDrawable(R.drawable.select_input));
    }

    public static void resetStyleSelect(EditText object, Resources resources){
        object.setBackground(resources.getDrawable(R.drawable.selector_input));
    }

    public static void blockElement(LinearLayout object, Resources resources){
        object.setBackground(resources.getDrawable(R.drawable.block_select_input));
    }

    public static void replaysIconBlockText(TextView object){
        Drawable drawable = ContextCompat.getDrawable(object.getContext(), R.drawable.baseline_block_18);
        object.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
    }
}
