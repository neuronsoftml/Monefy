package com.example.monefy.basic.functionality.Interface.billings;

import com.example.monefy.basic.functionality.model.billings.Billings;

import java.util.List;

public interface OnBillingsCallback {
    void onBillingsDataReceived(List<Billings> billingsList);
    void onDataNotFound();
}
