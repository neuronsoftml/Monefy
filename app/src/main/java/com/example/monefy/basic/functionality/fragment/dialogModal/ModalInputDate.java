package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.widget.Button;
import android.widget.EditText;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.dialogModal.DialogCallback;

import java.text.ParseException;

public class ModalInputDate extends DialogModal {

    private EditText inputDataIncome;
    private Button btnNext;
    private final Dialog dialog = getDialogModal();
    private String updateData;
    private final String argumentData;
    private final Resources resources;
    private final DialogCallback dialogCallback;

    public ModalInputDate(Context context, int contentView, String argumentData, Resources resources, DialogCallback dialogCallback) {
        super(context, contentView);
        this.argumentData = argumentData;
        this.resources = resources;
        this.dialogCallback = dialogCallback;
    }

    @Override
    public void modalStart() {
        openModal();
        setupUIDialogModal();
        if( argumentData != null){
            inputDataIncome.setText(argumentData);
        }
        handlerButtonDialogModal();
    }

    @Override
    public void setupUIDialogModal() {
        inputDataIncome = dialog.findViewById(R.id.inputDataIncome);
        btnNext = dialog.findViewById(R.id.modalBtnNext);
    }

    @Override
    public void handlerButtonDialogModal() {
        handlerBtnNext();
    }

    private void handlerBtnNext(){
        btnNext.setOnClickListener(v->{
            String data = inputDataIncome.getText().toString();
            if(!data.isEmpty()){
                if(checkDateFormat(data)){
                    updateData = data;
                    dialogCallback.onSuccess(updateData);
                    exitModal();
                }
            }else{
                inputDataIncome.setBackground(resources.getDrawable(R.drawable.selector_error_input));
            }
        });
    };

    private boolean checkDateFormat(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            inputDataIncome.setBackground(resources.getDrawable(R.drawable.selector_error_input));
            return false;
        }
    }

}
