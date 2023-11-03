package com.example.monefy.basic.functionality.model;

public enum Obligation {
    CREDIT_LIMIT("кредитний ліміт"),
    DEBT_TO_ME("мені винні"),
    DEBT_TO_ANOTHER("я винен"),
    GOAL("мета"),
    ;

    private String title;

    Obligation(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
