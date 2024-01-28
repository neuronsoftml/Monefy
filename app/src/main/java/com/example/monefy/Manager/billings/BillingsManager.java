package com.example.monefy.Manager.billings;

import android.util.Log;

import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.billings.OnBillingsCallback;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BillingsManager {

    private static BillingsManager billingsManager;

    public static BillingsManager getBillingsManager(){
        if(billingsManager == null){
            billingsManager = new BillingsManager();
        }
        return billingsManager;
    }

    public BillingsManager() {}

    /** Список рахунків.*/
    private static List<Billings> billingsList = new ArrayList<>();

    /**
     * @return Повертає список рахунків.
     */
    public List<Billings> getBillingsList() {
        return billingsList;
    }

    /**
     * Дістає усі рахунки окрім одного
     * @param bill передаємо параметр рахунок який необхідно видалити з списку.
     * @return повертає спицільальний список рахунків.
     */
    public List<Billings> getBillingsAllExceptOne(Billings bill){
        List<Billings> billingsAllExceptOne = billingsList;
        billingsAllExceptOne.remove(bill);
        return billingsAllExceptOne;
    }

    /**
     * loadBillings - Здійснює загрузку рахунків.
     */
    public void loadBillings(DataLoadListener dataLoadListener){
        FirebaseManager.getBillingsData(new OnBillingsCallback() {
            @Override
            public void onDataNotFound() {
                Log.d("error","Відсутні дані");;
            }

            @Override
            public void onDataError(Exception e) {
                Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
            }

            @Override
            public void onBillingsDataReceived(List<Billings> billings) {
                updateBillings(billings);
                dataLoadListener.onDataLoaded();
            }
        });
    }

    /** Метод обноляє список рахунків.
     * @param billings передаємо як парамер новий список рахунків.
     */
    private void updateBillings(List<Billings> billings){
        billingsList.clear();
        billingsList.addAll(billings);
    }

    /**
     * sortingBillings - сортирує рахунки, повертає список рахунків  необхідного типу.
     * @param billingList - Список рахунків.
     * @param typeBillings - Список типів рахунки.
     * @return повертає список рахунків Billings
     */
    public static List<Billings> sortingBillings(List<Billings> billingList, List<TypeBillings> typeBillings){
        List<Billings> billings = new ArrayList<>();
        for(Billings bill : billingList){
            for(TypeBillings typeBill : typeBillings){
                if(bill.getTypeBillings().equals(typeBill.getTitle())){
                    billings.add(bill);
                    break;
                }
            }
        }
        return billings;
    }

    /**
     * sortingBillings - сортирує рахунки, повертає список рахунків  необхідного типу.
     * @param billingList - Список рахунків.
     * @param typeBillings - Тип рахунку.
     * @return повертає список вісортированих рахунків Billings по типу
     */
    public static List<Billings> sortingBillings(List<Billings> billingList, TypeBillings typeBillings){
        List<Billings> billings = new ArrayList<>();
        for(Billings bill : billingList){
            if(bill.getTypeBillings().equals(typeBillings.getTitle())){
                billings.add(bill);
            }
        }
        return billings;
    }


    /**
     * Цей метод отримує дані з Billingsоб’єкта та повертає їх як Map<String, Object>.
     * Він робить це шляхом динамічного виклику getCreateMap()методу відповідного підкласу Billings.
     * @param billing об’єкт, з якого потрібно отримати дані.
     * @return Map<String, Object>що містить отримані дані.
     * @throws InvocationTargetException Якщо під час виклику методу виникає виняток.
     * @throws IllegalAccessException  якщо метод недоступний.
     * @throws NoSuchMethodException якщо метод не існує.
     */
    public static Map<String, Object> getBillingMapData(Billings billing) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // Get the type of the `billing` object
        Class<?> billingType = billing.getClass();

        // Get the appropriate `getCreateMap()` method
        Method getCreateMapMethod = billingType.getDeclaredMethod("getCreateMap");

        // Call the `getCreateMap()` method
        Object result = getCreateMapMethod.invoke(billing);

        return (Map<String, Object>) result;
    }


    public static int getBillingsSize(){
        return billingsList.size();
    }

}
