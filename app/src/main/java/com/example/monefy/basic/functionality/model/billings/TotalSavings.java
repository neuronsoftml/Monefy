package com.example.monefy.basic.functionality.model.billings;

import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;

import java.util.List;

public class TotalSavings {
    private long amount;
    private List<Billings> billings;
    private List<CurrencyPrivateBank> currencyPrivateBankS;

    public TotalSavings(long amount, List<Billings> billings, List<CurrencyPrivateBank> currencyPrivateBankS) {
        this.amount = amount;
        this.billings = billings;
        this.currencyPrivateBankS = currencyPrivateBankS;
    }

    private void calculatingTotalSavings(){
        for(Billings billing : billings){
            if(billing.getTypeBillings().equals(TypeBillings.CUMULATIVE.getCCY())){

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
        for(CurrencyPrivateBank currencyPrivateBank : currencyPrivateBankS){
            /*if(currencyNbu.getCc().equals(currency)){
                return (long) currencyNbu.getRate();
            }

             */
        }
        return 0;
    }
}
