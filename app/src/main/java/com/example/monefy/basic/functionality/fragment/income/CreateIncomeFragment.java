package com.example.monefy.basic.functionality.fragment.income;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelect;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.example.monefy.basic.functionality.model.income.TypeCategory;
import com.example.monefy.basic.functionality.model.income.TypeFrequency;

import java.util.Arrays;

public class CreateIncomeFragment extends Fragment {

    private EditText etName, etNotes, etSource;
    private ImageButton imgBtnClose, imgBtnSetUp;
    private LinearLayout linerLayoutAmount, linearLayoutTypeCurrency,
            linerLayoutFrequency, linerLayoutCategory, linerLayoutDateReceived;
    private TextView tvAmount, tvValueAmount, tvValueTypeCurrency,
             tvValueFrequency, tvValueCategory, tvDateReceived, tvValueDateReceived;

    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view =  inflater.inflate(R.layout.fragment_create_income, container, false);

        setupUIElements();
        handlerClickLinerLayout();
        return view;
    }

    private void setupUIElements(){
        this.etNotes = view.findViewById(R.id.etNotesIncome);
        this.etName = view.findViewById(R.id.etNameIncome);
        this.etSource = view.findViewById(R.id.etSourceIncome);

        this.tvAmount = view.findViewById(R.id.tVAmountIncome);
        this.tvValueAmount = view.findViewById(R.id.valueAmountIncome);
        this.tvValueTypeCurrency = view.findViewById(R.id.valueTypeCurrencyIncome);
        this.tvValueFrequency = view.findViewById(R.id.valueFrequencyIncome);
        this.tvValueCategory = view.findViewById(R.id.valueCategoryIncome);
        this.tvDateReceived = view.findViewById(R.id.tvDateReceivedIncome);
        this.tvValueDateReceived = view.findViewById(R.id.valueDateReceivedIncome);

        this.linerLayoutAmount = view.findViewById(R.id.linerLayoutAmountIncome);
        this.linearLayoutTypeCurrency = view.findViewById(R.id.linerLayoutTypeCurrency);
        this.linerLayoutFrequency = view.findViewById(R.id.linerLayoutFrequencyIncome);
        this.linerLayoutCategory = view.findViewById(R.id.linerLayoutCategoryIncome);
        this.linerLayoutDateReceived = view.findViewById(R.id.linerLayoutDateReceivedIncome);
    }

    private void handlerClickLinerLayout(){
        handlerClickLinerLayoutAmount();
        handlerClickLinearLayoutTypeCurrency();
        handlerClickLinearLayoutFrequency();
        handlerClickLinearLayoutCategory();
        handlerClickBtnClose();
    }

    private void handlerClickBtnClose(){
        imgBtnClose.setOnClickListener(v->{
            FragmentSwitcher.replaceFragmentBack(getContext());
        });
    }

    private void handlerClickBtnSetUp(){
        imgBtnSetUp.setOnClickListener(v->{

        });
    }

    private void handlerClickLinerLayoutAmount(){
        linerLayoutAmount.setOnClickListener(v->{
            ModalBalance modalBalance = new ModalBalance(
                    getContext(),
                    view,
                    tvAmount.getText().toString(),
                    tvValueAmount.getText().toString(),
                    tvValueTypeCurrency.getText().toString(),
                    null
            );
            modalBalance.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tvValueAmount.setText(modalBalance.getUpdateBalance());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinearLayoutTypeCurrency() {
        linearLayoutTypeCurrency.setOnClickListener(v->{
            ModalSelect modalSelect = new ModalSelect(
                    getContext(),
                    R.string.tV_modal_select_type_currencies,
                    Arrays.asList(TypeCurrency.values())
            );
            modalSelect.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tvValueTypeCurrency.setText(modalSelect.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinearLayoutFrequency(){
        linerLayoutFrequency.setOnClickListener(v->{
            ModalSelect modalSelect = new ModalSelect(
                    getContext(),
                    R.string.tV_modal_select_frequency,
                    Arrays.asList(TypeFrequency.values())
            );
            modalSelect.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tvValueFrequency.setText(modalSelect.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinearLayoutCategory(){
        linerLayoutCategory.setOnClickListener(v->{
            ModalSelect modalSelect = new ModalSelect(
                    getContext(),
                    R.string.tV_modal_select_category,
                   Arrays.asList(TypeCategory.values())
            );

            modalSelect.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tvValueCategory.setText(modalSelect.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }
}