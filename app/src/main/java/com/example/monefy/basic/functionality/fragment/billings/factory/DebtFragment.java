package com.example.monefy.basic.functionality.fragment.billings.factory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.Manager.date.ManagerDate;
import com.example.monefy.Manager.dialogModal.ManagerType;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.UI.UpdateUI;
import com.example.monefy.basic.functionality.UI.UpdateUIError;
import com.example.monefy.basic.functionality.fragment.billings.BillingDetailsFragment;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalanceFragment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalInputDate;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalInputText;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalFunctionalSelect;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Debt;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.model.billings.TypeDebtorSide;
import com.example.monefy.basic.functionality.fragment.dialogModal.TypeSelectModal;

import java.util.ArrayList;
import java.util.List;

public class DebtFragment extends Fragment {
    private final boolean isEditMode;
    private TextView tvTypeBillings, tvTypeCurrency, tvDebtAmount, tvDebtor, tvName, tvReturnDate;
    private TextView tvTitleDebtAmount,textTitleTypeBillings;
    private LinearLayout lirLatTypeBillings, lirLatTypeCurrency, lirDebtAmount, lirLatDebtor, lirLatName, lirLatReturnDate;
    private String argTypeBillings;
    private Debt debtBilling;
    private BillingDetailsFragment billingDetailsFragment;

