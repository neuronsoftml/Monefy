package com.example.monefy.basic.functionality.model;

public enum TypeBillings {
    ORDINARY("Звичайний"),
    DEBT("Борговий"),
    CUMULATIVE("Накопичувальний")
    ;
    private String title;

    TypeBillings(String title) {
        this.title = title;
    }

    public String getTypeBillingsTitle() {
        return title;
    }

    public TypeBillings[] getAllTypeBillings(){
        return TypeBillings.values();
    }
}
