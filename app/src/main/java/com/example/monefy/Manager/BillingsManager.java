package com.example.monefy.Manager;

import android.util.Log;

import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.firebase.OnBillingsCallback;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BillingsManager {

    private static BillingsManager billingsManager;

    public static BillingsManager getBillingsManager(){
        if(billingsManager == null){
            billingsManager = new BillingsManager();
        }
        return billingsManager;
    }

    public BillingsManager() {
    }

    private List<Billings> billingsList = new ArrayList<>();

    public List<Billings> getBillingsList() {
        return billingsList;
    }

    /**
     * loadBillings - Здійснює загрузку рахунків.
     * @return billingsList повертає список рахунків
     */
    public void loadBillings(DataLoadListener dataLoadListener){
        String userId = AuthenticationManager.getAuthenticationManager().getUserId();
        FirebaseManager.getBillingsData(
                FirebaseFirestore.getInstance(),
                userId,
                new OnBillingsCallback() {
                    @Override
                    public void onBillingsDataReceived(List<Billings> billings) {
                        updateBillings(billings);
                        dataLoadListener.onDataLoaded();
                    }

                    @Override
                    public void onBillingsDataNotFound() {
                        Log.d("error","Відсутні дані");;
                    }

                    @Override
                    public void onBillingsDataError(Exception e) {
                        Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
                    }
                }
        );
    }

    private void updateBillings(List<Billings> billings){
        billingsList.clear();
        billingsList.addAll(billings);
    }

    /**
     * sortingBillings - сортирує рахунки, повертає список рахунків  необхідного типу.
     * @param billingList - Список рахунків.
     * @param typeBillings - Список типів рахунки.
     * @return
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
     * @return
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

}
