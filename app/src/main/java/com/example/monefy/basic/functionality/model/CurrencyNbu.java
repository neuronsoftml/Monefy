package com.example.monefy.basic.functionality.model;

import java.io.Serializable;

public class CurrencyNbu implements Serializable{
    private int r030;
    private String txt;
    private double rate;
    private String cc;
    private String exchangedate;

    public CurrencyNbu(int r030, String txt, double rate, String cc, String exchangedate) {
        this.r030 = r030;
        this.txt = txt;
        this.rate = rate;
        this.cc = cc;
        this.exchangedate = exchangedate;
    }

    public int getR030() {
        return r030;
    }

    public String getTxt() {
        return txt;
    }

    public double getRate() {
        return rate;
    }

    public String getCc() {
        return cc;
    }

    public String getExchangedate() {
        return exchangedate;
    }
}
