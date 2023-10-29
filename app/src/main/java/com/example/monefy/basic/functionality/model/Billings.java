package com.example.monefy.basic.functionality.model;

import com.example.monefy.R;

public class Billings {
    private long balance;
    private long creditLimit;
    private String name;
    private String typeBillings;
    private String typeCurrency;

    public Billings(){

    }

    public Billings(long balance, long creditLimit, String name,String typeBillings, String typeCurrency){
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.name = name;
        this.typeBillings = typeBillings;
        this.typeCurrency = typeCurrency;
    }

    public long getBalance() {
        return balance;
    }

    public String getTypeBillings() {
        return typeBillings;
    }

    public String getTypeCurrency() {
        return typeCurrency;
    }

    public int getIdImageTypeBillings(String typeBillings){
        if(typeBillings.equals("Звичайний")){
            return R.drawable.icon_money_svgrepo_com;
        } else if (typeBillings.equals("Борговий")) {
            return R.drawable.icon_benefit_svgrepo_com;
        } else if (typeBillings.equals("Накопичувальний")){
            return R.drawable.icon_savings_svgrepo_com;
        }
        return 0;
    }


    public long getCreditLimit() {
        return creditLimit;
    }


    public String getName() {
        return name;
    }

}
