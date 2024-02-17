package com.example.monefy.basic.functionality.UI;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Button;
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

    public static void btnToggleActive(TextView button, Resources resources){
        button.setTextColor(resources.getColor(R.color.white));
        button.setTextSize(18);
        button.setBackground(resources.getDrawable(R.drawable.shape_btn_active_swhitch));
    }

    public static void btnToggleInactive(TextView button, Resources resources){
        button.setTextColor(resources.getColor(R.color.black));
        button.setTextSize(14);
        button.setBackground(resources.getDrawable(R.drawable.shape_btn_inactive_swhitch));
    }
}
