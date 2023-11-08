package com.example.monefy.basic.functionality.model.billings;

import java.io.Serializable;
import java.util.Objects;

public class Billings implements Serializable {
    private long balance;
    private long creditLimit;
    private String name;
    private String typeBillings;
    private String typeCurrency;
    private String obligation;
    private String id;

    public Billings(){

    }

    public Billings(long balance, long creditLimit, String name,String typeBillings, String typeCurrency, String obligation){
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.name = name;
        this.typeBillings = typeBillings;
        this.typeCurrency = typeCurrency;
        this.obligation = obligation;
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

    public String getObligation() {
        return obligation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Billings other = (Billings) obj;
        return balance == other.balance &&
                creditLimit == other.creditLimit &&
                Objects.equals(name, other.name) &&
                Objects.equals(typeBillings, other.typeBillings) &&
                Objects.equals(typeCurrency, other.typeCurrency) &&
                Objects.equals(obligation, other.obligation) &&
                Objects.equals(id, other.id);
    }

}
