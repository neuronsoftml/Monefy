package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.example.monefy.R;

public class ModalInputText extends DialogModal {

    private final Dialog modal = getDialogModal();
    private Button btnNext;
    private EditText editTxtName;
    private String nameBillings;
    private DialogCallback dialogCallback;

    public ModalInputText(Context context, int contentView, DialogCallback dialogCallback) {
        super(context, contentView);
        this.dialogCallback = dialogCallback;
    }

    @Override
    public void modalStart() {
        openModal();
        setupUIDialogModal();
        handlerButtonDialogModal();
    }

    @Override
    public void setupUIDialogModal() {
        btnNext = modal.findViewById(R.id.btnNext);
        editTxtName = modal.findViewById(R.id.nameBillings);
    }

    @Override
    public void handlerButtonDialogModal() {
        btnNext.setOnClickListener(v->{

            nameBillings = editTxtName.getText().toString();

            if(checkInput(nameBillings)){
                dialogCallback.onSuccess(nameBillings);
                exitModal();
            }
        });
    }

    private boolean checkInput(String name){
        if(name == null || name.isEmpty()){
            setErrorUI();
            return false;
        }
        return true;
    }

    private void setErrorUI() {
        editTxtName.setError("Необхідно заповнити");
    }

}
