package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.example.monefy.tools.message.ToastManager;

import java.util.ArrayList;
import java.util.List;

public class ModalTypeCurrency implements DialogModal{

    private String updateData;
    private LinearLayout elementTypeCurrencies;
    private List<Button> buttonListModalTypeCurrency = new ArrayList<>();
    private Dialog dialogModal;
    private Context context;

    public ModalTypeCurrency(Context context){
        this.context = context;
    }

    @Override
    public void modalStart(DialogCallback dialogCallback){
        showDialogModal();
        createButtonDialogModal();
        setupUIDialogModal();
        handlerButtonDialogModal(dialogCallback);
    }

    @Override
    public void showDialogModal() {
        dialogModal = new Dialog(context);
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(R.layout.model_bottom_type_currencies);
        dialogModal.show();
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void createButtonDialogModal() {
        elementTypeCurrencies = dialogModal.findViewById(R.id.linearLayoutListButton);
        for (TypeCurrency typeCurrency : TypeCurrency.values()) {
            Button button = createCurrencyButton(typeCurrency);
            elementTypeCurrencies.addView(button);
            buttonListModalTypeCurrency.add(button);
        }
    }

    private Button createCurrencyButton(TypeCurrency typeCurrency) {
        Button button = new Button(context);
        button.setText(typeCurrency.getTypeCurrencyTitle());
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(typeCurrency.getIdIconTypeCurrency(), 0, 0, 0);
        button.setCompoundDrawablePadding(10);
        button.setTag(typeCurrency.toString());
        button.setGravity(Gravity.CENTER_VERTICAL);
        button.setAllCaps(false);
        button.setBackgroundColor(Color.WHITE);
        return button;
    }

    @Override
    public void setupUIDialogModal(){
        for (int i = 0; i < elementTypeCurrencies.getChildCount(); i++) {
            View childView = elementTypeCurrencies.getChildAt(i);

            if (childView instanceof Button) {
                buttonListModalTypeCurrency.add((Button) childView);
            }
        }
    }

    @Override
    public void handlerButtonDialogModal(DialogCallback dialogCallback) {
        for (Button button : buttonListModalTypeCurrency) {
            button.setOnClickListener(v -> {
                for (TypeCurrency typeCurrency : TypeCurrency.values()) {
                    if (button.getText().toString().equals(typeCurrency.getTypeCurrencyTitle())) {

                        if(dialogCallback != null){
                            updateData = typeCurrency.getTypeCurrencyTitle();
                            dialogCallback.onSuccess();
                        }
                        dialogModal.cancel();
                        ToastManager.showToastOnSuccessful(context, R.string.toast_successful_entered_the_data);
                    }
                }
            });
        }
    }

    public String getUpdateData(){
        return updateData;
    }
}

