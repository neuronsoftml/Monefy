package com.example.monefy.basic.functionality.model.income;

import com.example.monefy.R;

import java.util.Date;

public class Income {
    private String id;
    private String name;            // Назва активу
    private double amount;          // Сума доходу
    private String typeCurrency;    // Тип валюти
    private String source;          // Джерело доходу
    private String frequency;       // Частота отримання (одноразово, щомісяця, щодня)
    private Date dateReceived;      // Дата отримання
    private String category;        // Категорія доходу (наприклад, зарплата, подарунок)
    private String notes;           // Додаткові нотатки

    public Income(){

    }
    public Income(String id, String name, double amount, String typeCurrency ,String source, String frequency, Date dateReceived, String category, String notes) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.typeCurrency = typeCurrency;
        this.source = source;
        this.frequency = frequency;
        this.dateReceived = dateReceived;
        this.category = category;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public String getFrequency() {
        return frequency;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public String getCategory() {
        return category;
    }

    public String getNotes() {
        return notes;
    }

    public String getTypeCurrency() {
        return typeCurrency;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageIncome(){
        if(category.equals(TypeCategory.BUSINESS.getTitle())){
            //Бізнес
        }else if(category.equals(TypeCategory.FREELANCE.getTitle())){
            //Фріланс
        }else if(category.equals(TypeCategory.INVESTMENTS.getTitle())){
            //Інвестиції
        }else if (category.equals(TypeCategory.PENSIONS.getTitle())) {
            //Пенція.
        }else if (category.equals(TypeCategory.REAL_ESTATE.getTitle())) {
            //Нерухомість.
        }else if (category.equals(TypeCategory.SALARY.getTitle())) {
            //Зарплата.
        } else if (category.equals(TypeCategory.SUBSIDIES.getTitle())) {
            //Супсидії.
        }
        return 0;
    }
 }
