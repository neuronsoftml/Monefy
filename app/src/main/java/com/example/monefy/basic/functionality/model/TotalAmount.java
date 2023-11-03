package com.example.monefy.basic.functionality.model;

import java.util.ArrayList;
import java.util.List;

public class TotalAmount {
    private long amount;
    private List<Billings> billings = new ArrayList<>();
    private List<CurrencyNbu> currencyNbuS = new ArrayList<>();

    public TotalAmount(long amount, List<Billings> billings, List<CurrencyNbu> currencyNbuS) {
        this.amount = amount;
        this.billings = billings;
        this.currencyNbuS = currencyNbuS;
    }

    private void calculatingTotalAmount(){
        for(Billings billing : billings){
            if(!billing.getTypeBillings().equals(TypeBillings.DEBT.getTypeBillingsTitle())){
                if(billing.getTypeCurrency().equals("UAH")){
                    amount = amount + billing.getBalance();
                }
                else if(billing.getTypeCurrency().equals("USD")){
                    amount = amount + (billing.getBalance() * getCourseByCurrency("USD"));
                }
                else if(billing.getTypeCurrency().equals("EUR")){
                    amount = amount + (billing.getBalance() * getCourseByCurrency("EUR"));
                }
            }
        }
    }

    public long getAmount() {
        calculatingTotalAmount();
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
