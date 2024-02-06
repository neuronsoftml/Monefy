package com.example.monefy.basic.functionality.fragment.bank.PrivateBank;

import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;

import java.util.List;

public interface CallbackPrivateBank {
    void onResponse(List<CurrencyPrivateBank> currencyPrivateBankListCallback);
    void onFailure();
}
