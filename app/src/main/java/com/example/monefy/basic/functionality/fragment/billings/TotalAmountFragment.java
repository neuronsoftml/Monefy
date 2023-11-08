package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.CurrencyNbu;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.TotalAmount;

import java.util.ArrayList;
import java.util.List;

public class TotalAmountFragment extends Fragment implements DataLoadListener {

    private List<Billings> billings = new ArrayList<>();
    private List<CurrencyNbu> currencyNbu = new ArrayList<>();
    private TextView tVTotalBill;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_total_amount, container, false);

       setupUIElements(view);
       return view;
    }

    private void setupUIElements(View view){
        this.tVTotalBill = view.findViewById(R.id.tVtotalBill);
    }

    public void setValueData(){
        TotalAmount totalAmount = new TotalAmount(0, billings,currencyNbu);
        tVTotalBill.setText(String.valueOf(totalAmount.getAmount()));
    }

    @Override
    public void onDataLoaded() {
        if(billings != null && currencyNbu != null){
            setValueData();
        }
    }

    public void setBillings(List<Billings> billings) {
        this.billings = billings;
    }

    public void setCurrencyNbu(List<CurrencyNbu> currencyNbu) {
        this.currencyNbu = currencyNbu;
    }
}