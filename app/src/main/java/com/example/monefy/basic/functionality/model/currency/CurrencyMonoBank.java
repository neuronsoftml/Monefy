package com.example.monefy.basic.functionality.model.currency;

public class CurrencyMonoBank {

    private int currencyCodeA;
    private int currencyCodeB;
    private int date;
    private double rateBuy;
    private double rateSell;
    private String ccy;

    public CurrencyMonoBank(int currencyCodeA, int currencyCodeB, int date, double rateBuy, double rateSell) {
        this.currencyCodeA = currencyCodeA;
        this.currencyCodeB = currencyCodeB;
        this.date = date;
        this.rateBuy = rateBuy;
        this.rateSell = rateSell;
        this.ccy = TypeCurrency.searchCurrencyCcy(currencyCodeA);
    }

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }

    public int getCurrencyCodeB() {
        return currencyCodeB;
    }

    public int getDate() {
        return date;
    }

    public double getBuy() {
        return rateBuy;
    }

    public double getSell() {
        return rateSell;
    }

    public String getCcy() {
        return ccy;
    }

}
