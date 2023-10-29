package com.example.monefy.basic.functionality.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeCurrency;



public class CreateBillingsFragment extends Fragment {
    private String argumentTypeBillings;
    private TextView textViewTypeBillings, textViewTypeCurrency, textViewBalanceBillings, textViewCreditLimit;
    private LinearLayout linerLayoutTypeBillings, linerLayoutTypeCurrency, linerLayoutBalanceBillings, linerLayoutCreditLimit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            argumentTypeBillings = getArguments().getString("TypeBillings");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_billings, container, false);
        setupUIElements(view);
        textViewTypeBillings.setText(argumentTypeBillings);
        setupClickListeners();
        return view;
    }

    private void setupUIElements(View view) {
        textViewTypeBillings = view.findViewById(R.id.type_billings);
        textViewTypeCurrency = view.findViewById(R.id.text_type_currency);
        textViewBalanceBillings = view.findViewById(R.id.balance_billings);
        textViewCreditLimit = view.findViewById(R.id.credit_limit);
        linerLayoutTypeBillings = view.findViewById(R.id.linerLayout_type_billings);
        linerLayoutTypeCurrency = view.findViewById(R.id.linerLayout_type_currency);
        linerLayoutBalanceBillings = view.findViewById(R.id.linerLayout_balance_billings);
        linerLayoutCreditLimit = view.findViewById(R.id.liner_layout_credit_limit);
    }

    private void setupClickListeners() {
        handlerClickLinerLayoutTypeBillings();
        handlerClickLinerLayoutTypeCurrency();
        handlerClickLinerLayoutBalance();
        handlerClickLinerLayoutCreditLimit();
    }

    private void handlerClickLinerLayoutTypeBillings(){
        linerLayoutTypeBillings.setOnClickListener(v -> {
            ModalTypeBillings modalTypeBillings = new ModalTypeBillings(getContext());
            modalTypeBillings.modalStart(new ModalTypeBillings.InConclusionCompleteListener() {
                @Override
                public void onSuccess() {
                    textViewTypeBillings.setText(modalTypeBillings.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutTypeCurrency(){
        linerLayoutTypeCurrency.setOnClickListener(v -> {
            ModalTypeCurrency modalTypeCurrency = new ModalTypeCurrency(getContext());
            modalTypeCurrency.modalStart(new ModalTypeCurrency.InConclusionCompleteListener() {
                @Override
                public void onSuccess() {
                    textViewTypeCurrency.setText(modalTypeCurrency.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutBalance(){
        linerLayoutBalanceBillings.setOnClickListener(v -> {
            ModalBalance modalBalance = new ModalBalance(
                    getContext(), getView(),
                    R.string.text_modal_view_select_billings_balance,
                    textViewBalanceBillings.getText().toString());
            modalBalance.modalStart(new ModalBalance.InConclusionCompleteListener() {
                @Override
                public void onSuccess() {
                    textViewBalanceBillings.setText(modalBalance.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutCreditLimit(){
        linerLayoutCreditLimit.setOnClickListener(v ->{
            ModalBalance modalBalance = new ModalBalance(
                    getContext(),getView(),
                    R.string.text_modal_view_select_billings_credit_limit,
                    textViewCreditLimit.getText().toString());
            modalBalance.modalStart(new ModalBalance.InConclusionCompleteListener() {
                @Override
                public void onSuccess() {
                    textViewCreditLimit.setText(modalBalance.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

}