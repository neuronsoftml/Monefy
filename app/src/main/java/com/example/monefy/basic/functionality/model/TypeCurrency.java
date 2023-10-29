package com.example.monefy.basic.functionality.model;

import com.example.monefy.R;

public enum TypeCurrency {
    EUR("ЄВРО"),
    USD("ДОЛАР"),
    UAH("ГРИВНЯ")
    ;
    private String title;

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
        if(title.equals("ЄВРО")){
            return R.drawable.icon_money_euro;
        }
        else if(title.equals("ДОЛАР")){
            return R.drawable.icon_money_usd;
        }
        else if(title.equals("ГРИВНЯ")){
            return R.drawable.icon_money_ukraine_hryvnia;
        }
        return R.drawable.icon_money_usd;
    }
}
