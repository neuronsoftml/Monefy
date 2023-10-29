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

public class ModalTypeBillings {
    private Dialog dialogModal;
    private Button buttonOrdinary, buttonDebt, buttonCumulative;
    private Context context;
    private String updateData;

    public ModalTypeBillings(Context context){
        this.context = context;
    }

    public void modalStart(InConclusionCompleteListener listener){
        showDialogModal();
        setupUIDialogModalTypeBillings();
        handlerButtonDialogModal(listener);
    }

    private void showDialogModal() {
        dialogModal = new Dialog(context);
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(R.layout.model_bootom_type_billings);
        dialogModal.show();
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void setupUIDialogModalTypeBillings() {
        buttonOrdinary = dialogModal.findViewById(R.id.button_ordinary);
        buttonDebt = dialogModal.findViewById(R.id.button_debt);
        buttonCumulative = dialogModal.findViewById(R.id.button_cumulative);
    }

    public String getUpdateData() {
        return updateData;
    }

    private void handlerButtonDialogModal(InConclusionCompleteListener listener){
        clickButtonOrdinary(listener);
        clickButtonDebt(listener);
        clickButtonCumulative(listener);
    }

    private void clickButtonOrdinary(InConclusionCompleteListener listener){
        buttonOrdinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    updateData = TypeBillings.ORDINARY.getTypeBillingsTitle();
                    listener.onSuccess();
                    dialogModal.cancel();
                }
            }
        });
    };

    private void clickButtonDebt(InConclusionCompleteListener listener){
        buttonDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    updateData = TypeBillings.DEBT.getTypeBillingsTitle();
                    listener.onSuccess();
                    dialogModal.cancel();
                }
            }
        });
    };

    private void clickButtonCumulative(InConclusionCompleteListener listener){
        buttonCumulative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    updateData = TypeBillings.CUMULATIVE.getTypeBillingsTitle();
                    listener.onSuccess();
                    dialogModal.cancel();
                }
            }
        });
    };


    public interface InConclusionCompleteListener {
        void onSuccess();
        void onFailure(Exception exception);
    }
}
