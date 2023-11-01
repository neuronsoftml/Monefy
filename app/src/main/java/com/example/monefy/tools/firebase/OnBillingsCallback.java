package com.example.monefy.tools.firebase;

import com.example.monefy.basic.functionality.model.Billings;

import java.util.List;

public interface OnBillingsCallback {
    void onBillingsDataReceived(List<Billings> billingsList);

    void onBillingsDataNotFound();

    void onBillingsDataError(Exception e);
}
