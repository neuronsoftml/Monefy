package com.example.monefy.basic.functionality.fragment.bank.MonoBank;

import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonoBankApiClient {
    private static final String BASE_URL ="https://api.monobank.ua";
    private Retrofit retrofit;
    private final MonoBankApiService apiService;

    public MonoBankApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(MonoBankApiService.class);
    }

    public Call<List<CurrencyMonoBank>> getCurrency(){
        return apiService.getCurrencyRates();
    }
}
