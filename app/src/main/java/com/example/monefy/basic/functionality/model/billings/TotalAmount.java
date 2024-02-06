package com.example.monefy.basic.functionality.model.billings;

import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;

import java.util.List;

public class TotalAmount {
    private double amount;
    private final List<Billings> billings;
    private final List<CurrencyMonoBank> currencyMonoBankList;

    public TotalAmount(long amount, List<Billings> billings, List<CurrencyMonoBank> currencyMonoBankList) {
        this.amount = amount;
        this.billings = billings;
        this.currencyMonoBankList = currencyMonoBankList;
    }

    private void calculatingTotalAmount(){
        for(Billings billing : billings){

           if(billing.getTypeBillings().equals(TypeBillings.ORDINARY.getTitle())){

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

    public double getAmount() {
        calculatingTotalAmount();
        return amount;
    }

    private double getCourseByCurrency(String currency){
        for(CurrencyMonoBank element : currencyMonoBankList){
            if(TypeCurrency.searchCurrencyCcy(element.getCurrencyCodeA()).equals(currency)){
                return  element.getBuy();
            }
        }
        return 0;
    }
}
