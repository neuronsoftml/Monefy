package com.example.monefy.basic.functionality.model.billings;

import com.example.monefy.basic.functionality.Interface.dialogModal.ModalTypeItem;

public enum TypeDebtorSide implements ModalTypeItem {
    DEBT_TO_ME("мені винні"),
    DEBT_TO_ANOTHER("я винен")
    ;

    private String title;

    TypeDebtorSide(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getIdentifier(String title) {
        return null;
    }
}
