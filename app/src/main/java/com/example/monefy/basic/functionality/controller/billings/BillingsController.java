package com.example.monefy.basic.functionality.controller.billings;

import android.util.Log;

import com.example.monefy.basic.functionality.Interface.billings.OnBillingsCallback;
import com.example.monefy.basic.functionality.Interface.billings.OnLoadBillingsCallback;
import com.example.monefy.basic.functionality.Interface.billings.OnSizeBillingsCallback;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BillingsController {

    /**
     * @return Повертає весь список рахунків які є в Firebase.
     */
    public static void getBillingsList(OnBillingsCallback onBillingsCallback) {
        loadBillings(new OnBillingsCallback() {
            @Override
            public void onBillingsDataReceived(List<Billings> billingsList) {
                onBillingsCallback.onBillingsDataReceived(billingsList);
            }

            @Override
            public void onDataNotFound() {
                onBillingsCallback.onDataNotFound();
            }
        });
    }

    /**
     * Дістає усі рахунки окрім одного
     * @param bill передаємо параметр рахунок який необхідно видалити з списку.
     * @return повертає спицільальний список рахунків.
     */
    public static void getBillingsAllExceptOne(Billings bill, OnBillingsCallback onBillingsCallback){
         getBillingsList(new OnBillingsCallback() {
             @Override
             public void onBillingsDataReceived(List<Billings> billingsList) {
                 billingsList.remove(bill);
                 onBillingsCallback.onBillingsDataReceived(billingsList);
             }

             @Override
             public void onDataNotFound() {
                onBillingsCallback.onDataNotFound();
             }
         });
    }

    /**
     * loadBillings - Здійснює загрузку рахунків.
     */
    private static void loadBillings(OnBillingsCallback onBillingsCallback){
        FirebaseController firebaseController = FirebaseController.getFirebaseManager();
        firebaseController.getBillingsData(new OnLoadBillingsCallback() {

            @Override
            public void onBillingsDataReceived(List<Billings> billings) {
                onBillingsCallback.onBillingsDataReceived(billings);
            }

            @Override
            public void onDataNotFound() {
                onBillingsCallback.onDataNotFound();
            }

            @Override
            public void onDataError(Exception e) {
                Log.e("loadBillings", String.valueOf(e));
            }

        });
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

    public static void getBillingsSize(OnSizeBillingsCallback onSizeBillingsCallback){
        getBillingsList(new OnBillingsCallback() {
            @Override
            public void onBillingsDataReceived(List<Billings> billingsList) {
                onSizeBillingsCallback.OnSize(billingsList.size());
            }

            @Override
            public void onDataNotFound() {
                onSizeBillingsCallback.OnSize(0);
            }
        });
    }

}
