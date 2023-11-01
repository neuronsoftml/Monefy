package com.example.monefy.basic.functionality.model;

import com.example.monefy.R;

import java.io.Serializable;

public class Billings implements Serializable {
    private long balance;
    private long creditLimit;
    private String name;
    private String typeBillings;
    private String typeCurrency;
    private String id;

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

    public long getCreditLimit() {
        return creditLimit;
    }
    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
