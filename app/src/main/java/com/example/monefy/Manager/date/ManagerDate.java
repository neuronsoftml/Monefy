package com.example.monefy.Manager.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManagerDate {
    public static final SimpleDateFormat dateFormatLocalApp = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat dateFormatFirebase = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH);

    /**
     * Цей метод повертає теперишню дату у String.
     * @return Формат дати  "EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH
     */
    public static String getCurrentDateStringFormatFireBase(){
        Date currentDate = new Date();
        return dateFormatFirebase.format(currentDate);
    }

    /**
     * Цей метод повертає теперишню дату у String.
     * @return Формат дати  dd.MM.yyyy Locale.ENGLISH
     */
    public static String getCurrentDateStringLocalApp(){
        Date currentDate = new Date();
        return dateFormatLocalApp.format(currentDate);
    }

    /**
     * Цей метод повертає теперишню дату у тип даних Date.
     * @return "EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH
     */
    public static Date getCurrentDateFormatFirebase() {
        Date currentDate = new Date();
        try {
            // Форматуємо поточну дату у рядок
            String dateString = dateFormatFirebase.format(currentDate);

            // Перетворюємо рядок в Date
            return dateFormatFirebase.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); //
            return null;
        }
    }

    public static Date convertStringToDate(String dateString, SimpleDateFormat dateFormatFirebase) {
        try {
            // Перетворення рядка у тип Date за допомогою SimpleDateFormat
            return dateFormatFirebase.parse(dateString);
        } catch (ParseException e) {
            // Обробка можливих помилок парсингу
            e.printStackTrace(); // або можна кинути виняток в вашому коді
            return null;
        }
    }

    /**
     * Цей метод приймає дату формату Firebase, конвертує дату у формат локального використання.
     * @param firebaseDateString "EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH
     * @return повертає дату формата localDate
     */
    public static String convertFirebaseDateToLocalDate(String firebaseDateString) {
        try {
            // Перетворення рядка з формату Firebase у тип Date
            Date firebaseDate = dateFormatFirebase.parse(firebaseDateString);

            // Перетворення типу Date у рядок за допомогою SDF
            return dateFormatLocalApp.format(firebaseDate);
        } catch (ParseException e) {
            // Обробка можливих помилок парсингу
            e.printStackTrace(); // або можна кинути виняток в вашому коді
            return null;
        }
    }

    /**
     * Цей метод з типу данних Date повертає дату у тип данних String.
     * @param firebaseDate - Дата формата  "EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH
     * @return String
     */
    public static String convertFirebaseDateToString(Date firebaseDate) {
        try {
            // Перетворення типу Date у рядок за допомогою outputDateFormat
            return dateFormatFirebase.format(firebaseDate);
        } catch (Exception e) {
            // Обробка можливих помилок форматування
            e.printStackTrace(); // або можна кинути виняток в вашому коді
            return null;
        }
    }

}
