package com.example.monefy.basic.functionality.fragment.bank.PrivateBank;

import android.util.Log;


import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PrivateBankManager {

    private static PrivateBankManager privateBankManager;

    /** Цей метод створює Singleton для PrivateBankManager
     * @return повертає силку на обєкт
     */
    public static PrivateBankManager getPrivateBankManager(){
        if(privateBankManager == null){
            privateBankManager = new PrivateBankManager();
        }
        return privateBankManager;
    }

    /** Список валют з курсом.*/
    private static List<CurrencyPrivateBank> currencyPrivateBankRates = new ArrayList<>();

    /** Цей метод здійснює парсинг Json файла курса валют. */
    private static void currencyParse(CallbackPrivateBank callbackPrivateBank){
        PrivateBankApiClient privateBankApiClient = new PrivateBankApiClient();
        Call<List<CurrencyPrivateBank>> call = privateBankApiClient.getCurrency();
        call.enqueue(new retrofit2.Callback<List<CurrencyPrivateBank>>() {
            @Override
            public void onResponse(Call<List<CurrencyPrivateBank>> call, Response<List<CurrencyPrivateBank>> response) {
                if (response.isSuccessful()) {
                    currencyPrivateBankRates = response.body();
                }
                callbackPrivateBank.onResponse(currencyPrivateBankRates);
            }

            @Override
            public void onFailure(Call<List<CurrencyPrivateBank>> call, Throwable t) {
               callbackPrivateBank.onFailure();
                Log.e("PrivetBank","ПОМИЛКА ЗЄДНАННЯ PrivateBank");
            }
        });
    }

    /** Цей метод повертає список курса валют, при тому загрузку з серверів здійснює один раз за сесію додатка.
     * * @param callbackPrivateBank
     */
    public void getCurrencyRates(CallbackPrivateBank callbackPrivateBank) {
        if(currencyPrivateBankRates.isEmpty()){
            currencyParse(callbackPrivateBank);
        }else {
            callbackPrivateBank.onResponse(currencyPrivateBankRates);
        }
    }

}
