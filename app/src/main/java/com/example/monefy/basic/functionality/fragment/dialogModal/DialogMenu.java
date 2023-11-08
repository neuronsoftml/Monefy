package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import com.example.monefy.R;

public abstract class DialogMenu implements DialogModal {
    private Context context;
    private Dialog dialogModal;
    private int contentView;

    public DialogMenu(Context context, int contentView) {
        this.context = context;
        this.contentView = contentView;
        showDialogModal();
    }

    @Override
    public void showDialogModal() {
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
