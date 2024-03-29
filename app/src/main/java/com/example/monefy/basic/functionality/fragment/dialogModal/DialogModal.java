package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.dialogModal.DialogFunctional;

public abstract class DialogModal implements DialogFunctional {
    private final Context context;
    private Dialog dialogModal;
    private final int contentView;

    public DialogModal(Context context, int contentView) {
        this.context = context;
        this.contentView = contentView;
        createDialogModal();
    }

    @Override
    public void createDialogModal() {
        dialogModal = new Dialog(context);
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(contentView);
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    protected void openModal(){
        dialogModal.show();
    }

    protected void exitModal(){
        dialogModal.dismiss();
    }

    protected Dialog getDialogModal(){
        return dialogModal;
    }
}
