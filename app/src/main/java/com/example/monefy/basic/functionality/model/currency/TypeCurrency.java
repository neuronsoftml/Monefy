package com.example.monefy.basic.functionality.model.currency;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.dialogModal.ModalTypeItem;

import java.util.Arrays;

public enum TypeCurrency implements ModalTypeItem {
    EUR("EUR", 978),
    USD("USD",840),

    UAH("UAH",980);

    private final String CCY;
    private final int currencyCodeA;

    TypeCurrency(String CCY, int currencyCodeA) {
        this.CCY = CCY;
        this.currencyCodeA = currencyCodeA;
    }

    public TypeCurrency[] getAllTypeCurrency(){
        return TypeCurrency.values();
    }

    public int getIdIconTypeCurrency(){
        if(CCY.equals(TypeCurrency.EUR.getTitle())){
            return R.drawable.icon_money_euro;
        }
        else if(CCY.equals(TypeCurrency.USD.getTitle())){
            return R.drawable.icon_money_usd;
        }
        else if(CCY.equals(TypeCurrency.UAH.getTitle())){
            return R.drawable.icon_money_ukraine_hryvnia;
        }
        return R.drawable.icon_money_usd;
    }


    public String getTitle() {
        return CCY;
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

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }

    public static String searchCurrencyCcy(int codeA){
        for(TypeCurrency typeCurrency : Arrays.asList(values())){
            if(typeCurrency.currencyCodeA == codeA){
                return typeCurrency.getTitle();
            }
        }
        return "";
    }
}
