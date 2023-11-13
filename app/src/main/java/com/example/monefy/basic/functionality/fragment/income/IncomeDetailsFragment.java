package com.example.monefy.basic.functionality.fragment.income;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.UI.UpdateUI;
import com.example.monefy.basic.functionality.UI.UpdateUIError;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalInputData;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelect;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.example.monefy.basic.functionality.model.income.TypeCategory;
import com.example.monefy.basic.functionality.model.income.TypeFrequency;
import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IncomeDetailsFragment extends Fragment {

    private EditText etName, etNotes, etSource;

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
        this.view = inflater.inflate(R.layout.fragment_income_details, container, false);
        setupUIElements();
        handlerClickLinerLayout();
        checkFocusableEditText();
        return view;
    }

    private void setupUIElements(){
        etNotes = view.findViewById(R.id.etNotesIncome);
        etName = view.findViewById(R.id.etNameIncome);
        etSource = view.findViewById(R.id.etSourceIncome);
        tvAmount = view.findViewById(R.id.tVAmountIncome);
        tvValueAmount = view.findViewById(R.id.valueAmountIncome);
        tvValueTypeCurrency = view.findViewById(R.id.valueTypeCurrencyIncome);
        tvValueFrequency = view.findViewById(R.id.valueFrequencyIncome);
        tvValueCategory = view.findViewById(R.id.valueCategoryIncome);
        tvDateReceived = view.findViewById(R.id.tvDateReceivedIncome);
        tvValueDateReceived = view.findViewById(R.id.valueDateReceivedIncome);
        linerLayoutAmount = view.findViewById(R.id.linerLayoutAmountIncome);
        linearLayoutTypeCurrency = view.findViewById(R.id.linerLayoutTypeCurrency);
        linerLayoutFrequency = view.findViewById(R.id.linerLayoutFrequencyIncome);
        linerLayoutCategory = view.findViewById(R.id.linerLayoutCategoryIncome);
        linerLayoutDateReceived = view.findViewById(R.id.linerLayoutDateReceivedIncome);
    }

    private void handlerClickLinerLayout(){
        handlerClickLinerLayoutAmount();
        handlerClickLinearLayoutTypeCurrency();
        handlerClickLinearLayoutFrequency();
        handlerClickLinearLayoutCategory();
        handlerClickLinearLayoutData();
    }

    private void handlerClickLinearLayoutData() {
        linerLayoutDateReceived.setOnClickListener(v->{
            UpdateUI.resetStyleSelect(linerLayoutDateReceived,getResources());
            ModalInputData modalInputData = new ModalInputData(
                    getContext(),
                    R.layout.modal_bottom_input_data,
                    tvValueDateReceived.getText().toString(),
                    getResources()
            );
            modalInputData.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tvValueDateReceived.setText(modalInputData.getUpdateData());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutAmount(){
        linerLayoutAmount.setOnClickListener(v->{
            UpdateUI.resetStyleSelect(linerLayoutAmount,getResources());
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
            UpdateUI.resetStyleSelect(linearLayoutTypeCurrency, getResources());
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
            UpdateUI.resetStyleSelect(linerLayoutFrequency,getResources());
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
            UpdateUI.resetStyleSelect(linerLayoutCategory, getResources());
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

    public Map<String, Object> getDataMapIncome(){

        Map<String, Object> incomeMap = new HashMap<>();

        if(checkInputData()){
            incomeMap.put("amount",Long.parseLong(tvValueAmount.getText().toString()));
            incomeMap.put("category", tvValueCategory.getText().toString());
            incomeMap.put("dateReceived", convertTimestamp(tvValueDateReceived.getText().toString()));
            incomeMap.put("frequency",tvValueFrequency.getText().toString());
            incomeMap.put("name",etName.getText().toString());
            incomeMap.put("notes", etNotes.getText().toString());
            incomeMap.put("source", etSource.getText().toString());
            incomeMap.put("typeCurrency",tvValueTypeCurrency.getText().toString());
        }

        return incomeMap;
    }

    private Date convertData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Timestamp convertTimestamp(String stringData){
        Date date = convertData(stringData);

         assert date != null;

        return new Timestamp(date);
    }

    private boolean checkInputData(){
        boolean amount =  checkSelectDataIsEmpty(tvValueAmount.getText().toString(), linerLayoutAmount);
        boolean category = checkSelectDataIsEmpty(tvValueCategory.getText().toString(), linerLayoutCategory);
        boolean frequency = checkSelectDataIsEmpty(tvValueFrequency.getText().toString(), linerLayoutFrequency);
        boolean typeCurrency = checkSelectDataIsEmpty(tvValueTypeCurrency.getText().toString(),linearLayoutTypeCurrency);
        boolean dateReceived = checkSelectDataIsEmpty(tvValueDateReceived.getText().toString(),linerLayoutDateReceived);
        boolean name = checkSelectDataIsEmpty(etName.getText().toString(), etName);
        boolean notes = checkSelectDataIsEmpty(etNotes.getText().toString(), etNotes);
        boolean source = checkSelectDataIsEmpty(etSource.getText().toString(), etSource);

        return name && amount && typeCurrency && frequency && category && dateReceived && source && notes;
    }

    private boolean checkSelectDataIsEmpty(String data, Object object){
        if(object instanceof LinearLayout){
            if(data.isEmpty()){
                UpdateUIError.setStyleError((LinearLayout) object,getResources());
                return false;
            }else{
                UpdateUI.resetStyleSelect((LinearLayout) object, getResources());
            }
        }else if (object instanceof EditText){
            if(data.isEmpty()){
               UpdateUIError.setStyleError((EditText) object, getResources());
               return false;
            }else{
                UpdateUI.resetStyleSelect((EditText) object, getResources());
            }
        }
        return true;
    }

    private void checkFocusableEditText(){
        if(etName.isClickable()){
            UpdateUI.resetStyleSelect(etName,getResources());
        }
        if(etSource.isCursorVisible()){
            UpdateUI.resetStyleSelect(etSource,getResources());
        }
        if(etNotes.isAccessibilityFocused()){
            UpdateUI.resetStyleSelect(etNotes,getResources());
        }
    }
}