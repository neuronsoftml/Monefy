package com.example.monefy.basic.functionality.fragment.dialogModal;

import androidx.fragment.app.FragmentManager;

public interface BillingDialogCallback extends DialogCallback{
    void onSuccessDelete();
    void onClickEdit();
    void onClickOperations();
    void onClickReplenishment();
    void onClickWriteOff();
    void onClickTransfer();
}
