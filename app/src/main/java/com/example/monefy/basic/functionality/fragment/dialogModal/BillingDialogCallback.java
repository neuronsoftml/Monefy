package com.example.monefy.basic.functionality.fragment.dialogModal;


import com.example.monefy.basic.functionality.model.billings.Billings;

public interface BillingDialogCallback extends DialogCallback{
    void onSuccessDelete(Billings billing);
    void onClickEdit(Billings billing);
    void onClickOperations(Billings billing);
    void onClickReplenishment(Billings billing);
    void onClickWriteOff(Billings billing);
    void onClickTransfer(Billings billing);
}
