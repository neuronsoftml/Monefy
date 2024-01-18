package com.example.monefy.basic.functionality.model.billings;


import com.example.monefy.basic.functionality.model.CurrencyNbu;
import com.example.monefy.basic.functionality.model.TypeCurrency;

import java.util.List;

public class TotalDebt {

    private static final String UAH = TypeCurrency.UAH.getTitle();
    private static final String USD = TypeCurrency.USD.getTitle();
    private static final String EUR = TypeCurrency.EUR.getTitle();

    private List<Billings> billings;
    private List<CurrencyNbu> currencyNbuS;

    public TotalDebt(List<Billings> billings, List<CurrencyNbu> currencyNbuS) {
        this.billings = billings;
        this.currencyNbuS = currencyNbuS;
    }

    public long calculateTotalDebtAmountInUAHByDebtor(String debtor) {
        long totalDebtAmountInUAH = 0;

        for (Billings billing : billings) {
            if (billing instanceof Debt && ((Debt) billing).getDebtor().equals(debtor)) {
                String currency = billing.getTypeCurrency();
                long debtBalance = billing.getBalance();

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

    private long convertToUAH(long amount, String fromCurrency) {
        if (USD.equals(fromCurrency)) {
            return amount * getCourseByCurrency(USD);
        } else if (EUR.equals(fromCurrency)) {
            return amount * getCourseByCurrency(EUR);
        } else {
            throw new IllegalArgumentException("Unsupported currency for conversion: " + fromCurrency);
        }
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
