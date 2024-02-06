package com.example.monefy.basic.functionality.fragment.bank.MonoBank;

import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;

import java.util.List;

public interface CallbackMonoBank {
    void onResponse(List<CurrencyMonoBank> currencyMonoBankList);
    void onFailure();
}
