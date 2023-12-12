package com.example.monefy.basic.functionality.fragment.income;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monefy.Manager.incomes.IncomeManager;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.incomes.IncomesListAdapter;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeListFragment extends Fragment {

    private ListView listItemIncome;
    private TextView tvMessage;
    private Context context;
    private IncomeManager incomeManager = IncomeManager.getIncomeManager();
    private List<Income> incomeList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_list, container, false);

        setupUIElement(view);

        incomeManager.loadIncomes
                (()->{
            incomeList.clear();
            incomeList = incomeManager.getIncomeList();
            showIncomeList();
            handlerClickItemIncome();

            if(incomeList.size() == 0){
                tvMessage.setVisibility(View.VISIBLE);
            }
        });

        context =  getContext();

        return view;
    }

    private void setupUIElement(View view){
        this.listItemIncome = view.findViewById(R.id.list_item_incomes);
    }
    private IncomesListAdapter incomesListAdapter;
    private void showIncomeList() {
        incomesListAdapter = new IncomesListAdapter(
                getContext(),
                incomeList

        );
        listItemIncome.setAdapter(incomesListAdapter);
    }

    private void handlerClickItemIncome() {
    }


}