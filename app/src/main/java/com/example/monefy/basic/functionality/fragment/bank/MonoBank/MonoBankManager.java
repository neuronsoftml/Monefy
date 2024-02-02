package com.example.monefy.basic.functionality.fragment.bank.MonoBank;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

import com.example.monefy.basic.functionality.fragment.bank.CallbackBank;
import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MonoBankManager {

    private static List<CurrencyMonoBank> currencyMonoBanksRates = new ArrayList<>();

    public static void currencyParse(CallbackBank callbackBank){
        MonoBankApiClient monoBankApiClient = new MonoBankApiClient();
        Call<List<CurrencyMonoBank>> call = monoBankApiClient.getCurrency();

        call.enqueue(new retrofit2.Callback<List<CurrencyMonoBank>>() {
            @Override
            public void onResponse(Call<List<CurrencyMonoBank>> call, Response<List<CurrencyMonoBank>> response) {
                if(response.isSuccessful()){
                    currencyMonoBanksRates = response.body();
                    searchDeleteDuplicates();
                }
                callbackBank.onResponse();
            }

            @Override
            public void onFailure(Call<List<CurrencyMonoBank>> call, Throwable t) {
                callbackBank.onFailure();
            }
        });
    }

    public static List<CurrencyMonoBank> getCurrencyRates() {
        return currencyMonoBanksRates;
    }

    /** Цей метот дозволяє видалити помилкові обєкти які видає Monobank;
     *  Вони дублюється, та мають нижче значення від реального курса.
     */
    private static void searchDeleteDuplicates() {
        ListIterator<CurrencyMonoBank> listIterator = currencyMonoBanksRates.listIterator();
        Set<Integer> seenCurrencyCodes = new CopyOnWriteArraySet<>();

        while (listIterator.hasNext()) {
            CurrencyMonoBank basisElement = listIterator.next();

            if (seenCurrencyCodes.contains(basisElement.getCurrencyCodeA())) {
                // Remove the duplicate element
                listIterator.remove();
            } else {
                seenCurrencyCodes.add(basisElement.getCurrencyCodeA());
            }
        }
    }
}
