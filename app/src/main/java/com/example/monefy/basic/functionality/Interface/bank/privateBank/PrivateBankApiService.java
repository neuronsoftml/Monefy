package com.example.monefy.basic.functionality.Interface.bank.privateBank;

import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PrivateBankApiService {
    @GET("/p24api/pubinfo?exchange&coursid=5")
    Call<List<CurrencyPrivateBank>> getCurrencyRates();
}

