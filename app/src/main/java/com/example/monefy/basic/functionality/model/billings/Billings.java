package com.example.monefy.basic.functionality.model.billings;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public abstract class Billings {
    private double balance; //Баланс рахунка
    private double creditLimit; //Кредитний ліміт
    private String name; //Назва рахунка
    private String typeBillings; //Тип рахунка
    private String typeCurrency; //Тип Валюти
    private String id; //Id Рахунка
    private Date dateReceived; //Дата створення

    public Billings(){

    }

    public Billings(double balance, double creditLimit, String name,String typeBillings, String typeCurrency, Date dateReceived){
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.name = name;
        this.typeBillings = typeBillings;
        this.typeCurrency = typeCurrency;
        this.dateReceived = dateReceived;
    }

    public double getBalance() {
        return balance;
    }

    public String getTypeBillings() {
        return typeBillings;
    }

    public String getTypeCurrency() {
        return typeCurrency;
    }

    public double getCreditLimit() {
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

    public Date getDateReceived() {
        return dateReceived;
    }

    public static Billings fromDocumentSnapshot(DocumentSnapshot document) {
        String type = document.getString("typeBillings");
        switch (type) {
            case "Звичайний":
                return document.toObject(Ordinary.class);
            case "Борговий":
                return document.toObject(Debt.class);
            case "Накопичувальний":
                return document.toObject(Cumulative.class);
            default:
                return null;
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
