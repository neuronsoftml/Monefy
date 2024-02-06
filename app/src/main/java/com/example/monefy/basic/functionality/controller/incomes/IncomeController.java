package com.example.monefy.basic.functionality.controller.incomes;

import android.util.Log;

import com.example.monefy.basic.functionality.Interface.incomes.OnIncomesCallback;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;
import com.example.monefy.basic.functionality.Interface.DataLoadListener;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeController {
    private static IncomeController incomeController;

    public static IncomeController getIncomeManager(){
        if(incomeController == null){
            incomeController = new IncomeController();
        }
        return incomeController;
    }

    public IncomeController(){}

    private static List<Income> incomeList = new ArrayList<>();

    public List<Income> getIncomeList(){
        return incomeList;
    }

    public void loadIncomes(DataLoadListener dataLoadListener){
        FirebaseController firebaseController = FirebaseController.getFirebaseManager();
        firebaseController.getIncomesData(new OnIncomesCallback() {
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
