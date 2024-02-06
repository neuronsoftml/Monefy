package com.example.monefy.basic.functionality.fragment.bank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.bank.monoBank.CallbackMonoBank;
import com.example.monefy.basic.functionality.controller.bank.monoBank.MonoBankController;
import com.example.monefy.basic.functionality.Interface.bank.privateBank.CallbackPrivateBank;
import com.example.monefy.basic.functionality.controller.bank.privateBank.PrivateBankManager;
import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;
import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;

import java.util.List;

public class CurrencyBankFragment extends Fragment {

    private TextView tVTitleUSD_type_private, tVTitleEUR_type_private;
    private TextView tVRateUSD_BAY_private, tVRateEUR_BAY_private;
    private TextView tVRateUSD_SALE_private, tVRateEUR_SALE_private;

    private TextView tVTitleUSD_type_monoBank, tVTitleEUR_type_monoBank;
    private TextView tVRateUSD_BAY_monoBank, tVRateEUR_BAY_monoBank;
    private TextView tVRateUSD_SALE_monoBank, tVRateEUR_SALE_monoBank;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency_bank, container, false);
        setupUIElements(view);
        loadCurrencyPrivateBank();
        loadCurrencyMonoBank();
        return view;
    }

    /** Цей метод здійснює ініціалізацію UI елементів які знаходяться у  Fragment
     * @param view  базовий клас для віджетів.
     */
    private void setupUIElements(View view){
        this.tVTitleUSD_type_private = view.findViewById(R.id.tVTitleUSD_PrivateBank);
        this.tVTitleEUR_type_private = view.findViewById(R.id.tVTitleEUR_PrivateBank);
        this.tVRateUSD_BAY_private = view.findViewById(R.id.tVBuyUSD_PrivateBank);
        this.tVRateEUR_BAY_private = view.findViewById(R.id.tvBuyEUR_PrivateBank);
        this.tVRateUSD_SALE_private = view.findViewById(R.id.tVSaleUSD_PrivateBank);
        this.tVRateEUR_SALE_private = view.findViewById(R.id.tvSaleEUR_PrivateBank);

        this.tVTitleUSD_type_monoBank = view.findViewById(R.id.tVTitleUSD_MonoBank);
        this.tVTitleEUR_type_monoBank = view.findViewById(R.id.tVTitleEUR_MonoBank);
        this.tVRateUSD_BAY_monoBank = view.findViewById(R.id.tVBuyUSD_MonoBank);
        this.tVRateEUR_BAY_monoBank = view.findViewById(R.id.tvBuyEUR_MonoBank);
        this.tVRateUSD_SALE_monoBank = view.findViewById(R.id.tVSaleUSD_MonoBank);
        this.tVRateEUR_SALE_monoBank = view.findViewById(R.id.tvSaleEUR_MonoBank);
    }

    /** Цей метод здійснює завантаження курса валют з Приват банк*/
    private void loadCurrencyPrivateBank() {
        PrivateBankManager privateBankManager = PrivateBankManager.getPrivateBankManager();
        privateBankManager.getCurrencyRates(new CallbackPrivateBank() {
            @Override
            public void onResponse(List<CurrencyPrivateBank> currencyPrivateBankListCallback) {
                setDataCurrencyPrivateBankUI(currencyPrivateBankListCallback);
            }

            @Override
            public void onFailure() {
                // На майбутнє
            }
        });
    }

    /** Цей метод здійснює загрузку курса валют з Монобанк*/
    private void loadCurrencyMonoBank(){
        MonoBankController monoBankController = MonoBankController.getMonoBankManager();
        monoBankController.getCurrencyMonoBanksRates(new CallbackMonoBank() {
            @Override
            public void onResponse(List<CurrencyMonoBank> currencyMonoBankListCall) {
                setDataCurrencyMonoBankUI(currencyMonoBankListCall);
            }

            @Override
            public void onFailure() {
                // Залишили на майбутнє
            }
        });
    }

    /** Цей метод здійснює встановлення значення в UI елементи,
     *  які відповідать за відображення курса валют приват банк */
    private void setDataCurrencyPrivateBankUI(List<CurrencyPrivateBank> currencyPrivateBankList){
        for(CurrencyPrivateBank currency : currencyPrivateBankList){
            if(currency.getCcy().equals("USD")){
                tVTitleUSD_type_private.setText(currency.getCcy());
                tVRateUSD_BAY_private.setText(String.valueOf(currency.getBuy()));
                tVRateUSD_SALE_private.setText(String.valueOf(currency.getSalle()));
            } else if (currency.getCcy().equals("EUR")) {
               tVTitleEUR_type_private.setText(currency.getCcy());
               tVRateEUR_BAY_private.setText(String.valueOf((currency.getBuy())));
               tVRateEUR_SALE_private.setText(String.valueOf((currency.getSalle())));
            }
        }
    }

    /** Цей метод здійснює встановлення значення в UI елементи,
     * які відповідать за відображення курса валют моно банк*/
    private void setDataCurrencyMonoBankUI(List<CurrencyMonoBank> currencyMonoBanksList){
        for(CurrencyMonoBank currency : currencyMonoBanksList){
           String CCY = TypeCurrency.searchCurrencyCcy(currency.getCurrencyCodeA());
            if(CCY != null){
                if (CCY.equals("EUR")) {
                    tVTitleEUR_type_monoBank.setText(CCY);
                    tVRateEUR_BAY_monoBank.setText(String.valueOf((currency.getBuy())));
                    tVRateEUR_SALE_monoBank.setText(String.valueOf((currency.getSell())));
                }

                else if (CCY.equals("USD")) {
                    tVTitleUSD_type_monoBank.setText(CCY);
                    tVRateUSD_BAY_monoBank.setText(String.valueOf(currency.getBuy()));
                    Log.e("tVRateUSD_BAY_monoBank", String.valueOf(currency.getBuy()));
                    tVRateUSD_SALE_monoBank.setText(String.valueOf(currency.getSell()));
                    Log.e("tVRateUSD_SALE_monoBank",String.valueOf(currency.getSell()));
                }
            }
        }
    }
}