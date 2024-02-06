package com.example.monefy.basic.functionality.model.billings;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.dialogModal.ModalTypeItem;

import java.util.ArrayList;
import java.util.List;

public enum TypeBillings implements ModalTypeItem {
    ORDINARY("Звичайний"),
    DEBT("Борговий"),
    CUMULATIVE("Накопичувальний");
    private String title;

    TypeBillings(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getIdentifier(String title) {
        for(TypeBillings element: TypeBillings.values()){
            if(element.getTitle().equals(title)){
                return element.toString();
            }
        }
        return null;
    }

    public TypeBillings[] getAllTypeBillings(){
        return TypeBillings.values();
    }

    public static int getIdImageTypeBillings(String typeBillings){
        if(typeBillings.equals(TypeBillings.ORDINARY.getTitle())){
            return R.drawable.icon_credit_card_blue;
        } else if (typeBillings.equals(TypeBillings.DEBT.getTitle())) {
            return R.drawable.icon_credit_card_red;
        } else if (typeBillings.equals(TypeBillings.CUMULATIVE.getTitle())){
            return R.drawable.icon_credit_card_gold;
        }
        return 0;
    }

    private static final List<TypeBillings> list = new ArrayList<>();

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
