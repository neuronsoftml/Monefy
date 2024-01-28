package com.example.monefy.Manager.billings;

import com.example.monefy.Manager.OnDataCallback;
import com.example.monefy.basic.functionality.model.billings.Billings;

import java.util.List;

public interface OnBillingsCallback extends OnDataCallback {
    void onBillingsDataReceived(List<Billings> billingsList);
}
