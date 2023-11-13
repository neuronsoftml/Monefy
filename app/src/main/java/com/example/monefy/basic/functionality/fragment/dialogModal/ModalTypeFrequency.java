package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ModalTypeFrequency extends DialogMenu{

    private final Dialog dialog = getDialogModal();

    public ModalTypeFrequency(Context context, int contentView) {
        super(context, contentView);
    }

    @Override
    public void modalStart(DialogCallback dialogCallback) {
        openModal();
        setupUIDialogModal();
        handlerButtonDialogModal();
    }

    private List<Button> buttonList = new ArrayList<>();

    @Override
    public void setupUIDialogModal() {

    }

    @Override
    public void handlerButtonDialogModal() {

    }
}
