package com.example.monefy.basic.functionality.controller.bank.privateBank;

import com.example.monefy.basic.functionality.Interface.bank.privateBank.PrivateBankApiService;
import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrivateBankApiClient {
    private static final String BASE_URL = "https://api.privatbank.ua";
    private Retrofit retrofit;
    private PrivateBankApiService apiService;

    public PrivateBankApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(PrivateBankApiService.class);
    }

    public Call<List<CurrencyPrivateBank>> getCurrency() {
        return apiService.getCurrencyRates();
    }
}
