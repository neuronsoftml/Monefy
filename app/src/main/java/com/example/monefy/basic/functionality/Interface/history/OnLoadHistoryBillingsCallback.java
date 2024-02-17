package com.example.monefy.basic.functionality.Interface.history;

import com.example.monefy.basic.functionality.Interface.OnDataCallback;
import com.example.monefy.basic.functionality.model.history.HistoryBilling;

import java.util.List;

public interface OnLoadHistoryBillingsCallback extends OnDataCallback {
    void onHistoryBillingsReceived(List<HistoryBilling> historyBillingsList);
}
