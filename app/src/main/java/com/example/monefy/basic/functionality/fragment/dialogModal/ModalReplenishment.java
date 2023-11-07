package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.BillingsListAdapter;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.Manager.BillingsManager;

import java.util.ArrayList;
import java.util.List;

public class ModalReplenishment extends DialogMenu{
    private Billings bill;
    private ImageView imgViewBillingCard;
    private TextView tVNameBilling, tVBillingBalance, tVTypeCurrency, tVTypeBilling;
    private ToggleButton toggleBtnIncome, toggleBtnFromTheBill;
    private LinearLayout linearLayoutIncome, linearLayoutFromTheBill;
    private ListView listViewBillRep;
    private Context context;
    private BillingsManager billingsManager = BillingsManager.getBillingsManager();
    public ModalReplenishment(Context context, int contentView, Billings bill) {
        super(context, contentView);
        this.context = context;
        this.bill = bill;
    }

    @Override
    public void modalStart(DialogCallback dialogCallback) {
        openModal();
        billingsManager.loadBillings(()->{
            billings.clear();
            billings = billingsManager.getBillingsList();
            showBillingsList();
        });
        setupUIDialogModal();
        setValueObjectModal();
        handlerButtonDialogModal(dialogCallback);
    }

    private Dialog dialog = getDialogModal();

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
    }

    @Override
    public void handlerButtonDialogModal(DialogCallback dialogCallback) {
        handlerToggleButton();
    }

    private void setValueObjectModal() {
        imgViewBillingCard.setImageResource(TypeBillings.getIdImageTypeBillings(bill.getTypeBillings()));
        tVNameBilling.setText(bill.getName());
        tVBillingBalance.setText(String.valueOf(bill.getBalance()));
        tVTypeCurrency.setText(bill.getTypeCurrency());
        tVTypeBilling.setText(bill.getTypeBillings());
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
    private BillingsListAdapter billingsListAdapter;

    private void showBillingsList(){
        billingsListAdapter = new BillingsListAdapter(
                context,
                BillingsManager.sortingBillings(
                        billings,
                        TypeBillings.getListTypeBillingsCO()
                ));
        listViewBillRep.setAdapter(billingsListAdapter);
    }


}
