package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monefy.basic.functionality.Interface.billings.OnBillingsCallback;
import com.example.monefy.basic.functionality.Interface.dialogModal.BillingsSelectionCallback;
import com.example.monefy.basic.functionality.controller.incomes.IncomeController;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.billings.BillingsListAdapter;
import com.example.monefy.basic.functionality.adapter.incomes.IncomesListAdapter;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.controller.billings.BillingsController;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.ArrayList;
import java.util.List;

public class ModalSelectReplenishment extends DialogModal {
    private final Billings selectedBillings;
    private ImageView imgViewBillingCard;
    private TextView tVNameBilling, tVBillingBalance, tVTypeCurrency, tVTypeBilling;
    private ToggleButton toggleBtnIncome, toggleBtnFromTheBill;
    private LinearLayout linearLayoutIncome, linearLayoutFromTheBill;
    private ListView listViewIncome;
    private RecyclerView listViewBillRep;
    private final Context context;
    private final BillingsController billingsController = new BillingsController();
    private final IncomeController incomeController = IncomeController.getIncomeManager();
    private final BillingsSelectionCallback callbackSelectedBillings;
    public ModalSelectReplenishment(Context context, int contentView, Billings selectedBillings, BillingsSelectionCallback callbackSelectedBillings) {
        super(context, contentView);
        this.context = context;
        this.selectedBillings = selectedBillings;
        this.callbackSelectedBillings = callbackSelectedBillings;
    }

    @Override
    public void modalStart() {
        openModal();


       /* incomeManager.loadIncomes(()->{
            incomes.clear();
            incomes = incomeManager.getIncomeList();
            showIncomesList();
            handlerIncomeListView();
        });
        */
        setupUIDialogModal();
        loadBillingsList();
        setValueObjectModal();
        handlerButtonDialogModal();
    }

    private final Dialog dialog = getDialogModal();


    @Override
    public void setupUIDialogModal() {
        imgViewBillingCard =  dialog.findViewById(R.id.imgV_modal_card_rep);
        tVNameBilling = dialog.findViewById(R.id.tV_modal_name_bill_rep);
        tVBillingBalance = dialog.findViewById(R.id.tV_modal_balance_bill_rep);
        tVTypeCurrency = dialog.findViewById(R.id.tV_modal_type_currency_bill_rep);
        tVTypeBilling = dialog.findViewById(R.id.tV_type_bill_rep);
        toggleBtnIncome = dialog.findViewById(R.id.toggleBtn_income);
        toggleBtnFromTheBill = dialog.findViewById(R.id.toggleBtn_from_the_bill);
        linearLayoutIncome = dialog.findViewById(R.id.linearLayoutIncome);
        linearLayoutFromTheBill = dialog.findViewById(R.id.linearLayoutFromTheBill);
        listViewBillRep = dialog.findViewById(R.id.listView_bill_rep);
        listViewIncome = dialog.findViewById(R.id.listView_income);
    }

    @Override
    public void handlerButtonDialogModal() {
        handlerToggleButton();
    }

    private void setValueObjectModal() {
        imgViewBillingCard.setImageResource(TypeBillings.getIdImageTypeBillings(selectedBillings.getTypeBillings()));
        tVNameBilling.setText(selectedBillings.getName());
        tVBillingBalance.setText(String.valueOf(selectedBillings.getBalance()));
        tVTypeCurrency.setText(selectedBillings.getTypeCurrency());
        tVTypeBilling.setText(selectedBillings.getTypeBillings());
        switcherToggleButton(toggleBtnIncome);
    }

    private void handlerToggleButton() {
        toggleBtnIncome.setOnClickListener(v->{
            switcherToggleButton(toggleBtnIncome);
        });
        toggleBtnFromTheBill.setOnClickListener(v->{
            switcherToggleButton(toggleBtnFromTheBill);
        });
    }

    private void switcherToggleButton(ToggleButton toggleButton) {
        if(toggleBtnIncome == toggleButton){
            toggleBtnFromTheBill.setChecked(false);
            toggleBtnFromTheBill.setTextColor(context.getResources().getColor(R.color.gray));
            linearLayoutFromTheBill.setVisibility(View.GONE);
            toggleBtnIncome.setChecked(true);
            toggleBtnIncome.setTextColor(context.getResources().getColor(R.color.black));
            linearLayoutIncome.setVisibility(View.VISIBLE);
        }
        if(toggleBtnFromTheBill == toggleButton){
            toggleBtnIncome.setChecked(false);
            toggleBtnIncome.setTextColor(context.getResources().getColor(R.color.gray));
            linearLayoutIncome.setVisibility(View.GONE);
            toggleBtnFromTheBill.setChecked(true);
            toggleBtnFromTheBill.setTextColor(context.getResources().getColor(R.color.black));
            linearLayoutFromTheBill.setVisibility(View.VISIBLE);
        }
    }

    private List<Billings> billings = new ArrayList<>();

    private  BillingsListAdapter billingsListAdapter;

    private void loadBillingsList(){
        BillingsController.getBillingsAllExceptOne(selectedBillings, new OnBillingsCallback() {
            @Override
            public void onBillingsDataReceived(List<Billings> billingsList) {
                billings.clear();
                billings.addAll(billingsList);
                showBillingsList();
                handlerBillingsListView();
            }

            @Override
            public void onDataNotFound() {
                //На майбутнє
            }
        });
    }
    private void showBillingsList(){
      billingsListAdapter = new BillingsListAdapter(
                BillingsController.sortingBillings(
                        billings,
                        TypeBillings.getListTypeBillingsCO()
                ));
        listViewBillRep.setAdapter(billingsListAdapter);
        listViewBillRep.setLayoutManager(new LinearLayoutManager(context));
    }

    private List<Income> incomes = new ArrayList<>();

    private IncomesListAdapter incomesListAdapter;

    private void showIncomesList(){
        incomesListAdapter = new IncomesListAdapter(
                context,
                incomes );
        listViewIncome.setAdapter(incomesListAdapter);
    }

    private void handlerBillingsListView(){
        billingsListAdapter.setOnItemClickListener(object -> {
            Billings theBillFromWhichWeDebit = (Billings) object;
            if(callbackSelectedBillings != null){
                callbackSelectedBillings.selectBillings(
                        selectedBillings,
                        theBillFromWhichWeDebit
                );
                exitModal();
            }
        });
    }

    private void handlerIncomeListView(){
        incomesListAdapter.setOnItemClickListener(object -> {
            Income income = (Income) object;
            Log.e("Income", income.getName());
        });
    }

}
