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

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.TypeBillings;

public class ModalTypeBillings implements DialogModal {
    private Dialog dialogModal;
    private Button buttonOrdinary, buttonDebt, buttonCumulative;
    private Context context;
    private String updateData;

    public ModalTypeBillings(Context context){
        this.context = context;
    }

    @Override
    public void modalStart(DialogCallback dialogCallback){
        showDialogModal();
        setupUIDialogModal();
        handlerButtonDialogModal(dialogCallback);
    }

    @Override
    public void showDialogModal() {
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

    public String getUpdateData() {
        return updateData;
    }

    @Override
    public void handlerButtonDialogModal(DialogCallback dialogCallback){
        clickButtonOrdinary(dialogCallback);
        clickButtonDebt(dialogCallback);
        clickButtonCumulative(dialogCallback);
    }

    private void clickButtonOrdinary(DialogCallback dialogCallback){
        buttonOrdinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogCallback != null){
                    updateData = TypeBillings.ORDINARY.getTitle();
                    dialogCallback.onSuccess();
                    dialogModal.cancel();
                }
            }
        });
    };

    private void clickButtonDebt(DialogCallback dialogCallback){
        buttonDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogCallback != null){
                    updateData = TypeBillings.DEBT.getTitle();
                    dialogCallback.onSuccess();
                    dialogModal.cancel();
                }
            }
        });
    };

    private void clickButtonCumulative(DialogCallback dialogCallback){
        buttonCumulative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogCallback != null){
                    updateData = TypeBillings.CUMULATIVE.getTitle();
                    dialogCallback.onSuccess();
                    dialogModal.cancel();
                }
            }
        });
    };

}
