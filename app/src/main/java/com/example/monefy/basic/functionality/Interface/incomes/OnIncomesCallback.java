package com.example.monefy.basic.functionality.Interface.incomes;

import com.example.monefy.basic.functionality.Interface.OnDataCallback;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.List;

public interface OnIncomesCallback extends OnDataCallback {
    void onIncomesDataReceived(List<Income> incomesList);
}
