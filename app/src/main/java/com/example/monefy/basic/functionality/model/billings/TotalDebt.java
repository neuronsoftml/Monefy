package com.example.monefy.basic.functionality.model.billings;


import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;

import java.util.List;

public class TotalDebt {

    private static final String UAH = TypeCurrency.UAH.getTitle();
    private static final String USD = TypeCurrency.USD.getTitle();
    private static final String EUR = TypeCurrency.EUR.getTitle();
    private final List<Billings> billings;
    private final List<CurrencyMonoBank> currencyMonoBankList;

    public TotalDebt(List<Billings> billings, List<CurrencyMonoBank> currencyPrivateBankS) {
        this.billings = billings;
        this. currencyMonoBankList = currencyPrivateBankS;
    }

    public long calculateTotalDebtAmountInUAHByDebtor(String debtor) {
        long totalDebtAmountInUAH = 0;

        for (Billings billing : billings) {
            if (billing instanceof Debt && ((Debt) billing).getDebtor().equals(debtor)) {
                String currency = billing.getTypeCurrency();
                double debtBalance = billing.getBalance();

                if (currency.equals(UAH)) {
                    totalDebtAmountInUAH += debtBalance;
                } else if (currency.equals(USD)) {
                    totalDebtAmountInUAH += convertToUAH(debtBalance, USD);
                } else if (currency.equals(EUR)) {
                    totalDebtAmountInUAH += convertToUAH(debtBalance, EUR);
                } else {
                    throw new IllegalArgumentException("Unknown currency: " + currency);
                }
            }
        }

        return totalDebtAmountInUAH;
    }

    private double convertToUAH(double amount, String fromCurrency) {
        if (USD.equals(fromCurrency)) {
            return amount * getCourseByCurrency(USD);
        } else if (EUR.equals(fromCurrency)) {
            return amount * getCourseByCurrency(EUR);
        } else {
            throw new IllegalArgumentException("Unsupported currency for conversion: " + fromCurrency);
        }
    }


    private double getCourseByCurrency(String currency){
        for(CurrencyMonoBank element :  currencyMonoBankList){
            if(TypeCurrency.searchCurrencyCcy(element.getCurrencyCodeA()).equals(currency)){
                return  element.getBuy();
            }
        }
        return 0;
    }
}
