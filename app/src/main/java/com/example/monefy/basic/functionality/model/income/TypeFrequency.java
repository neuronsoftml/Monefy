package com.example.monefy.basic.functionality.model.income;

import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeItem;

public enum TypeFrequency implements ModalTypeItem {
    MONTHKY("щомісячно"),
    ONE_TIME("одноразово"),
    DAILY("щодня")
    ;

    TypeFrequency(String title) {
        this.title = title;
    }

    private String title;

    @Override
    public String getCCY() {
        return title;
    }

    @Override
    public String getIdentifier(String title) {
        for(TypeFrequency element : TypeFrequency.values()){
            if(element.getCCY().equals(title)){
                return element.toString();
            }
        }
        return null;
    }
}
