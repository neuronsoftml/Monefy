package com.example.monefy.basic.functionality.fragment.income;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;

public class IncomeFragment extends Fragment {
    private FragmentContainerView fragIncome;

    private IncomeListFragment incomeListFragment = new IncomeListFragment();
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

        return view;
    }

    private void setupUIElements(View view){
        fragIncome = view.findViewById(R.id.fragIncome);
    }

    private void showFragIncomes(){
        FragmentSwitcher.replaceFragment(
                incomeListFragment,
                getContext(),
                fragIncome.getId()
        );
    }
}