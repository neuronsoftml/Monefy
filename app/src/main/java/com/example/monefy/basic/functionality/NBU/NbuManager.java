package com.example.monefy.basic.functionality.NBU;

import com.example.monefy.basic.functionality.model.CurrencyNbu;
import com.example.monefy.basic.functionality.model.TypeCurrency;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NbuManager {

    private static List<CurrencyNbu> currencyNbuRates;

    public static void currencyNbuParse(CallbackNbu callbackNbu){
        NbuApiClient nbuApiClient = new NbuApiClient();
        Call<List<CurrencyNbu>> call = nbuApiClient.getCurrencyRates();
        call.enqueue(new Callback<List<CurrencyNbu>>() {
            @Override
            public void onResponse(Call<List<CurrencyNbu>> call, Response<List<CurrencyNbu>> response) {
                if (response.isSuccessful()) {
                    currencyNbuRates = response.body();
                    callbackNbu.onResponse();
                } else {
                    callbackNbu.onResponse();
                }
            }

            @Override
            public void onFailure(Call<List<CurrencyNbu>> call, Throwable t) {
               callbackNbu.onFailure();
            }
        });
    }

    public static List<CurrencyNbu> getCurrencyRates() {
        return currencyNbuRates;
    }

    public static double currencyConversionAtTheExchangeRate(double sum, String typeCurrency){
        for(CurrencyNbu currency : currencyNbuRates){
            if(typeCurrency.equals(currency.getCc())){
                return currency.getRate() * sum;
            }
            else if (typeCurrency.equals(TypeCurrency.UAH.getTitle())){
                return sum / currency.getRate();
            }
        }
        return 0;
    }
}
