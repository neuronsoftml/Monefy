package com.example.monefy.basic.functionality.controller.billings;

import android.util.Log;

import com.example.monefy.basic.functionality.Interface.billings.OnBillingCallback;
import com.example.monefy.basic.functionality.Interface.billings.OnBillingsCallback;
import com.example.monefy.basic.functionality.Interface.billings.OnLoadBillingsCallback;
import com.example.monefy.basic.functionality.Interface.billings.OnSizeBillingsCallback;
import com.example.monefy.basic.functionality.Interface.dialogModal.BillingDialogCallback;
import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Цей класс дає можливість здійснювати комунікацію та управління обєктами рахунків.*/
public class BillingsController {

    /** Link на firebaseController що дозволяє використовувати різноманітні методи взаємодії з базу данних FireBase */
    private static FirebaseController firebaseController = FirebaseController.getFirebaseManager();

    /**
     * loadBillings - Здійснює загрузку рахунків з бази Firebase.
     */
    private static void loadBillings(final OnBillingsCallback onBillingsCallback){
        firebaseController.getBillingsAll(new OnLoadBillingsCallback() {

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
     * @return Повертає весь список рахунків які є в бази Firebase.
     */
    public static void getAllBillings(final OnBillingsCallback onBillingsCallback) {
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
    public static void getBillingsAllExceptOne(Billings bill, final OnBillingsCallback onBillingsCallback){
         getAllBillings(new OnBillingsCallback() {
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

    /** Цей метод дістає з бази даних один рахунок за параметром його UID
     * @param BillingUID id - рахунка
     * @param onBillingCallback - інтерфейс через якого повернемо рахунок який дістали.
     */
    public static void getBillingExceptOne(String BillingUID, final OnBillingCallback onBillingCallback){
        firebaseController.getBillingsByUID(BillingUID, new OnBillingCallback() {
            @Override
            public void onBillingsDataReceived(Billings billing) {
                onBillingCallback.onBillingsDataReceived(billing);
            }

            @Override
            public void onDataNotFound() {
                onBillingCallback.onDataNotFound();
            }
        });
    }

    /**
     * sortingBillings - сортирує рахунки, повертає список рахунків  необхідного типу.
     * @param billingList - Список рахунків.
     * @param typeBillings - Список типів рахунки.
     * @return повертає сортирований список рахунків "Billings".
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
     * Цей метод отримує дані з Billings об’єкта та повертає їх як Map<String, Object>.
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

    /** Цей метод дістає кількість рахунків
     * @param onSizeBillingsCallback повертає кількість рахунків через інтерфейс.
     */
    public static void getBillingsSize(final OnSizeBillingsCallback onSizeBillingsCallback){
        getAllBillings(new OnBillingsCallback() {
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

    /** Цей метод вносить зміни у вибраний рахунок.
     * @param billing - Рахунок який потрібно внести зміни.
     * @param editBilling - Рахунок з внесенеми змінами.
     * @param callback - повертає відповідь про виконання
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public static void editBilling(Billings billing, Billings editBilling, final InConclusionCompleteListener callback) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(editBilling!= null){
            editBilling.setId(billing.getId());
            if(!editBilling.equals(billing)){
                firebaseController.updatedBillings(
                        billing.getId(),
                        getBillingMapData(editBilling),
                        new InConclusionCompleteListener() {
                            @Override
                            public void onSuccess() {
                                callback.onSuccess();
                            }

                            @Override
                            public void onFailure(Exception exception) throws Exception {
                                callback.onFailure(exception);
                            }
                        }

                );
            }
        }
    }

    /** Цей метод видаляє з бази даних переданий рахунок.
     * @param billing - Рахунок який потрібно видали.
     * @param callback - Повертає відповідь про виконання
     */
    public static void deleteBilling(Billings billing, final InConclusionCompleteListener callback){
        firebaseController.deleteBillings(
                billing.getId(),
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess();
                    }

                    @Override
                    public void onFailure(Exception exception) throws Exception {
                        callback.onFailure(exception);
                    }
                });
    }
}
