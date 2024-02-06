package com.example.monefy.basic.functionality.model.billings;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HistoryBilling{
    private String id; // Id історії
    private String UIDBilling; //UID обєкта з якого здійснили перевод.
    private String UIDObjectTransfer; //UID обєкта на який здійснили перевод.
    private Date dateReceived; // Дата створення.
    private String typeCurrency; //Тип Валюти.
    private String typeTransfer; // Тип переводу.
    private double suma; // Cума переводу.

    public HistoryBilling(String UIDBilling, String UIDObjectTransfer, Date dateReceived, String typeCurrency, String typeTransfer, double suma) {
        this.UIDBilling = UIDBilling;
        this.UIDObjectTransfer = UIDObjectTransfer;
        this.dateReceived = dateReceived;
        this.typeCurrency = typeCurrency;
        this.typeTransfer = typeTransfer;
        this.suma = suma;
    }

    public Map<String, Object> getCreateMap(){
        Map<String, Object> historyBilling = new HashMap<>();
        historyBilling.put("UIDBilling", getUIDBilling());
        historyBilling.put("UIDObjectTransfer", getUIDObjectTransfer());
        historyBilling.put("dateReceived",getDateReceived());
        historyBilling.put("typeCurrency", getTypeCurrency());
        historyBilling.put("typeTransfer", getTypeTransfer());
        historyBilling.put("suma", getSuma());
        return historyBilling;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUIDBilling(String UIDBilling) {
        this.UIDBilling = UIDBilling;
    }

    public void setUIDObjectTransfer(String UIDObjectTransfer) {
        this.UIDObjectTransfer = UIDObjectTransfer;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setTypeCurrency(String typeCurrency) {
        this.typeCurrency = typeCurrency;
    }

    public void setTypeTransfer(String typeTransfer) {
        this.typeTransfer = typeTransfer;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }


    public String getUIDBilling() {
        return UIDBilling;
    }

    public String getUIDObjectTransfer() {
        return UIDObjectTransfer;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public String getTypeCurrency() {
        return typeCurrency;
    }

    public String getTypeTransfer() {
        return typeTransfer;
    }

    public double getSuma() {
        return suma;
    }

    public String getId() {
        return id;
    }
}
