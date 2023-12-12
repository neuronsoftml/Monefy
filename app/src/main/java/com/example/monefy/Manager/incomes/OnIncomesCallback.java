package com.example.monefy.Manager.incomes;

import com.example.monefy.basic.functionality.model.income.Income;

import java.util.List;

public interface OnIncomesCallback {
    void onBillingsDataReceived(List<Income> incomesList);

    void onBillingsDataNotFound();

    void onBillingsDataError(Exception e);
}
