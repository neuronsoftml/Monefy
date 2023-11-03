package com.example.monefy.basic.functionality.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeCurrency;
import com.example.monefy.basic.functionality.model.Obligation;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.example.monefy.tools.firebase.InConclusionCompleteListener;
import com.example.monefy.tools.message.ToastManager;
import com.google.firebase.firestore.FirebaseFirestore;


public class CreateBillingsFragment extends Fragment {
    private String argTypeBillings;
    private TextView tVTypeBillings, tViewTypeCurrency, tVBalanceBillings, tVCreditLimit;
    private  TextView tVTitleBalanceBillings, tVTitleCreditLimit;
    private LinearLayout linerLayoutTypeBillings, linerLayoutTypeCurrency, linerLayoutBalanceBillings, linerLayoutCreditLimit;
    private EditText editTextNameBillings;
    private ImageButton imgBtnClose, imgBtnSetUp;
    private ImageView imgViewCreditCart;
    private ConstraintLayout constraintLayoutPanelTop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            argTypeBillings = getArguments().getString("TypeBillings");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_billings, container, false);
        setupUIElements(view);
        switcherStyleInterface(argTypeBillings);
        setupClickListeners();
        return view;
    }

    private void setupUIElements(View view) {
        tVTypeBillings = view.findViewById(R.id.type_billings);
        tViewTypeCurrency = view.findViewById(R.id.text_type_currency);
        tVBalanceBillings = view.findViewById(R.id.balance_billings);
        tVCreditLimit = view.findViewById(R.id.credit_limit);
        linerLayoutTypeBillings = view.findViewById(R.id.linerLayout_type_billings);
        linerLayoutTypeCurrency = view.findViewById(R.id.linerLayout_type_currency);
        linerLayoutBalanceBillings = view.findViewById(R.id.linerLayout_balance_billings);
        linerLayoutCreditLimit = view.findViewById(R.id.liner_layout_credit_limit);
        editTextNameBillings = view.findViewById(R.id.editText_name_billings);
        imgBtnClose = view.findViewById(R.id.imageButton_close);
        imgBtnSetUp = view.findViewById(R.id.imageButtonSetUp);
        imgViewCreditCart = view.findViewById(R.id.imageViewCreditCartTypeBillings);
        constraintLayoutPanelTop = view.findViewById(R.id.constraintLayout_create_billings);
        tVTitleBalanceBillings = view.findViewById(R.id.textView_balance_create_billings);
        tVTitleCreditLimit = view.findViewById(R.id.text_view_credit_limit_new_billings);
    }

    private void setupClickListeners() {
        handlerClickLinerLayoutTypeBillings();
        handlerClickLinerLayoutTypeCurrency();
        handlerClickLinerLayoutBalance();
        handlerClickLinerLayoutCreditLimit();

        handlerClickImageButtonClose();
        handlerClickImageButtonSetUp();
    }

    private void handlerClickImageButtonClose() {
        imgBtnClose.setOnClickListener(v ->{
            FragmentSwitcher.replaceFragmentBack(getContext());
        });
    }

    private void handlerClickImageButtonSetUp() {
        imgBtnSetUp.setOnClickListener(v->{
            String nameBillings = editTextNameBillings.getText().toString();
            String typeBillings = tVTypeBillings.getText().toString();
            String typeCurrency = tViewTypeCurrency.getText().toString();
            String balance = tVBalanceBillings.getText().toString().isEmpty() ? "0" : tVBalanceBillings.getText().toString();
            String creditLimit = tVCreditLimit.getText().toString().isEmpty() ? "0" : tVCreditLimit.getText().toString();

            if(checkInputTypeCurrency(typeCurrency)){
                FirebaseManager.addBilling(
                        FirebaseFirestore.getInstance(),
                        AuthenticationManager.getAuthenticationManager().getUserId(),
                        Long.parseLong(balance),
                        Long.parseLong(creditLimit),
                        nameBillings,
                        typeBillings,
                        typeCurrency,
                        setObligation(typeBillings),
                        new InConclusionCompleteListener() {
                            @Override
                            public void onSuccess() {
                                ToastManager.showToastOnSuccessful(getContext(),R.string.toast_successful_entered_the_data);
                                FragmentSwitcher.replaceFragmentBack(getContext());
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                ToastManager.showToastOnFailure(getContext(),R.string.toast_failure_entered_the_data);
                            }
                        }
                );
            }
        });
    }

    private String setObligation(String typeBillings) {
        if(typeBillings.equals(TypeBillings.ORDINARY.getTypeBillingsTitle())){
            return Obligation.CREDIT_LIMIT.getTitle();
        }

        else if(typeBillings.equals(TypeBillings.DEBT.getTypeBillingsTitle())){
            return tVTitleBalanceBillings.getText().toString();
        }
        else if (typeBillings.equals(TypeBillings.CUMULATIVE.getTypeBillingsTitle())) {
            return Obligation.GOAL.getTitle();
        }
        return "";
    }

    private void handlerClickLinerLayoutTypeBillings(){
        linerLayoutTypeBillings.setOnClickListener(v -> {
            ModalTypeBillings modalTypeBillings = new ModalTypeBillings(getContext());
            modalTypeBillings.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    switcherStyleInterface(modalTypeBillings.getUpdateData());
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
            modalTypeCurrency.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tViewTypeCurrency.setText(modalTypeCurrency.getUpdateData());
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
                    tVTitleBalanceBillings.getText().toString(),
                    tVBalanceBillings.getText().toString(),
                    tViewTypeCurrency.getText().toString(),
                    tVTypeBillings.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tVBalanceBillings.setText(modalBalance.getUpdateBalance());
                    if(modalBalance.getUpdateToWhomHeOwes() != null){
                        tVTitleBalanceBillings.setText(modalBalance.getUpdateToWhomHeOwes());
                    }
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
                    tVTitleCreditLimit.getText().toString(),
                    tVCreditLimit.getText().toString(),
                    tViewTypeCurrency.getText().toString(),
                    tVTypeBillings.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    tVCreditLimit.setText(modalBalance.getUpdateBalance());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private boolean checkInputTypeCurrency(String type){
        return type != null && !type.isEmpty();
    }

    private void switcherStyleInterface(String argumentTypeBillings){
        if(argumentTypeBillings.equals(TypeBillings.ORDINARY.getTypeBillingsTitle())){
            typeOrdinaryBillingsStyle();
        }else if(argumentTypeBillings.equals(TypeBillings.DEBT.getTypeBillingsTitle())){
            typeDebtBillingsStyle();
        } else if (argumentTypeBillings.equals(TypeBillings.CUMULATIVE.getTypeBillingsTitle())) {
            typeAccumulativeBillingsStyle();
        }
    }

    private void typeOrdinaryBillingsStyle(){
        tVTypeBillings.setText(TypeBillings.ORDINARY.getTypeBillingsTitle());
        imgViewCreditCart.setImageResource(R.drawable.icon_credit_card_blue);
        constraintLayoutPanelTop.setBackgroundColor(getResources().getColor(R.color.blue));
        tVTitleBalanceBillings.setText(R.string.tV_balance_billing);
        tVTitleCreditLimit.setText(R.string.tV_credit_limit);
    }

    private void typeDebtBillingsStyle(){
        tVTypeBillings.setText(TypeBillings.DEBT.getTypeBillingsTitle());
        imgViewCreditCart.setImageResource(R.drawable.icon_credit_card_red);
        constraintLayoutPanelTop.setBackgroundColor(getResources().getColor(R.color.red));
        tVTitleBalanceBillings.setText(R.string.tV_select_billings_debt_i_must);
        tVTitleCreditLimit.setText(R.string.tV_select_billings_total_amount_of_debt);
    }

    private void typeAccumulativeBillingsStyle(){
        tVTypeBillings.setText(TypeBillings.CUMULATIVE.getTypeBillingsTitle());
        imgViewCreditCart.setImageResource(R.drawable.icon_credit_card_gold);
        constraintLayoutPanelTop.setBackgroundColor(getResources().getColor(R.color.gold));
        tVTitleBalanceBillings.setText(R.string.tV_balance_billing);
        tVTitleCreditLimit.setText(R.string.tV_select_billings_objective);
    }
}