package com.example.monefy.basic.functionality.fragment.income;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.message.ToastManager;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.billings.BillingsFragment;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBalance;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelect;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalInputData;
import com.example.monefy.basic.functionality.fragment.navigation.ClickListener;
import com.example.monefy.basic.functionality.fragment.navigation.ConfirmationFragment;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.example.monefy.basic.functionality.model.income.TypeCategory;
import com.example.monefy.basic.functionality.model.income.TypeFrequency;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateIncomeFragment extends Fragment {

    private FragmentContainerView fragNavigation, fragIncomeDetails;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_create_income, container, false);

        setupUIElements();
        showFragNavigation();
        showIncomeDetails();
        return view;
    }

    private void setupUIElements(){
        fragNavigation = view.findViewById(R.id.fragNavigationIncome);
        fragIncomeDetails = view.findViewById(R.id.fragIncomeDetails);
    }

    private IncomeDetailsFragment incomeDetailsFragment;

    private void showIncomeDetails(){
        incomeDetailsFragment = new IncomeDetailsFragment();
        FragmentSwitcher.replaceFragment(
                incomeDetailsFragment,
                getContext(),
                fragIncomeDetails.getId()
        );
    }

    private ConfirmationFragment confirmationFragment;

    private void showFragNavigation(){
        confirmationFragment = new ConfirmationFragment();
        FragmentSwitcher.replaceFragment(
                confirmationFragment,
                getContext(),
                fragNavigation.getId()
        );
        handlerClick();
    }

    private void handlerClick(){
        confirmationFragment.setClickListener(new ClickListener() {
            @Override
            public void clickBtnClose() {
                handlerClickBtnClose();
            }

            @Override
            public void clickBtnSetUp() {
                handlerClickBtnSetUp();
            }
        });
    }

    private void handlerClickBtnClose(){
        FragmentSwitcher.replaceFragment(
                new BillingsFragment(),
                getContext(),
                FragmentSwitcher.getContainerHome()
        );
    }

    private void handlerClickBtnSetUp(){
        Map<String, Object> incomeMap = incomeDetailsFragment.getDataMapIncome();
        if(incomeMap.size() != 0){
            FirebaseManager.addIncome(
                    FirebaseFirestore.getInstance(),
                    AuthenticationManager.getAuthenticationManager().getUserId(),
                    incomeMap,
                    new InConclusionCompleteListener() {
                        @Override
                        public void onSuccess() {
                            FragmentSwitcher.replaceFragment(
                                    new IncomeFragment(),
                                    getContext(),
                                    FragmentSwitcher.getContainerHome()
                            );
                            ToastManager.showToastOnFailure(getContext(),R.string.toast_successful_add_income);
                        }

                        @Override
                        public void onFailure(Exception exception) {

                        }
                    }
            );
        }
    }
}