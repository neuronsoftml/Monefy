package com.example.monefy.basic.functionality.model;

import com.example.monefy.R;

public enum TypeBillings {
    ORDINARY("Звичайний"),
    DEBT("Борговий"),
    CUMULATIVE("Накопичувальний")
    ;
    private String title;

    TypeBillings(String title) {
        this.title = title;
    }

    public String getTypeBillingsTitle() {
        return title;
    }

    public TypeBillings[] getAllTypeBillings(){
        return TypeBillings.values();
    }

    public static int getIdImageTypeBillings(String typeBillings){
        if(typeBillings.equals("Звичайний")){
            return R.drawable.icon_credit_card_blue;
        } else if (typeBillings.equals("Борговий")) {
            return R.drawable.icon_credit_card_red;
        } else if (typeBillings.equals("Накопичувальний")){
            return R.drawable.icon_credit_card_gold;
        }
        return 0;
    }
}
