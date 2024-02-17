package com.example.monefy.basic.functionality.controller.bank.monoBank;

import com.example.monefy.basic.functionality.Interface.bank.monoBank.MonoBankApiService;
import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonoBankApiClient {
    private static final String BASE_URL ="https://api.monobank.ua";
    private Retrofit retrofit;
    private final MonoBankApiService apiService;

    /** Цей метод створює зєднання по силці до API lick курса валют monoBank.*/
    public MonoBankApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(MonoBankApiService.class);
    }

    /** Цей метод повертає колекцію списків курса валют.
     * @return
     */
    public Call<List<CurrencyMonoBank>> getCurrency(){
        return apiService.getCurrencyRates();
    }
}
