package com.example.monefy.basic.functionality.fragment.billings;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeCurrency;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.Obligation;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.example.monefy.tools.firebase.InConclusionCompleteListener;
import com.example.monefy.tools.message.ToastManager;

public class EditBillingsFragment extends Fragment {

    private Billings billing;
    private TextView textViewTypeBillings, textViewTypeCurrency, textViewBalanceBillings, textViewCreditLimit;
    private TextView tVTitleBalanceBillings, textViewTitleCreditLimit;
    private LinearLayout linerLayoutTypeBillings, linerLayoutTypeCurrency, linerLayoutBalanceBillings, linerLayoutCreditLimit;
    private EditText editTextNameBillings;
    private ImageButton imageButtonClose, imageButtonSetUp;
    private ImageView imageViewCreditCartTypeBillings;
    private ConstraintLayout constraintLayoutPanelTop;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Billings billing = (Billings) bundle.getSerializable("billing");
            if (billing != null) {
                this.billing = billing;
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_billings, container, false);
        setupUIElements(view);
        setValueObject();
        switcherStyleInterface(billing.getTypeBillings());
        setupClickListeners();
        return view;
    }

    private void setupUIElements(View view){
        textViewTypeBillings = view.findViewById(R.id.type_billings_fragment_edit_billings);
        textViewTypeCurrency = view.findViewById(R.id.text_type_currency_fragment_edit_billings);
        textViewBalanceBillings = view.findViewById(R.id.balance_billings_fragment_edit_billings);
        textViewCreditLimit = view.findViewById(R.id.credit_limit_fragment_edit_billings);
        linerLayoutTypeBillings = view.findViewById(R.id.linerLayout_type_billings_fragment_edit_billings);
        linerLayoutTypeCurrency = view.findViewById(R.id.linerLayout_type_currency_fragment_edit_billings);
        linerLayoutBalanceBillings = view.findViewById(R.id.linerLayout_balance_billings_fragment_edit_billings);
        linerLayoutCreditLimit = view.findViewById(R.id.liner_layout_credit_limit_fragment_edit_billings);
        editTextNameBillings = view.findViewById(R.id.editText_name_billings_fragment_edit_billings);
        imageButtonClose = view.findViewById(R.id.imageButton_close_fragment_edit_billings);
        imageButtonSetUp = view.findViewById(R.id.imageButtonSetUp_fragment_edit_billings);
        imageViewCreditCartTypeBillings = view.findViewById(R.id.imageViewCreditCartTypeBillings_fragment_edit_billings);
        constraintLayoutPanelTop = view.findViewById(R.id.constraintLayout_create_billings_fragment_edit_billings);
        tVTitleBalanceBillings = view.findViewById(R.id.tV_balance_fragment_edit_billings);
        textViewTitleCreditLimit = view.findViewById(R.id.text_view_credit_limit_fragment_edit_billings);

    }

    private void setValueObject(){
        if(billing.getTypeBillings().equals(TypeBillings.DEBT.getTitle())){
            tVTitleBalanceBillings.setText(billing.getObligation());
        }
        textViewTypeBillings.setText(billing.getTypeBillings());
        textViewTypeCurrency.setText(billing.getTypeCurrency());
        textViewBalanceBillings.setText(String.valueOf(billing.getBalance()));
        textViewCreditLimit.setText(String.valueOf(billing.getCreditLimit()));
        editTextNameBillings.setText(billing.getName());
        imageViewCreditCartTypeBillings.setImageResource(TypeBillings.getIdImageTypeBillings(billing.getTypeBillings()));
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
        imageButtonClose.setOnClickListener(v ->{
           FragmentSwitcher.replaceFragmentBack(getContext());
        });
    }

    private void handlerClickImageButtonSetUp() {
        context = getContext();
        imageButtonSetUp.setOnClickListener(v->{
            String nameBillings = editTextNameBillings.getText().toString();
            String typeBillings = textViewTypeBillings.getText().toString();
            String typeCurrency = textViewTypeCurrency.getText().toString();
            String balance = textViewBalanceBillings.getText().toString().isEmpty() ? "0" : textViewBalanceBillings.getText().toString();
            String creditLimit = textViewCreditLimit.getText().toString().isEmpty() ? "0" : textViewCreditLimit.getText().toString();

            Log.d("obligation", getObligation(typeBillings));
            Billings updatesBillings = new Billings(
                    Long.parseLong(balance),
                    Long.parseLong(creditLimit),
                    nameBillings,
                    typeBillings,
                    typeCurrency,
                    getObligation(typeBillings)
            );

            updatesBillings.setId(billing.getId());

            if(!updatesBillings.equals(billing)){
                FirebaseManager.updatedBillings(
                        AuthenticationManager.getAuthenticationManager().getUserId(),
                        updatesBillings,
                        new InConclusionCompleteListener() {
                            @Override
                            public void onSuccess() {
                                ToastManager.showToastOnSuccessful(context,R.string.toast_successful_edit_billings);
                                FragmentSwitcher.replaceFragment(
                                        new BillingsFragment(),
                                        ((FragmentActivity) context).getSupportFragmentManager(),
                                        FragmentSwitcher.getContainerHome()
                                );
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                ToastManager.showToastOnFailure(context,R.string.toast_failure_edit_billings);
                                FragmentSwitcher.replaceFragmentBack(context);
                            }
                        }

                );
            }
            FragmentSwitcher.replaceFragmentBack(context);

        });
    }

    private String getObligation(String typeBillings) {
        if(typeBillings.equals(TypeBillings.ORDINARY.getTitle())){
            return Obligation.CREDIT_LIMIT.getTitle();
        }

        else if(typeBillings.equals(TypeBillings.DEBT.getTitle())){
            return tVTitleBalanceBillings.getText().toString();
        }
        else if (typeBillings.equals(TypeBillings.CUMULATIVE.getTitle())) {
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
                    tVTitleBalanceBillings.getText().toString(),
                    textViewBalanceBillings.getText().toString(),
                    textViewTypeCurrency.getText().toString(),
                    textViewTypeBillings.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    textViewBalanceBillings.setText(modalBalance.getUpdateBalance());
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
                    textViewTitleCreditLimit.getText().toString(),
                    textViewCreditLimit.getText().toString(),
                    textViewTypeCurrency.getText().toString(),
                    textViewTypeBillings.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    textViewCreditLimit.setText(modalBalance.getUpdateBalance());
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void switcherStyleInterface(String argumentTypeBillings){
        if(argumentTypeBillings.equals(TypeBillings.ORDINARY.getTitle())){
            typeOrdinaryBillingsStyle();
        }else if(argumentTypeBillings.equals(TypeBillings.DEBT.getTitle())){
            typeDebtBillingsStyle();
        } else if (argumentTypeBillings.equals(TypeBillings.CUMULATIVE.getTitle())) {
            typeAccumulativeBillingsStyle();
        }
    }

    private void typeOrdinaryBillingsStyle(){
        textViewTypeBillings.setText(TypeBillings.ORDINARY.getTitle());
        imageViewCreditCartTypeBillings.setImageResource(R.drawable.icon_credit_card_blue);
        constraintLayoutPanelTop.setBackgroundColor(getResources().getColor(R.color.blue));
        tVTitleBalanceBillings.setText(R.string.tV_balance_billing);
        textViewTitleCreditLimit.setText(R.string.tV_credit_limit);
    }

    private void typeDebtBillingsStyle(){
        textViewTypeBillings.setText(TypeBillings.DEBT.getTitle());
        imageViewCreditCartTypeBillings.setImageResource(R.drawable.icon_credit_card_red);
        constraintLayoutPanelTop.setBackgroundColor(getResources().getColor(R.color.red));
        textViewTitleCreditLimit.setText(R.string.tV_select_billings_total_amount_of_debt);
    }

    private void typeAccumulativeBillingsStyle(){
        textViewTypeBillings.setText(TypeBillings.CUMULATIVE.getTitle());
        imageViewCreditCartTypeBillings.setImageResource(R.drawable.icon_credit_card_gold);
        constraintLayoutPanelTop.setBackgroundColor(getResources().getColor(R.color.gold));
        tVTitleBalanceBillings.setText(R.string.tV_balance_billing);
        textViewTitleCreditLimit.setText(R.string.tV_select_billings_objective);
    }

}