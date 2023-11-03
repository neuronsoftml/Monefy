package com.example.monefy.basic.functionality.NBU;

import com.example.monefy.basic.functionality.model.CurrencyNbu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NbuApiClient {
    private static final String BASE_URL = "https://bank.gov.ua";
    private Retrofit retrofit;
    private NbuApiService apiService;

    public NbuApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(NbuApiService.class);
    }

    public Call<List<CurrencyNbu>> getCurrencyRates() {
        return apiService.getCurrencyRates();
    }
}
