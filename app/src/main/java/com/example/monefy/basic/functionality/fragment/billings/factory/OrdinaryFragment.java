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
import com.example.monefy.basic.functionality.model.billings.Ordinary;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;


public class OrdinaryFragment extends Fragment {
    private final boolean isEditMode;
    private TextView tvTypeBillings, tvTypeCurrency, tVBalance, tvCreditLimit, tvName;
    private TextView tvTitleBalanceBillings, tvTitleCreditLimit, textTitleTypeBillings;
    private LinearLayout lirLatTypeBillings, lirLatTypeCurrency, lirLatBalance, lirLatCreditLimit, lirLatName;
    private String argTypeBillings;
    private Billings ordinaryBilling;
    private BillingDetailsFragment billingDetailsFragment;

    public OrdinaryFragment(boolean isEditMode) {
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
                this.ordinaryBilling = (Ordinary) billing;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_ordinary, container, false);
       setupUIElements(view);
       setupClickListeners();
       setDataArguments();
       return view;
    }

    private void setupUIElements(View view) {
        tvTypeBillings = view.findViewById(R.id.type_billings);
        tvTypeCurrency = view.findViewById(R.id.text_type_currency);
        tVBalance = view.findViewById(R.id.balance);
        tvCreditLimit = view.findViewById(R.id.credit_limit);
        tvName = view.findViewById(R.id.name_billings);
        lirLatTypeBillings = view.findViewById(R.id.linerLayout_type_billings);
        lirLatTypeCurrency = view.findViewById(R.id.linerLayout_type_currency);
        lirLatBalance = view.findViewById(R.id.linerLayout_balance_billings);
        lirLatCreditLimit = view.findViewById(R.id.liner_layout_credit_limit);
        lirLatName = view.findViewById(R.id.linerLayout_name_billings);
        tvTitleBalanceBillings = view.findViewById(R.id.textView_balance_create_billings);
        tvTitleCreditLimit = view.findViewById(R.id.text_view_credit_limit_new_billings);
        textTitleTypeBillings = view.findViewById(R.id.textTitleTypeBillings);
    }

    private void setDataArguments() {
        if(argTypeBillings != null){
            tvTypeBillings.setText(argTypeBillings);
        }

        if(ordinaryBilling != null){
            tvTypeBillings.setText(ordinaryBilling.getTypeBillings());
            tvTypeCurrency.setText(ordinaryBilling.getTypeCurrency());
            tVBalance.setText(String.valueOf(ordinaryBilling.getBalance()));
            tvCreditLimit.setText(String.valueOf(ordinaryBilling.getCreditLimit()));
            tvName.setText(ordinaryBilling.getName());
        }
    }

    private void setupClickListeners() {
        handlerClickLinerLayoutTypeBillings();
        handlerClickLinerLayoutTypeCurrency();
        handlerClickLinerLayoutBalance();
        handlerClickLinerLayoutCreditLimit();
        handlerClickLinerLayoutName();
    }

    private void handlerClickLinerLayoutName(){
        lirLatName.setOnClickListener(v->{
            ModalInputText modalInputText = new ModalInputText(getContext(), R.layout.modal_bottom_input_name, new DialogCallback() {
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
            modalBalanceFragment.show(getChildFragmentManager(),"ModalBalanceFragment");
            modalBalanceFragment.checkDialogCallback(new DialogCallback() {
                @Override
                public void onSuccess(String data) {
                    tVBalance.setText(data);
                    UpdateUI.resetStyleSelect(lirLatBalance, getResources());
                    modalBalanceFragment.dismiss();
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutCreditLimit(){
        lirLatCreditLimit.setOnClickListener(v->{
            ModalBalanceFragment modalBalanceFragment = new ModalBalanceFragment();

            Bundle bundle = new Bundle();
            bundle.putString("titleModal",tvTitleCreditLimit.getText().toString());
            bundle.putString("balance", tvCreditLimit.getText().toString());
            bundle.putString("typeCurrency", tvTypeCurrency.getText().toString());
            bundle.putString("typeBillings", tvTypeBillings.getText().toString());

            modalBalanceFragment.setArguments(bundle);
            modalBalanceFragment.show(getChildFragmentManager(),"ModalBalanceFragment");

            modalBalanceFragment.checkDialogCallback(new DialogCallback() {
                @Override
                public void onSuccess(String data) {
                    tvCreditLimit.setText(data);
                    UpdateUI.resetStyleSelect(lirLatCreditLimit, getResources());
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
            return new Ordinary(
                    Long.parseLong(balance),
                    Long.parseLong(creditLimit),
                    nameBillings,
                    typeBillings,
                    typeCurrency,
                    DateController.getCurrentDateFormatFirebase()
            );
        }
        return null;
    }

    private String nameBillings;
    private String typeBillings;
    private String typeCurrency;
    private String balance;
    private String creditLimit;

    private void getValueAllInput(){
        nameBillings = tvName.getText().toString();
        typeBillings = tvTypeBillings.getText().toString();
        typeCurrency = tvTypeCurrency.getText().toString();
        balance = tVBalance.getText().toString().isEmpty() ? "0" : tVBalance.getText().toString();
        creditLimit = tvCreditLimit.getText().toString().isEmpty() ? "0" : tvCreditLimit.getText().toString();
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
        } else if (creditLimit == null || creditLimit.equals("0")) {
            UpdateUIError.setStyleError(lirLatCreditLimit,getResources());
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