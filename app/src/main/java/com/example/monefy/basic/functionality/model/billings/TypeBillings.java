package com.example.monefy.basic.functionality.model.billings;

import com.example.monefy.R;

import java.util.ArrayList;
import java.util.List;

public enum TypeBillings {
    ORDINARY("Звичайний"),
    DEBT("Борговий"),
    CUMULATIVE("Накопичувальний")
    ;
    private String title;

    TypeBillings(String title) {
        this.title = title;
    }

    public String getTitle() {
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

    private static List<TypeBillings> list = new ArrayList<>();

    /**
     * Повертає список типів рахунків категорії CUMULATIVE та ORDINARY.
     * @returnд list.
     */
    public static List<TypeBillings> getListTypeBillingsCO(){
        list.add(TypeBillings.CUMULATIVE);
        list.add(TypeBillings.ORDINARY);
        return list;
    }

    /**
     * Повертає список типів рахунків категорії CUMULATIVE та DEBT
     * @return
     */
    public static List<TypeBillings> getListTypeBillingsOD(){
        list.add(TypeBillings.ORDINARY);
        list.add(TypeBillings.DEBT);
        return list;
    }

}
