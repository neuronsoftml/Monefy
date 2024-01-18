package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

public class ModalSelectTypeBillings implements DialogFunctional {
    private Dialog dialogModal;
    private Button buttonOrdinary, buttonDebt, buttonCumulative;
    private final Context context;
    private final DialogCallback dialogCallback;

    public ModalSelectTypeBillings(Context context, DialogCallback dialogCallback){
        this.context = context;
        this.dialogCallback = dialogCallback;
    }

    @Override
    public void modalStart(){
        createDialogModal();
        setupUIDialogModal();
        handlerButtonDialogModal();
    }

    @Override
    public void createDialogModal() {
        dialogModal = new Dialog(context);
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(R.layout.model_bottom_type_billings);
        dialogModal.show();
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void setupUIDialogModal() {
        buttonOrdinary = dialogModal.findViewById(R.id.button_ordinary);
        buttonDebt = dialogModal.findViewById(R.id.button_debt);
        buttonCumulative = dialogModal.findViewById(R.id.button_cumulative);
    }


    @Override
    public void handlerButtonDialogModal(){
        clickButtonOrdinary(dialogCallback);
        clickButtonDebt(dialogCallback);
        clickButtonCumulative(dialogCallback);
    }

    private void clickButtonOrdinary(DialogCallback dialogCallback){
        buttonOrdinary.setOnClickListener(v -> {
            if(dialogCallback != null){
                dialogCallback.onSuccess(TypeBillings.ORDINARY.getTitle());
                dialogModal.cancel();
            }
        });
    };

    private void clickButtonDebt(DialogCallback dialogCallback){
        buttonDebt.setOnClickListener(v -> {
            if(dialogCallback != null){
                dialogCallback.onSuccess(TypeBillings.DEBT.getTitle());
                dialogModal.cancel();
            }
        });
    };

    private void clickButtonCumulative(DialogCallback dialogCallback){
        buttonCumulative.setOnClickListener(v -> {
            if(dialogCallback != null){
                dialogCallback.onSuccess(TypeBillings.CUMULATIVE.getTitle());
                dialogModal.cancel();
            }
        });
    }
}
