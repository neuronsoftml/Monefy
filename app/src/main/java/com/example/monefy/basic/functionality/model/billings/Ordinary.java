package com.example.monefy.basic.functionality.model.billings;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ordinary extends Billings implements Serializable {

    public Ordinary() {
    }

    public Ordinary(long balance, long creditLimit, String name, String typeBillings, String typeCurrency, Date dateReceived) {
        super(balance, creditLimit, name, typeBillings, typeCurrency,dateReceived);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Ordinary other = (Ordinary) obj;
        return getBalance() == other.getBalance() &&
                getCreditLimit() == other.getCreditLimit() &&
                Objects.equals(getName(), other.getName()) &&
                Objects.equals(getTypeCurrency(), other.getTypeCurrency()) &&
                Objects.equals(getId(), other.getId());
    }

    public Map<String,Object> getCreateMap(){
        Map<String,Object> billingData = new HashMap<>();
        billingData.put("balance", getBalance());
        billingData.put("creditLimit", getCreditLimit());
        billingData.put("name", getName());
        billingData.put("typeBillings", getTypeBillings());
        billingData.put("typeCurrency", getTypeCurrency());
        billingData.put("dateReceived", getDateReceived());
        return billingData;
    }
}
