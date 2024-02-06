package com.example.monefy.basic.functionality.Interface.billings;

import com.example.monefy.basic.functionality.Interface.OnDataCallback;
import com.example.monefy.basic.functionality.model.billings.Billings;

import java.util.List;

public interface OnLoadBillingsCallback extends OnDataCallback {
    void onBillingsDataReceived(List<Billings> billingsList);
}
