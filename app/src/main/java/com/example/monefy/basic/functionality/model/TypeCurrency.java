package com.example.monefy.basic.functionality.model;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeItem;

public enum TypeCurrency implements ModalTypeItem {
    EUR("EUR"),
    USD("USD"),
    UAH("UAH")
    ;
    private final String title;

    TypeCurrency(String title) {
        this.title = title;
    }

    public TypeCurrency[] getAllTypeCurrency(){
        return TypeCurrency.values();
    }

    public int getIdIconTypeCurrency(){
        if(title.equals(TypeCurrency.EUR.getTitle())){
            return R.drawable.icon_money_euro;
        }
        else if(title.equals(TypeCurrency.USD.getTitle())){
            return R.drawable.icon_money_usd;
        }
        else if(title.equals(TypeCurrency.UAH.getTitle())){
            return R.drawable.icon_money_ukraine_hryvnia;
        }
        return R.drawable.icon_money_usd;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getIdentifier(String title) {
        for(TypeCurrency element: TypeCurrency.values()){
            if(element.getTitle().equals(title)){
                return element.toString();
            }
        }
        return null;
    }

}
