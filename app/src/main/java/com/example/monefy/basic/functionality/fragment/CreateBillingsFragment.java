package com.example.monefy.basic.functionality.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeCurrency;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.example.monefy.tools.firebase.InConclusionCompleteListener;
import com.example.monefy.tools.message.ToastManager;
import com.google.firebase.firestore.FirebaseFirestore;


public class CreateBillingsFragment extends Fragment {
    private String argumentTypeBillings;
    private TextView textViewTypeBillings, textViewTypeCurrency, textViewBalanceBillings, textViewCreditLimit;
    private LinearLayout linerLayoutTypeBillings, linerLayoutTypeCurrency, linerLayoutBalanceBillings, linerLayoutCreditLimit;
    private EditText editTextNameBillings;
    private ImageButton imageButtonClose, imageButtonSetUp;

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

        editTextNameBillings = view.findViewById(R.id.editText_name_billings);

        imageButtonClose = view.findViewById(R.id.imageButton_close);
        imageButtonSetUp = view.findViewById(R.id.imageButtonSetUp);
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
            replaceFragmentBack();
        });
    }

    private void handlerClickImageButtonSetUp() {
        imageButtonSetUp.setOnClickListener(v->{
            String nameBillings = editTextNameBillings.getText().toString();
            String typeBillings = textViewTypeBillings.getText().toString();
            String typeCurrency = textViewTypeCurrency.getText().toString();
            String balance = textViewBalanceBillings.getText().toString().isEmpty() ? "0" : textViewBalanceBillings.getText().toString();
            String creditLimit = textViewCreditLimit.getText().toString().isEmpty() ? "0" : textViewCreditLimit.getText().toString();

            if(checkInputTypeCurrency(typeCurrency)){
                FirebaseManager.addBilling(
                        FirebaseFirestore.getInstance(),
                        AuthenticationManager.getAuthenticationManager().getUserId(),
                        Long.parseLong(balance),
                        Long.parseLong(creditLimit),
                        nameBillings,
                        typeBillings,
                        typeCurrency,
                        new InConclusionCompleteListener() {
                            @Override
                            public void onSuccess() {
                                ToastManager.showToastOnSuccessful(getContext(),R.string.toast_text_message_successful_entered_the_data);
                                replaceFragmentBack();
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                ToastManager.showToastOnFailure(getContext(),R.string.toast_text_message_failure_entered_the_data);
                            }
                        }
                );
            }
        });
    }

    private void handlerClickLinerLayoutTypeBillings(){
        linerLayoutTypeBillings.setOnClickListener(v -> {
            ModalTypeBillings modalTypeBillings = new ModalTypeBillings(getContext());
            modalTypeBillings.modalStart(new DialogCallback() {
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
                    R.string.text_modal_view_select_billings_balance,
                    textViewBalanceBillings.getText().toString(),
                    textViewTypeCurrency.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
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
                    textViewCreditLimit.getText().toString(),
                    textViewTypeCurrency.getText().toString());
            modalBalance.modalStart(new DialogCallback() {
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

    private boolean checkInputTypeCurrency(String type){
        return type != null && !type.isEmpty();
    }

    private void replaceFragmentBack(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            getActivity().finish();
        }
    }
}