    public DebtFragment(boolean isEditMode) {
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
                this.argTypeBillings = billing.getTypeBillings();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_debt, container, false);
       setupUIElements(view);
       setupClickListeners();
       setDataArguments();
       return view;
    }

    private void setupUIElements(View view) {
        tvTypeBillings = view.findViewById(R.id.type_billings);
        tvName = view.findViewById(R.id.name_billings);
        tvTypeCurrency = view.findViewById(R.id.type_currency);
        tvDebtAmount = view.findViewById(R.id.debt_amount);
        tvDebtor = view.findViewById(R.id.debtor);
        tvReturnDate = view.findViewById(R.id.returnDate);

        lirLatTypeBillings = view.findViewById(R.id.linerLayout_type_billings);
        lirLatName = view.findViewById(R.id.linerLayout_name_billings);
        lirLatTypeCurrency = view.findViewById(R.id.linerLayout_type_currency);
        lirDebtAmount = view.findViewById(R.id.liner_layout_debt_amount);
        lirLatDebtor = view.findViewById(R.id.linerLayout_debtor);
        lirLatReturnDate = view.findViewById(R.id.linerLayout_return_date);

        tvTitleDebtAmount = view.findViewById(R.id.text_view_debt_amount_new_billings);
        textTitleTypeBillings = view.findViewById(R.id.textTitleTypeBillings);
    }

    private void setDataArguments() {
        if(argTypeBillings != null){
            tvTypeBillings.setText(argTypeBillings);
        }

        if(debtBilling != null){
            tvTypeBillings.setText(debtBilling.getTypeBillings());
            tvTypeCurrency.setText(debtBilling.getTypeCurrency());
            tvDebtAmount.setText(String.valueOf(debtBilling.getBalance()));
            tvDebtor.setText(debtBilling.getDebtor());
            tvReturnDate.setText(
                    ManagerDate.convertFirebaseDateToLocalDate(
                            ManagerDate.convertFirebaseDateToString(
                                    debtBilling.getReturnDate()
                            )
                    )
            );
            tvName.setText(debtBilling.getName());
        }
    }

    private void setupClickListeners() {
        handlerClickLinerLayoutTypeBillings();
        handlerClickLinerLayoutTypeCurrency();
        handlerClickLinerLayoutDebtAmount();
        handlerClickLinerLayoutDebtor();
        handlerClickLinerLayoutName();
        handlerClickLinerLayoutReturnDate();
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
                        ManagerType.getTypesBillings(),
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
                    ManagerType.getTypeCurrency(),
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
                    });

            modalSelect.modalStart();
        });
    }

    private void handlerClickLinerLayoutDebtAmount(){
        lirDebtAmount.setOnClickListener(v -> {
            ModalBalanceFragment modalBalanceFragment = new ModalBalanceFragment();

            Bundle bundle = new Bundle();
            bundle.putString("titleModal",tvTitleDebtAmount.getText().toString());
            bundle.putString("balance", tvDebtAmount.getText().toString());
            bundle.putString("typeCurrency", tvTypeCurrency.getText().toString());
            bundle.putString("typeBillings", tvTypeBillings.getText().toString());

            modalBalanceFragment.setArguments(bundle);
            modalBalanceFragment.show(getChildFragmentManager(),"ModalBalanceFragment");
            modalBalanceFragment.checkDialogCallback(new DialogCallback() {
                @Override
                public void onSuccess(String data) {
                    tvDebtAmount.setText(data);
                    UpdateUI.resetStyleSelect(lirDebtAmount, getResources());
                    modalBalanceFragment.dismiss();
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutDebtor(){
        lirLatDebtor.setOnClickListener(v->{

            ModalFunctionalSelect modalSelect = new ModalFunctionalSelect(
                    getContext(),
                    R.string.textSelectDebtor,
                    ManagerType.getTypeDebtor(),
                    TypeDebtorSide.class,
                    new DialogCallback() {
                        @Override
                        public void onSuccess(String data) {
                            tvDebtor.setText(data);
                            UpdateUI.resetStyleSelect(lirLatDebtor, getResources());
                        }

                        @Override
                        public void onFailure(Exception exception) {

                        }
                    }
            );

            modalSelect.modalStart();
        });
    }

    private void handlerClickLinerLayoutReturnDate(){
        lirLatReturnDate.setOnClickListener(v -> {
            String data = debtBilling == null ? returnDate : ManagerDate.convertFirebaseDateToLocalDate(
                    ManagerDate.convertFirebaseDateToString(debtBilling.getReturnDate())
            );

            ModalInputDate modalInputDate = new ModalInputDate(
                    getContext(),
                    R.layout.modal_bottom_input_date,
                    data,
                    getResources(),
                    new DialogCallback() {
                        @Override
                        public void onSuccess(String data) {
                            tvReturnDate.setText(data);
                        }

                        @Override
                        public void onFailure(Exception exception) {

                        }
                    });

            modalInputDate.modalStart();
        });
    }

    public Billings getBilling(){
        if(checkInputData()){
            return new Debt(
                    Long.parseLong(debtAmount),
                    nameBillings,
                    typeBillings,
                    typeCurrency,
                    debtor,
                    ManagerDate.convertStringToDate(returnDate,ManagerDate.dateFormatLocalApp),
                    ManagerDate.getCurrentDateFormatFirebase()
            );
        }
        return null;
    }

    private String nameBillings;
    private String typeBillings;
    private String typeCurrency;
    private String debtAmount;
    private String debtor;
    private String returnDate;

    private void getValueAllInput(){
        nameBillings = tvName.getText().toString();
        typeBillings = tvTypeBillings.getText().toString();
        typeCurrency = tvTypeCurrency.getText().toString();
        debtAmount = tvDebtAmount.getText().toString().isEmpty() ? "0" : tvDebtAmount.getText().toString();
        debtor = tvDebtor.getText().toString();
        returnDate = tvReturnDate.getText().toString();
    }

    public boolean checkInputData(){
        getValueAllInput();
        if(typeBillings == null || typeBillings.isEmpty()){
            UpdateUIError.setStyleError(lirLatTypeBillings,getResources());
            return false;
        } else if (nameBillings == null || nameBillings.isEmpty()) {
            UpdateUIError.setStyleError(lirLatName,getResources());
            return false;
        } else if (typeCurrency == null || typeCurrency.isEmpty()) {
            UpdateUIError.setStyleError(lirLatTypeCurrency,getResources());
            return false;
        } else if (debtAmount == null || debtAmount.equals("0")) {
            UpdateUIError.setStyleError(lirDebtAmount,getResources());
            return false;
        } else if (debtor == null || debtor.isEmpty()) {
            UpdateUIError.setStyleError(lirLatDebtor,getResources());
            return false;
        } else if (returnDate == null || returnDate.isEmpty()) {
            UpdateUIError.setStyleError(lirLatReturnDate,getResources());
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