package com.example.monefy.Manager.billings;

import com.example.monefy.basic.functionality.model.billings.Billings;

import java.util.List;

public interface OnBillingsCallback {
    void onBillingsDataReceived(List<Billings> billingsList);

    void onBillingsDataNotFound();

    void onBillingsDataError(Exception e);
}
