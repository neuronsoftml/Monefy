package com.example.monefy.basic.functionality.model.income;

import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeItem;

public enum TypeIncomes implements ModalTypeItem {
    SALARY("Зарплата"),
    BUSINESS("Бізнес"),
    FREELANCE("Фріланс"),
    REAL_ESTATE("Нерухомість"),
    INVESTMENTS("Інвестиції"),
    PENSIONS("Пенсійні"),
    SUBSIDIES("Субсидії"),;

    TypeIncomes(String title) {
        this.title = title;
    }

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getIdentifier(String title) {
        for(TypeIncomes element : TypeIncomes.values()){
            if(element.getTitle().equals(title)){
                return element.toString();
            }
        }
        return null;
    }


}
