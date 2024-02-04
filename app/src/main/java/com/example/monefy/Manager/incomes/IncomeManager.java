package com.example.monefy.Manager.incomes;

import android.util.Log;

import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeManager {
    private static IncomeManager incomeManager;

    public static IncomeManager getIncomeManager(){
        if(incomeManager == null){
            incomeManager = new IncomeManager();
        }
        return incomeManager;
    }

    public IncomeManager(){}

    private static List<Income> incomeList = new ArrayList<>();

    public List<Income> getIncomeList(){
        return incomeList;
    }

    public void loadIncomes(DataLoadListener dataLoadListener){
        FirebaseManager firebaseManager = FirebaseManager.getFirebaseManager();
        firebaseManager.getIncomesData(new OnIncomesCallback() {
            @Override
            public void onIncomesDataReceived(List<Income> incomesList) {
                updateIncome(incomesList);
                dataLoadListener.onDataLoaded();
            }

            @Override
            public void onDataNotFound() {
                Log.d("error","Відсутні дані");;
            }

            @Override
            public void onDataError(Exception e) {
                Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
            }
        });
    }

    private void updateIncome(List<Income> incomes){
        incomeList.clear();
        incomeList.addAll(incomes);
    }

    public static int getIncomeSize(){
        return incomeList.size();
    }
}
