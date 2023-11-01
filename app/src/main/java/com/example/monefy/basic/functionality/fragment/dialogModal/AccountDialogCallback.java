package com.example.monefy.basic.functionality.fragment.dialogModal;

public interface AccountDialogCallback extends DialogCallback{
    void onSuccessDelete();
    void onClickEdit();
    void onClickOperations();
    void onClickDeposit();
    void onClickWriteOff();
    void onClickTransfer();
}
