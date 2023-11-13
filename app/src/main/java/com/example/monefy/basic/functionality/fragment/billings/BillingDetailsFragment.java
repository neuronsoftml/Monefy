package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.UI.UpdateUI;
import com.example.monefy.basic.functionality.UI.UpdateUIError;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelect;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Obligation;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BillingDetailsFragment extends Fragment {
    private String argTypeBillings;
    private TextView tvTypeBillings, tvTypeCurrency, tVBalance, tvCreditLimit;
    private  TextView tvTitleBalanceBillings, tvTitleCreditLimit;
    private LinearLayout lirLatTypeBillings, lirLatTypeCurrency, lirLatBalance, lirLatCreditLimit;
    private EditText editTextName;
    private ImageView imgViewCreditCart;
    private ConstraintLayout consLatPanelTop;
    private Billings billing;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            argTypeBillings = bundle.getString("TypeBillings");
            Billings billing = (Billings) bundle.getSerializable("billing");
            if (billing != null) {
                this.billing = billing;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billings_details, container, false);

        setupUIElements(view);
        setupClickListeners();
        setDataArguments();
        return view;
    }

    private void setupUIElements(View view) {
        tvTypeBillings = view.findViewById(R.id.type_billings);
        tvTypeCurrency = view.findViewById(R.id.text_type_currency);
        tVBalance = view.findViewById(R.id.balance_billings);
        tvCreditLimit = view.findViewById(R.id.credit_limit);
        lirLatTypeBillings = view.findViewById(R.id.linerLayout_type_billings);
        lirLatTypeCurrency = view.findViewById(R.id.linerLayout_type_currency);
        lirLatBalance = view.findViewById(R.id.linerLayout_balance_billings);
        lirLatCreditLimit = view.findViewById(R.id.liner_layout_credit_limit);
        editTextName = view.findViewById(R.id.eTxtNameBilling);
        tvTitleBalanceBillings = view.findViewById(R.id.textView_balance_create_billings);
        tvTitleCreditLimit = view.findViewById(R.id.text_view_credit_limit_new_billings);
    }

    private void setDataArguments() {
        if(argTypeBillings != null){
            tvTypeBillings.setText(argTypeBillings);
            updateUI(argTypeBillings);
        }

        if(billing != null){
            editTextName.setText(billing.getName());
            tvTypeBillings.setText(billing.getTypeBillings());
            tvTypeCurrency.setText(billing.getTypeCurrency());
            tVBalance.setText(String.valueOf(billing.getBalance()));
            tvCreditLimit.setText(String.valueOf(billing.getCreditLimit()));
        }
    }

    private void setupClickListeners() {
        handlerClickLinerLayoutTypeBillings();
        handlerClickLinerLayoutTypeCurrency();
        handlerClickLinerLayoutBalance();
        handlerClickLinerLayoutCreditLimit();
    }

    private void handlerClickLinerLayoutTypeBillings(){
        lirLatTypeBillings.setOnClickListener(v -> {
            ModalTypeBillings modalTypeBillings = new ModalTypeBillings(getContext());
            modalTypeBillings.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    updateUI(modalTypeBillings.getUpdateData());
                    UpdateUI.resetStyleSelect(lirLatTypeBillings, getResources());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutTypeCurrency(){
        lirLatTypeCurrency.setOnClickListener(v -> {
            ModalSelect modalSelect = new ModalSelect(
                    getContext(),
                    R.string.tV_modal_select_type_currencies,
                    Arrays.asList(TypeCurrency.values()));
            modalSelect.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tvTypeCurrency.setText(modalSelect.getUpdateData());
                    UpdateUI.resetStyleSelect(lirLatTypeCurrency, getResources());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutBalance(){
        lirLatBalance.setOnClickListener(v -> {
            ModalBalance modalBalance = new ModalBalance(
                    getContext(), getView(),
                    tvTitleBalanceBillings.getText().toString(),
                    tVBalance.getText().toString(),
                    tvTypeCurrency.getText().toString(),
                    tvTypeBillings.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tVBalance.setText(modalBalance.getUpdateBalance());
                    if(modalBalance.getUpdateToWhomHeOwes() != null){
                        tvTitleBalanceBillings.setText(modalBalance.getUpdateToWhomHeOwes());
                    }
                    UpdateUI.resetStyleSelect(lirLatBalance, getResources());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void handlerClickLinerLayoutCreditLimit(){
        lirLatCreditLimit.setOnClickListener(v ->{
            ModalBalance modalBalance = new ModalBalance(
                    getContext(),getView(),
                    tvTitleCreditLimit.getText().toString(),
                    tvCreditLimit.getText().toString(),
                    tvTypeCurrency.getText().toString(),
                    tvTypeBillings.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tvCreditLimit.setText(modalBalance.getUpdateBalance());
                    UpdateUI.resetStyleSelect(lirLatCreditLimit, getResources());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void updateUI(String argumentTypeBillings){
        if(argumentTypeBillings.equals(TypeBillings.ORDINARY.getTitle())){
            typeOrdinaryBillingsStyle();
        }else if(argumentTypeBillings.equals(TypeBillings.DEBT.getTitle())){
            typeDebtBillingsStyle();
        } else if (argumentTypeBillings.equals(TypeBillings.CUMULATIVE.getTitle())) {
            typeAccumulativeBillingsStyle();
        }
    }

    private void typeOrdinaryBillingsStyle(){
        tvTypeBillings.setText(TypeBillings.ORDINARY.getTitle());
        imgViewCreditCart.setImageResource(R.drawable.icon_credit_card_blue);
        consLatPanelTop.setBackground(getResources().getDrawable(R.drawable.bg_gradient_blue));
        tvTitleBalanceBillings.setText(R.string.tV_balance_billing);
        tvTitleCreditLimit.setText(R.string.tV_credit_limit);
    }

    private void typeDebtBillingsStyle(){
        tvTypeBillings.setText(TypeBillings.DEBT.getTitle());
        imgViewCreditCart.setImageResource(R.drawable.icon_credit_card_red);
        consLatPanelTop.setBackground(getResources().getDrawable(R.drawable.bg_gradient_red));
        tvTitleBalanceBillings.setText(R.string.tV_select_billings_debt_i_must);
        tvTitleCreditLimit.setText(R.string.tV_select_billings_total_amount_of_debt);
    }

    private void typeAccumulativeBillingsStyle(){
        tvTypeBillings.setText(TypeBillings.CUMULATIVE.getTitle());
        imgViewCreditCart.setImageResource(R.drawable.icon_credit_card_gold);
        consLatPanelTop.setBackground(getResources().getDrawable(R.drawable.bg_gradient_gold));
        tvTitleBalanceBillings.setText(R.string.tV_balance_billing);
        tvTitleCreditLimit.setText(R.string.tV_select_billings_objective);
    }

    public Map<String, Object> getBillingData(){
        Map<String, Object> billing = new HashMap<>();

        if(checkInputData()){
            billing.put("name", nameBillings);
            billing.put("typeBillings", typeBillings);
            billing.put("typeCurrency", typeCurrency);
            billing.put("creditLimit", Long.parseLong(creditLimit));
            billing.put("balance",Long.parseLong(balance));
            billing.put("obligation", setObligation(typeBillings));
        }

        return billing;
    }

    String nameBillings;
    String typeBillings;
    String typeCurrency;
    String balance;
    String creditLimit;

    private void getValueAllInput(){
        nameBillings = editTextName.getText().toString();
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

    public void setImgViewCreditCart(ImageView imgViewCreditCart) {
        this.imgViewCreditCart = imgViewCreditCart;
    }

    public void setConsLatPanelTop(ConstraintLayout consLatPanelTop) {
        this.consLatPanelTop = consLatPanelTop;
    }

    public TextView getTvTitleBalanceBillings() {
        return tvTitleBalanceBillings;
    }

    //Встановлення значення облігації
    private String setObligation(String typeBillings) {
        if(typeBillings.equals(TypeBillings.ORDINARY.getTitle())){
            return Obligation.CREDIT_LIMIT.getTitle();
        }

        else if(typeBillings.equals(TypeBillings.DEBT.getTitle())){
            return getTvTitleBalanceBillings().getText().toString();
        }
        else if (typeBillings.equals(TypeBillings.CUMULATIVE.getTitle())) {
            return Obligation.GOAL.getTitle();
        }
        return "";
    }
}