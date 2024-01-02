package com.example.monefy.basic.functionality.model.income;

import android.util.Log;

import com.example.monefy.R;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
        if(category.equals(TypeIncomes.BUSINESS.getTitle())){
            return R.drawable.baseline_business_center_24;
        }else if(category.equals(TypeIncomes.FREELANCE.getTitle())){
            return R.drawable.baseline_handshake_24;
        }else if(category.equals(TypeIncomes.INVESTMENTS.getTitle())){
            return R.drawable.baseline_equalizer_24;
        }else if (category.equals(TypeIncomes.PENSIONS.getTitle())) {
            return R.drawable.baseline_assist_walker_24;
        }else if (category.equals(TypeIncomes.REAL_ESTATE.getTitle())) {
            return R.drawable.baseline_home_work_24;
        }else if (category.equals(TypeIncomes.SALARY.getTitle())) {
            return R.drawable.baseline_account_balance_wallet_24;
        } else if (category.equals(TypeIncomes.SUBSIDIES.getTitle())) {
            return R.drawable.baseline_volunteer_activism_24;
        }
        return 0;
    }

    private final SimpleDateFormat SDF = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

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

    public int getMaxProgress(){
        if(frequency.equals(TypeFrequency.DAILY.getTitle())){
            return 1;
        } else if (frequency.equals(TypeFrequency.MONTHKY.getTitle())) {
            Calendar calendar = Calendar.getInstance(); // Отримуємо поточну дату
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // Отримуємо кількість днів у місяці
        }
        return 0;
    }

    public int getProgress(){
        try {
            Date targetDate = SDF.parse(getConvertData());
            long remainingDays = (targetDate.getTime() - System.currentTimeMillis()) / (24 * 60 * 60 * 1000);

            return getMaxProgress() - (int) remainingDays;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
 }
