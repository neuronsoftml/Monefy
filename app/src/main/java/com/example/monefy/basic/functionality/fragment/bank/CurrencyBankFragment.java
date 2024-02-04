package com.example.monefy.basic.functionality.fragment.bank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.bank.MonoBank.MonoBankManager;
import com.example.monefy.basic.functionality.fragment.bank.PrivateBank.PrivateBankManager;
import com.example.monefy.basic.functionality.fragment.billings.InfoBoardBillingsFragment;
import com.example.monefy.basic.functionality.fragment.income.InfoBoardIncomeFragment;
import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;
import com.example.monefy.basic.functionality.model.currency.CurrencyPrivateBank;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyBankFragment extends Fragment {

    private TextView tVTitleUSD_type_private, tVTitleEUR_type_private;
    private TextView tVRateUSD_BAY_private, tVRateEUR_BAY_private;
    private TextView tVRateUSD_SALE_private, tVRateEUR_SALE_private;

    private TextView tVTitleUSD_type_monoBank, tVTitleEUR_type_monoBank;
    private TextView tVRateUSD_BAY_monoBank, tVRateEUR_BAY_monoBank;
    private TextView tVRateUSD_SALE_monoBank, tVRateEUR_SALE_monoBank;

    private final List<CurrencyPrivateBank> currencyPrivateBankList = new ArrayList<>();
    private final List<CurrencyMonoBank> currencyMonoBanksList = new ArrayList<>();
    private InfoBoardBillingsFragment infoBoardBillingsFragment;
    private InfoBoardIncomeFragment infoBoardIncomeFragment;

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

    /** Цей метод здійснює загрузку курса валют з Приват банк*/
    private void loadCurrencyPrivateBank() {
        PrivateBankManager.currencyParse(new CallbackBank() {
            @Override
            public void onResponse() {
                currencyPrivateBankList.addAll(PrivateBankManager.getCurrencyRates());

                setDataCurrencyPrivateBankUI();

                if(infoBoardBillingsFragment != null){
                    infoBoardBillingsFragment.onDataLoaded();
                }
                if(infoBoardIncomeFragment != null){
                    infoBoardIncomeFragment.onDataLoaded();
                }
            }

            @Override
            public void onFailure() {
                Log.e("PrivatBank","ПОМИЛКА ЗЄДНАННЯ ПриватБанк");
            }
        });
    }

    /** Цей метод здійснює загрузку курса валют з Монобанк*/
    private void loadCurrencyMonoBank(){
        MonoBankManager.currencyParse(new CallbackBank() {
            @Override
            public void onResponse() {
                currencyMonoBanksList.addAll(MonoBankManager.getCurrencyRates());
                setDataCurrencyMonoBankUI();
            }

            @Override
            public void onFailure() {
                Log.e("MonoBank","ПОМИЛКА ЗЄДНАННЯ МоноБанк");
            }
        });
    }

    /** Цей метод здійснює встановлення значення в UI елементи,
     *  які відповідать за відображення курса валют приват банк */
    private void setDataCurrencyPrivateBankUI(){
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
    private void setDataCurrencyMonoBankUI(){
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

    public List<CurrencyPrivateBank> getCurrencyPrivateBankList() {
        return currencyPrivateBankList;
    }

    public void setInfoBoardBillingFragment(InfoBoardBillingsFragment infoBoardBillingsFragment) {
        this.infoBoardBillingsFragment = infoBoardBillingsFragment;
    }

    public void setInfoBoardIncomeFragment(InfoBoardIncomeFragment infoBoardIncomeFragment) {
        this.infoBoardIncomeFragment = infoBoardIncomeFragment;
    }

}