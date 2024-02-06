package com.example.monefy.basic.functionality.model.billings;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Debt extends Billings implements Serializable {
    private String debtor; //Хто заповязаний (Винні мені / чи я Винен).
    private Date returnDate; //Дата повернення.

    public Debt() {
    }

    public Debt(double balance, String name, String typeBillings, String typeCurrency, String debtor, Date returnDate, Date dateReceived) {
        super(balance, 0, name, typeBillings, typeCurrency, dateReceived);
        this.debtor = debtor;
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Debt other = (Debt) obj;
        return getBalance() == other.getBalance() &&
                Objects.equals(getName(), other.getName()) &&
                Objects.equals(getTypeCurrency(), other.getTypeCurrency()) &&
                Objects.equals(debtor, other.getDebtor()) &&
                Objects.equals(getId(), other.getId()) &&
                getReturnDate().equals(other.getReturnDate());
    }


    public Map<String,Object> getCreateMap(){
        Map<String,Object> billingData = new HashMap<>();
        billingData.put("balance", getBalance());
        billingData.put("name", getName());
        billingData.put("typeBillings",getTypeBillings());
        billingData.put("typeCurrency", getTypeCurrency());
        billingData.put("dateReceived", getDateReceived());
        billingData.put("debtor", getDebtor());
        billingData.put("returnDate", getReturnDate());
        return billingData;
    }

    public String getDebtor() {
        return debtor;
    }

    public Date getReturnDate() {
        return returnDate;
    }



}
