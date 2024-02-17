package com.example.monefy.basic.functionality.fragment.accounting.revenue;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monefy.basic.functionality.controller.incomes.IncomeController;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.incomes.IncomesListAdapter;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.ArrayList;
import java.util.List;

public class RevenueListFragment extends Fragment {

    private ListView listItemIncome;
    private final IncomeController incomeController = IncomeController.getIncomeManager();
    private List<Income> incomeList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue_list, container, false);

        setupUIElement(view);

        incomeController.loadIncomes
                (()->{
            incomeList.clear();
            incomeList = incomeController.getIncomeList();
            showIncomeList();
            handlerClickItemIncome();

            if(incomeList.size() == 0){
                tvMessage.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

    private void setupUIElement(View view){
        this.listItemIncome = view.findViewById(R.id.listItem);
    }

    private void showIncomeList() {
        IncomesListAdapter incomesListAdapter = new IncomesListAdapter(
                getContext(),
                incomeList

        );
        listItemIncome.setAdapter(incomesListAdapter);
    }

    private void handlerClickItemIncome() {
    }

    public List<Income> getIncomeList(){
        return incomeList;
    }

}