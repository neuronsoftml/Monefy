package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.billings.BillingsListFragment;
import com.example.monefy.basic.functionality.fragment.bank.CurrencyNbuFragment;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.TotalAmount;
import com.example.monefy.basic.functionality.model.TotalSavings;
import com.example.monefy.basic.functionality.model.billings.Billings;

import java.util.List;

public class InfoBoardFragment extends Fragment implements DataLoadListener {

    private BillingsListFragment billingsListFragment;
    private FragmentContainerView fragCurrencyNbu;
    private CurrencyNbuFragment currencyNbuFragment;
    private TextView tvTotalSavings, tvTotalAmount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_board, container, false);
        setupUIElements(view);
        showFragCurrencyNbu();
        return view;
    }

    private void setupUIElements(View view){
        this.fragCurrencyNbu = view.findViewById(R.id.fragCurrencyNbu);
        this.tvTotalSavings = view.findViewById(R.id.tVTotalSavings);
        this.tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
    }

    private void showFragCurrencyNbu() {
        currencyNbuFragment = new CurrencyNbuFragment();
        currencyNbuFragment.setInfoBoardFragment(this);
        FragmentSwitcher.replaceFragment(
                currencyNbuFragment,
                getContext(),
                fragCurrencyNbu.getId()
        );
    }

    public void setBillingsListFragment(BillingsListFragment billingsListFragment) {
        this.billingsListFragment = billingsListFragment;
    }

    @Override
    public void onDataLoaded() {
        if((billingsListFragment.getBillings() != null)){
            setValueTotalSaving(billingsListFragment.getBillings());
            setValueTotalAmount(billingsListFragment.getBillings());
        }
    }

    private TotalAmount totalAmount;
    private void setValueTotalAmount(List<Billings> billings) {
        totalAmount = new TotalAmount(0,billings,currencyNbuFragment.getCurrencyNbuRates());
        tvTotalAmount.setText(String.valueOf(totalAmount.getAmount()));
    }

    private TotalSavings totalSavings;
    private void setValueTotalSaving(List<Billings> billings) {
        totalSavings = new TotalSavings(0, billings, currencyNbuFragment.getCurrencyNbuRates());
        tvTotalSavings.setText(String.valueOf(totalSavings.getAmount()));
    }

    public void updateInfoBord(List<Billings> billings){
        setValueTotalAmount(billings);
        setValueTotalSaving(billings);
    }
}