package com.example.monefy.basic.functionality.fragment.billings.factory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.basic.functionality.controller.date.DateController;
import com.example.monefy.basic.functionality.controller.enumType.EnumTypeController;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.UI.UpdateUI;
import com.example.monefy.basic.functionality.UI.UpdateUIError;
import com.example.monefy.basic.functionality.fragment.billings.BillingDetailsFragment;
import com.example.monefy.basic.functionality.Interface.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalanceFragment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalInputText;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalFunctionalSelect;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Cumulative;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

public class CumulativeFragment extends Fragment {
    private final boolean isEditMode;
    private TextView tvTypeBillings, tvTypeCurrency, tVBalance, tvGoal, tvName;
    private TextView tvTitleBalanceBillings,textTitleTypeBillings;
    private LinearLayout lirLatTypeBillings, lirLatTypeCurrency, lirLatBalance, lirLatGoal, lirLatName;
    private String argTypeBillings;
    private Cumulative cumulativeBilling;
    private BillingDetailsFragment billingDetailsFragment;

    public CumulativeFragment(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            argTypeBillings = bundle.getString("TypeBillings");
            Billings billing = (Billings) bundle.getSerializable("billing");
            if (billing != null) {
                this.cumulativeBilling = (Cumulative) billing;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cumulative, container, false);
        setupUIElements(view);
        setupClickListeners();
        setDataArguments();
        return view;
    }

    private void setupUIElements(View view) {
        tvTypeBillings = view.findViewById(R.id.type_billings);
        tvTypeCurrency = view.findViewById(R.id.text_type_currency);
        tVBalance = view.findViewById(R.id.balance);
        tvGoal = view.findViewById(R.id.goal_cumulative);
        tvName = view.findViewById(R.id.name_billings);
        lirLatTypeBillings = view.findViewById(R.id.linerLayout_type_billings);
        lirLatTypeCurrency = view.findViewById(R.id.linerLayout_type_currency);
        lirLatBalance = view.findViewById(R.id.linerLayout_balance_billings);
        lirLatGoal = view.findViewById(R.id.linerLayout_type_goal);
        lirLatName = view.findViewById(R.id.linerLayout_name_billings);
        tvTitleBalanceBillings = view.findViewById(R.id.textView_balance_create_billings);
        textTitleTypeBillings = view.findViewById(R.id.textTitleTypeBillings);
    }

    private void setDataArguments() {
        if(argTypeBillings != null){
            tvTypeBillings.setText(argTypeBillings);
        }

        if(cumulativeBilling != null){
            tvTypeBillings.setText(cumulativeBilling.getTypeBillings());
            tvTypeCurrency.setText(cumulativeBilling.getTypeCurrency());
            tVBalance.setText(String.valueOf(cumulativeBilling.getBalance()));
            tvGoal.setText(String.valueOf(cumulativeBilling.getGoal()));
            tvName.setText(cumulativeBilling.getName());
        }
    }

    private void setupClickListeners() {
        handlerClickLinerLayoutTypeBillings();
        handlerClickLinerLayoutTypeCurrency();
        handlerClickLinerLayoutBalance();
        handlerClickLinerLayoutGoal();
        handlerClickLinerLayoutName();
    }

    private void handlerClickLinerLayoutName(){
        lirLatName.setOnClickListener(v->{
            ModalInputText modalInputText = new ModalInputText(getContext(), R.layout.modal_bottom_input_name,
                    new DialogCallback() {
                        @Override
                        public void onSuccess(String data) {
                            tvName.setText(data);
                            UpdateUI.resetStyleSelect(lirLatName, getResources());
                        }

                        @Override
                        public void onFailure(Exception exception) {

                        }
                    });
            modalInputText.modalStart();
        });
    }

    private void handlerClickLinerLayoutTypeBillings(){
       if(!isEditMode){
           lirLatTypeBillings.setOnClickListener(v -> {

               ModalFunctionalSelect modalFunctionalSelect = new ModalFunctionalSelect(
                       getContext(),
                       R.string.textSelectTypeBillings,
                       EnumTypeController.getTypesBillings(),
                       TypeBillings.class,
                       new DialogCallback() {
                           @Override
                           public void onSuccess(String data) {
                               tvTypeBillings.setText(data);
                               UpdateUI.resetStyleSelect(lirLatTypeBillings, getResources());
                               billingDetailsFragment.onBillingTypeChanged(data);
                               closeFragment();
                           }

                           @Override
                           public void onFailure(Exception exception) {

                           }
                       }
               );
               modalFunctionalSelect.modalStart();
           });
       }else{
            UpdateUI.blockElement(lirLatTypeBillings,getResources());
            UpdateUI.replaysIconBlockText(textTitleTypeBillings);
       }
    }

