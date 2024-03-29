package com.example.monefy.basic.functionality.fragment.accounting.revenue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monefy.R;

public class IncomeFragment extends Fragment {
    private FragmentContainerView fragIncome;
    private FragmentContainerView fragInformationBoard;
    private final InfoBoardIncomeFragment infoBoardIncomeFragment = new InfoBoardIncomeFragment();
    private final RevenueListFragment revenueListFragment = new RevenueListFragment();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_income, container, false);
        setupUIElements(view);
        showFragIncomes();
        showFragInformationBoard();
        return view;
    }

    private void setupUIElements(View view){
        this.fragIncome = view.findViewById(R.id.fragIncome);
        this.fragInformationBoard = view.findViewById(R.id.informationBoard);
    }

    private void showFragIncomes(){
        /*
        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                incomeListFragment,
                fragIncome.getId()
        );

         */
    }

    private void showFragInformationBoard(){
        infoBoardIncomeFragment.setIncomeListFragment(revenueListFragment);

        /*
        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                infoBoardIncomeFragment,
                fragInformationBoard.getId()
        );

         */
    }
}