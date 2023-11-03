package com.example.monefy.basic.functionality.NBU;

import com.example.monefy.basic.functionality.model.CurrencyNbu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NbuApiService {
    @GET("/NBUStatService/v1/statdirectory/exchange?json")
    Call<List<CurrencyNbu>> getCurrencyRates();
}