    private void handlerClickLinerLayoutTypeCurrency(){
        lirLatTypeCurrency.setOnClickListener(v -> {

            ModalFunctionalSelect modalSelect = new ModalFunctionalSelect(
                    getContext(),
                    R.string.textSelectTypeCurrencies,
                    EnumTypeController.getTypeCurrency(),
                    TypeCurrency.class,
                    new DialogCallback() {
                        @Override
                        public void onSuccess(String data) {
                            tvTypeCurrency.setText(data);
                            UpdateUI.resetStyleSelect(lirLatTypeCurrency, getResources());
                            billingDetailsFragment.onBillingTypeChanged(data);
                        }
                        @Override
                        public void onFailure(Exception exception) {

                        }
                    }
            );

            modalSelect.modalStart();
        });
    }

    private void handlerClickLinerLayoutBalance(){
        lirLatBalance.setOnClickListener(v -> {
            ModalBalanceFragment modalBalanceFragment = new ModalBalanceFragment();

            Bundle bundle = new Bundle();
            bundle.putString("titleModal",tvTitleBalanceBillings.getText().toString());
            bundle.putString("balance", tVBalance.getText().toString());
            bundle.putString("typeCurrency", tvTypeCurrency.getText().toString());
            bundle.putString("typeBillings", tvTypeBillings.getText().toString());

            modalBalanceFragment.setArguments(bundle);
            modalBalanceFragment.show(getChildFragmentManager(),"ModalReplenishmentFragment");
            modalBalanceFragment.checkDialogCallback(new DialogCallback() {
                @Override
                public void onSuccess(String data) {
                    tVBalance.setText(modalBalanceFragment.getBalance());
                    UpdateUI.resetStyleSelect(lirLatBalance, getResources());
                    modalBalanceFragment.dismiss();
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutGoal(){
        lirLatGoal.setOnClickListener(v-> {
            ModalBalanceFragment modalBalanceFragment = new ModalBalanceFragment();

            Bundle bundle = new Bundle();
            bundle.putString("titleModal", "Мета");
            bundle.putString("balance", tvGoal.getText().toString());
            bundle.putString("typeCurrency", tvTypeCurrency.getText().toString());
            bundle.putString("typeBillings", tvTypeBillings.getText().toString());

            modalBalanceFragment.setArguments(bundle);
            modalBalanceFragment.show(getChildFragmentManager(),"ModalBalanceFragment");
            modalBalanceFragment.checkDialogCallback(new DialogCallback() {
                @Override
                public void onSuccess(String data) {
                    tvGoal.setText(data);
                    UpdateUI.resetStyleSelect(lirLatGoal, getResources());
                    modalBalanceFragment.dismiss();
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    public Billings getBilling(){
        if(checkInputData()){
            return new Cumulative(
                    Long.parseLong(balance),
                    nameBillings,
                    typeBillings,
                    typeCurrency,
                    Long.parseLong(goal),
                    DateController.getCurrentDateFormatFirebase()
            );
        }
        return null;
    }

    private String nameBillings;
    private String typeBillings;
    private String typeCurrency;
    private String balance;
    private String goal;

    private void getValueAllInput(){
        nameBillings = tvName.getText().toString();
        typeBillings = tvTypeBillings.getText().toString();
        typeCurrency = tvTypeCurrency.getText().toString();
        balance = tVBalance.getText().toString().isEmpty() ? "0" : tVBalance.getText().toString();
        goal = tvGoal.getText().toString().isEmpty() ? "0" : tvGoal.getText().toString();
    }

    public boolean checkInputData(){
        getValueAllInput();
        if(typeBillings == null || typeBillings.isEmpty()){
            UpdateUIError.setStyleError(lirLatTypeBillings,getResources());
            return false;
        } else if (typeCurrency == null || typeCurrency.isEmpty()) {
            UpdateUIError.setStyleError(lirLatTypeCurrency,getResources());
            return false;
        } else if (balance == null || balance.equals("0")) {
            UpdateUIError.setStyleError(lirLatBalance,getResources());
            return false;
        } else if (goal == null || goal.isEmpty()) {
            UpdateUIError.setStyleError(lirLatGoal,getResources());
            return false;
        }
        return true;
    }

    private void closeFragment() {
        // Виклик методу активності для видалення поточного фрагменту
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    public void setBillingDetailsFragment(BillingDetailsFragment billingDetailsFragment) {
        this.billingDetailsFragment = billingDetailsFragment;
    }

}