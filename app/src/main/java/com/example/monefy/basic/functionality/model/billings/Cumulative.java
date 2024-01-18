package com.example.monefy.basic.functionality.model.billings;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cumulative extends Billings implements Serializable {
    private long goal; // Мета.

    public Cumulative() {
    }

    public Cumulative(long balance, String name, String typeBillings, String typeCurrency, long goal, Date dateReceived) {
        super(balance, 0, name, typeBillings, typeCurrency, dateReceived);
        this.goal = goal;
    }

    public long getGoal() {
        return goal;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cumulative other = (Cumulative) obj;
        return getBalance() == other.getBalance() &&
                getCreditLimit() == other.getCreditLimit() &&
                Objects.equals(getName(), other.getName()) &&
                Objects.equals(getTypeCurrency(), other.getTypeCurrency()) &&
                Objects.equals(getGoal(), other.getGoal()) &&
                Objects.equals(getId(), other.getId());
    }

    public Map<String,Object> getCreateMap(){
        Map<String,Object> billingData = new HashMap<>();
        billingData.put("balance", getBalance());
        billingData.put("goal", getGoal());
        billingData.put("name", getName());
        billingData.put("typeBillings", getTypeBillings());
        billingData.put("typeCurrency", getTypeCurrency());
        billingData.put("dateReceived", getDateReceived());
        return billingData;
    }

    public void setGoal(long goal) {
        this.goal = goal;
    }
}
