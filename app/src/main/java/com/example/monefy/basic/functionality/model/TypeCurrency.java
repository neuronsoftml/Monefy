package com.example.monefy.basic.functionality.model;

import com.example.monefy.R;

public enum TypeCurrency {
    EUR("EUR"),
    USD("USD"),
    UAH("UAH")
    ;
    private final String title;

    TypeCurrency(String title) {
        this.title = title;
    }

    public String getTypeCurrencyTitle() {
        return title;
    }

    public TypeCurrency[] getAllTypeCurrency(){
        return TypeCurrency.values();
    }

    public int getIdIconTypeCurrency(){
        if(title.equals(TypeCurrency.EUR.getTypeCurrencyTitle())){
            return R.drawable.icon_money_euro;
        }
        else if(title.equals(TypeCurrency.USD.getTypeCurrencyTitle())){
            return R.drawable.icon_money_usd;
        }
        else if(title.equals(TypeCurrency.UAH.getTypeCurrencyTitle())){
            return R.drawable.icon_money_ukraine_hryvnia;
        }
        return R.drawable.icon_money_usd;
    }
}
