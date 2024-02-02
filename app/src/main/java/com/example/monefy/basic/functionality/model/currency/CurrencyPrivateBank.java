package com.example.monefy.basic.functionality.model.currency;

import java.io.Serializable;

public class CurrencyPrivateBank implements Serializable{

    private String ccy;
    private String base_ccy;
    private double buy;
    private double sale;

    public CurrencyPrivateBank(String ccy, String base_ccy, double buy, double sale) {
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }

    public String getCcy() {
        return ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public double getBuy() {
        return buy;
    }

    public double getSalle() {
        return sale;
    }

}
