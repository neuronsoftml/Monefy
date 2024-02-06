package com.example.monefy.basic.functionality.controller.bank.monoBank;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

import com.example.monefy.basic.functionality.Interface.bank.monoBank.CallbackMonoBank;
import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;

import java.util.ArrayList;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MonoBankController {
    private static MonoBankController monoBankController;

    /** Список валют з курсом.*/
    private static List<CurrencyMonoBank> currencyMonoBanksRates = new ArrayList<>();

    /** Цей метод створює Singleton для MonoBankManager
     * @return повертає силку на обєкт
     */
    public static MonoBankController getMonoBankManager() {
        if(monoBankController == null){
            monoBankController = new MonoBankController();
        }
        return monoBankController;
    }

    /** Цей метод здійснює парсинг Json файла курса валют. */
    private static void currencyParse(CallbackMonoBank callbackMonoBank){
        if(currencyMonoBanksRates.isEmpty()){
            MonoBankApiClient monoBankApiClient = new MonoBankApiClient();
            Call<List<CurrencyMonoBank>> call = monoBankApiClient.getCurrency();

            call.enqueue(new retrofit2.Callback<List<CurrencyMonoBank>>() {
                @Override
                public void onResponse(Call<List<CurrencyMonoBank>> call, Response<List<CurrencyMonoBank>> response) {
                    if(response.isSuccessful()){
                        currencyMonoBanksRates = response.body();
                        searchDeleteDuplicates();
                        callbackMonoBank.onResponse(currencyMonoBanksRates);
                    }
                }

                @Override
                public void onFailure(Call<List<CurrencyMonoBank>> call, Throwable t) {
                    Log.e("MonoBank","ПОМИЛКА ЗЄДНАННЯ МоноБанк");
                    callbackMonoBank.onFailure();
                }
            });
        }
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

    /** Цей метод дозволяє конвертувати певну суму, з іноземної валюти у грн, відповідно до курку валют MonoBank*/
    public double currencyConversionAtTheExchangeRate(Double suma, String typeCurrency) {
       for (CurrencyMonoBank currency : currencyMonoBanksRates) {
           String CCY = TypeCurrency.searchCurrencyCcy(currency.getCurrencyCodeA());
           if (CCY != null) {
               if (CCY.equals(typeCurrency)) {
                   return suma * currency.getSell();
               }
           }
       }
       return 0;
   }

    /** Цей метод повертає список курса валют, при тому загрузку з серверів здійснює один раз за сесію додатка.
     * * @param callbackMonoBank
     */
    public void getCurrencyMonoBanksRates(CallbackMonoBank callbackMonoBank) {
        if(currencyMonoBanksRates.isEmpty()){
            currencyParse(callbackMonoBank);
        }else {
            callbackMonoBank.onResponse(currencyMonoBanksRates);
        }
    }
}
