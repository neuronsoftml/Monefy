package com.example.monefy.basic.functionality.fragment.bank.PrivateBank;

import com.example.monefy.basic.functionality.fragment.bank.CallbackBank;
import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PrivateBankManager {

    private static List<CurrencyPrivateBank> currencyPrivateBankRates;

    public static void currencyParse(CallbackBank callback){
        PrivateBankApiClient privateBankApiClient = new PrivateBankApiClient();
        Call<List<CurrencyPrivateBank>> call = privateBankApiClient.getCurrency();
        call.enqueue(new retrofit2.Callback<List<CurrencyPrivateBank>>() {
            @Override
            public void onResponse(Call<List<CurrencyPrivateBank>> call, Response<List<CurrencyPrivateBank>> response) {
                if (response.isSuccessful()) {
                    currencyPrivateBankRates = response.body();
                }
                callback.onResponse();
            }

            @Override
            public void onFailure(Call<List<CurrencyPrivateBank>> call, Throwable t) {
               callback.onFailure();
            }
        });
    }

    public static List<CurrencyPrivateBank> getCurrencyRates() {
        return currencyPrivateBankRates;
    }

    /*
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
     */
}
