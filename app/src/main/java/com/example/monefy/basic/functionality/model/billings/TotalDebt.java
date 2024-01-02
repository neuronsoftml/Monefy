package com.example.monefy.basic.functionality.model.billings;

import com.example.monefy.basic.functionality.model.CurrencyNbu;

import java.util.List;

public class TotalDebt {
    private List<Billings> billings;
    private List<CurrencyNbu> currencyNbuS;

    public TotalDebt(List<Billings> billings, List<CurrencyNbu> currencyNbuS) {
        this.billings = billings;
        this.currencyNbuS = currencyNbuS;
    }
    public long calculatingConvert(String typeDEBT){
        long amount = 0;

        for(Billings billing : billings) {
            if (billing.getTypeBillings().equals(TypeBillings.DEBT.getTitle()) && billing.getObligation().equals(typeDEBT)) {

                if (billing.getTypeCurrency().equals("UAH")) {
                    amount = amount + billing.getBalance();
                } else if (billing.getTypeCurrency().equals("USD")) {
                    amount = amount + (billing.getBalance() * getCourseByCurrency("USD"));
                } else if (billing.getTypeCurrency().equals("EUR")) {
                    amount = amount + (billing.getBalance() * getCourseByCurrency("EUR"));
                }
            }
        }
        return amount;
    }


    private long getCourseByCurrency(String currency){
        for(CurrencyNbu currencyNbu : currencyNbuS){
            if(currencyNbu.getCc().equals(currency)){
                return (long) currencyNbu.getRate();
            }
        }
        return 0;
    }
}
