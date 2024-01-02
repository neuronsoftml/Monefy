package com.example.monefy.basic.functionality.model.income;

import com.example.monefy.basic.functionality.model.CurrencyNbu;

import java.util.Currency;
import java.util.List;

public class TotalReceipts {
    private double amount;
    private List<Income> incomes;
    private List<CurrencyNbu> currencyNbuS;

    public TotalReceipts(double amount, List<Income> incomes, List<CurrencyNbu> currencyNbuS) {
        this.amount = amount;
        this.incomes = incomes;
        this.currencyNbuS = currencyNbuS;
    }

    private void calculatingTotalAmount(){
        for(Income income : incomes){
            if(!income.getCategory().equals(TypeIncomes.INVESTMENTS.getTitle())){
                switch (income.getTypeCurrency()) {
                    case "UAH":
                        amount = amount + income.getAmount();
                        break;
                    case "USD":
                        amount = amount + (income.getAmount() * getCourseByCurrency("USD"));
                        break;
                    case "EUR":
                        amount = amount + (income.getAmount() * getCourseByCurrency("EUR"));
                        break;
                }
            }
        }
    }

    public double getAmount(){
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

