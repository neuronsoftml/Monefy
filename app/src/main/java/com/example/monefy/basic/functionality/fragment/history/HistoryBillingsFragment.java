package com.example.monefy.basic.functionality.fragment.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.history.OnHistoryBillingsCallback;
import com.example.monefy.basic.functionality.adapter.history.HistoryBillingsAdapter;
import com.example.monefy.basic.functionality.controller.history.HistoryBillingsController;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.history.HistoryBilling;

import java.util.List;

public class HistoryBillingsFragment extends Fragment {

    private ListView listViewHistoryBillings;
    private TextView textMessagePage;

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

    /** Цей метод здійснює ініціалізацію UI елементів які знаходять в фрагменті
     * @param view віджети
     */
    private void setupUIElements(View view){
        listViewHistoryBillings = view.findViewById(R.id.listViewHistoryBillings);
        textMessagePage = view.findViewById(R.id.textMessagePage);
    }

    private HistoryBillingsAdapter historyBillingsAdapter;

    public void updateDataHistoryBillings(Billings billings){
        loadListHistoryBillings(billings);
    }

    private void loadListHistoryBillings(Billings billings){
        HistoryBillingsController.loadHistoryBillingsExceptOne(billings.getId(), new OnHistoryBillingsCallback() {
            @Override
            public void onHistoryBillingsReceived(List<HistoryBilling> historyBillingList) {
                showHistoryBillings(historyBillingList);
                listViewHistoryBillings.setVisibility(View.VISIBLE);
                textMessagePage.setVisibility(View.GONE);
            }

            @Override
            public void onDataNotFound() {
                listViewHistoryBillings.setVisibility(View.GONE);
                textMessagePage.setVisibility(View.VISIBLE);
                showTextNotFoundHistory();
            }
        });
    }

    /** Цей метод відображає адаптер історію рахунків*/
    private void showHistoryBillings(List<HistoryBilling> historyBillingList){
        if(historyBillingsAdapter == null){
            historyBillingsAdapter = new HistoryBillingsAdapter(getContext(),historyBillingList);
            listViewHistoryBillings.setAdapter(historyBillingsAdapter);
        }else {
            historyBillingsAdapter.updateHistoryBillingsList(historyBillingList);
            historyBillingsAdapter.notifyDataSetChanged();
        }
    }


    private void showTextNotFoundHistory(){
        // Необхідно реалізовати функцію що історій для цього рахунка немає.
        Log.w("HistoryBilling", "історій для цього рахунка немає");
    }
}