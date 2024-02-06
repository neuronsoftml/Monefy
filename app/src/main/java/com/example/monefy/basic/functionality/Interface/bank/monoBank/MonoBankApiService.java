package com.example.monefy.basic.functionality.Interface.bank.monoBank;

import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MonoBankApiService {
    @GET("/bank/currency")
    Call<List<CurrencyMonoBank>> getCurrencyRates();
}
