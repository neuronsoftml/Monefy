package com.example.monefy.basic.functionality.model.billings;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public abstract class Billings {
    private long balance; //Баланс рахунка
    private long creditLimit; //Кредитний ліміт
    private String name; //Назва рахунка
    private String typeBillings; //Тип рахунка
    private String typeCurrency; //Тип Валюти
    private String id; //Id Рахунка
    private Date dateReceived; //Дата створення

    public Billings(){

    }

    public Billings(long balance, long creditLimit, String name,String typeBillings, String typeCurrency, Date dateReceived){
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.name = name;
        this.typeBillings = typeBillings;
        this.typeCurrency = typeCurrency;
        this.dateReceived = dateReceived;
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

    private final SimpleDateFormat SDF = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

    public Date getDateReceived() {
        return dateReceived;
    }

    public String getConvertData() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH);

        try {
            Date date = inputFormat.parse(String.valueOf(dateReceived));
            return SDF.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("DateConversionError", "Error parsing the date: " + e.getMessage());
            return null;
        }
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

    public void setBalance(long balance) {
        this.balance = balance;
    }



}
