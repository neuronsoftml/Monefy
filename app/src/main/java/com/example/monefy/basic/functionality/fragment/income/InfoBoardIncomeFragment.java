package com.example.monefy.basic.functionality.fragment.income;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.bank.CurrencyBankFragment;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.List;

public class InfoBoardIncomeFragment extends Fragment implements DataLoadListener {

    private IncomeListFragment incomeListFragment;
    private CurrencyBankFragment currencyBankFragment;
    private FragmentContainerView fragCurrencyNbu;
    private TextView  tvTotalTurnover, tvTotalReceipts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_info_board_income, container, false);

        setupUIElements(view);
        showFragCurrencyNbu();

        return view;
    }

    private void setupUIElements(View view){
        this.tvTotalTurnover = view.findViewById(R.id.tVTotalTurnover);
        this.tvTotalReceipts = view.findViewById(R.id.tvTotalReceipts);
        this.fragCurrencyNbu = view.findViewById(R.id.fragCurrencyNbu);
    }

    @Override
    public void onDataLoaded() {
        if(incomeListFragment.getIncomeList() != null){
            updateInfoBoard(incomeListFragment.getIncomeList());
        }
    }

    private void setValueTotalTurnover(List<Income> incomes){
        //TotalTurnover totalTurnover = new TotalTurnover(0, incomes, currencyNbuFragment.getCurrencyNbuRates());
        //tvTotalTurnover.setText(String.valueOf(totalTurnover.getAmount()));
    }

    private void setValueTotalReceipts(List<Income> incomes){
        //TotalReceipts totalReceipts = new TotalReceipts(0, incomes, currencyNbuFragment.getCurrencyNbuRates());
       // tvTotalReceipts.setText(String.valueOf(totalReceipts.getAmount()));
    }

    private void showFragCurrencyNbu(){
        //currencyNbuFragment = new CurrencyNbuFragment();
       // currencyNbuFragment.setInfoBoardIncomeFragment(this);
        /*
        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                currencyNbuFragment,
                fragCurrencyNbu.getId()
        );

         */
    }

    public void updateInfoBoard(List<Income> incomes){
        setValueTotalTurnover(incomes);
        setValueTotalReceipts(incomes);
    }

    public void setIncomeListFragment(IncomeListFragment incomeListFragment) {
        this.incomeListFragment = incomeListFragment;
    }
}