package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.CurrencyNbu;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.TotalSavings;

import java.util.List;

public class TotalSavingsFragment extends Fragment implements DataLoadListener {

    private TextView tVTotalSavings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_total_savings, container, false);

        setupUIElements(view);

        return view;
    }

    private void setupUIElements(View view){
        this.tVTotalSavings = view.findViewById(R.id.tVTotalSavings);
    }

    private List<Billings> billingsList;
    private List<CurrencyNbu> currencyNbuList;

    public void setBillingsList(List<Billings> billingsList) {
        this.billingsList = billingsList;
    }

    public void setCurrencyNbuList(List<CurrencyNbu> currencyNbuList) {
        this.currencyNbuList = currencyNbuList;
    }

    @Override
    public void onDataLoaded() {
        if(billingsList != null && currencyNbuList != null){
            setValueData();
        }
    }

    private void setValueData() {
        TotalSavings totalSavings = new TotalSavings(0,billingsList, currencyNbuList);
        tVTotalSavings.setText(String.valueOf(totalSavings.getAmount()));
    }
}