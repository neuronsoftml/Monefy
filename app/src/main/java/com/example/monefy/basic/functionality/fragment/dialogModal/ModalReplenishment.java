package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.content.Context;

import com.example.monefy.basic.functionality.model.billings.Billings;

public class ModalReplenishment extends DialogModal{
    private Billings theBillToWhichWeTransfer; //Рахунок на який переказуємо.
    private Billings theBillFromWhichWeDebit; //Рахунок з якого списуєм.

    public ModalReplenishment(Context context, int contentView, Billings theBillToWhichWeTransfer, Billings theBillFromWhichWeDebit) {
        super(context, contentView);
        this.theBillToWhichWeTransfer = theBillToWhichWeTransfer;
        this.theBillFromWhichWeDebit = theBillFromWhichWeDebit;
    }

    @Override
    public void modalStart() {
        openModal();
    }

    @Override
    public void setupUIDialogModal() {

    }

    @Override
    public void handlerButtonDialogModal() {

    }
}
