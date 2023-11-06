package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.NBU.CallbackNbu;
import com.example.monefy.basic.functionality.NBU.NbuManager;
import com.example.monefy.basic.functionality.fragment.billings.TotalAmountFragment;
import com.example.monefy.basic.functionality.model.CurrencyNbu;

import java.util.ArrayList;
import java.util.List;

public class CurrencyNbuFragment extends Fragment {

    private TextView tVTitleUSD, tVTitleEUR, tVTitleRON;
    private TextView tVRateUSD, tVRateEUR, tVRateRON;
    private List<CurrencyNbu> currencyNbuRates = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_nbu, container, false);
        setupUIElements(view);
        loadCurrencyNBU();

        return view;
    }

    private void setupUIElements(View view){
        this.tVTitleUSD = view.findViewById(R.id.tVTitleUSD);
        this.tVTitleEUR = view.findViewById(R.id.tVTitleEUR);
        this.tVTitleRON = view.findViewById(R.id.tVTitleRON);
        this.tVRateUSD = view.findViewById(R.id.tVRateUSD);
        this.tVRateEUR = view.findViewById(R.id.tvRateEUR);
        this.tVRateRON = view.findViewById(R.id.tVRateRON);
    }

    private void loadCurrencyNBU() {
        NbuManager.currencyNbuParse(new CallbackNbu() {
            @Override
            public void onResponse() {
                currencyNbuRates.addAll(NbuManager.getCurrencyRates());
                setDataUINbu();
                totalAmountFragment.setCurrencyNbu(currencyNbuRates);
                totalAmountFragment.onDataLoaded();
                totalSavingsFragment.setCurrencyNbuList(currencyNbuRates);
                totalSavingsFragment.onDataLoaded();
            }

            @Override
            public void onFailure() {
                Log.v("NBU","ПОМИЛКА ЗЄДНАННЯ НБУ");
            }
        });
    }

    private void setDataUINbu(){
        for(CurrencyNbu currencyNbu : currencyNbuRates){
            if(currencyNbu.getCc().equals("USD")){
                tVTitleUSD.setText(currencyNbu.getCc());
                tVRateUSD.setText(String.valueOf(currencyNbu.getRate()));
            } else if (currencyNbu.getCc().equals("EUR")) {
                tVTitleEUR.setText(currencyNbu.getCc());
                tVRateEUR.setText(String.valueOf(currencyNbu.getRate()));
            } else if (currencyNbu.getCc().equals("RON")) {
                tVTitleRON.setText(currencyNbu.getCc());
                tVRateRON.setText(String.valueOf(currencyNbu.getRate()));
            }
        }
    }

    private TotalAmountFragment totalAmountFragment;
    private TotalSavingsFragment totalSavingsFragment;

    public void setTotalAmountFragment(TotalAmountFragment totalAmountFragment){
        this.totalAmountFragment = totalAmountFragment;
    }

    public void setTotalSavingsFragment(TotalSavingsFragment totalSavingsFragment) {
        this.totalSavingsFragment = totalSavingsFragment;
    }
}