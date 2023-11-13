package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.widget.Button;
import android.widget.EditText;

import com.example.monefy.R;

import java.text.ParseException;

public class ModalInputData extends DialogMenu{

    private EditText inputDataIncome;
    private Button btnNext;
    private Dialog dialog = getDialogModal();
    private String updateData;
    private String argumentData;
    private Resources resources;

    public ModalInputData(Context context, int contentView, String argumentData, Resources resources) {
        super(context, contentView);
        this.argumentData = argumentData;
        this.resources = resources;
    }

    private DialogCallback dialogCallback;

    @Override
    public void modalStart(DialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
        openModal();
        setupUIDialogModal();
        if(!argumentData.isEmpty()){
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
                    dialogCallback.onSuccess();
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

    public String getUpdateData() {
        return updateData;
    }
}
