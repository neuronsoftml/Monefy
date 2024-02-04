package com.example.monefy.basic.functionality.fragment.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Billings;

public class HistoryBillingsFragment extends Fragment {

    private TextView textBilling;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_billings, container, false);
        setupUIElements(view);
        return view;
    }

    private void setupUIElements(View view){
        this.textBilling = view.findViewById(R.id.textBilling);
    }


    public void updateDataHistory(Billings billings){
        textBilling.setText(billings.getName());
    }
}