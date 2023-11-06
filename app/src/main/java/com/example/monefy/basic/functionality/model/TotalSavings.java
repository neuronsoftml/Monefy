package com.example.monefy.basic.functionality.model;

import java.util.List;

public class TotalSavings {
    private long amount;
    private List<Billings> billings;
    private List<CurrencyNbu> currencyNbuS;

    public TotalSavings(long amount, List<Billings> billings, List<CurrencyNbu> currencyNbuS) {
        this.amount = amount;
        this.billings = billings;
        this.currencyNbuS = currencyNbuS;
    }

    private void calculatingTotalSavings(){
        for(Billings billing : billings){
            if(billing.getTypeBillings().equals(TypeBillings.CUMULATIVE.getTitle())){

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
        calculatingTotalSavings();
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
