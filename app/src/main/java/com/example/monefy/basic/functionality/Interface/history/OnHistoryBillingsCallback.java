package com.example.monefy.basic.functionality.Interface.history;

import com.example.monefy.basic.functionality.model.history.HistoryBilling;

import java.util.List;

public interface OnHistoryBillingsCallback {
    void onHistoryBillingsReceived(List<HistoryBilling> historyBillingList);
    void onDataNotFound();
}
