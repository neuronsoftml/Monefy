package com.example.monefy.basic.functionality.fragment.accounting.revenue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;
import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.controller.message.ToastController;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;
import com.example.monefy.basic.functionality.Interface.navigation.ClickListener;
import com.example.monefy.basic.functionality.fragment.navigation.ConfirmationFragment;

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
        FragmentNavigation.replaceFragment(
                getChildFragmentManager(),
                incomeDetailsFragment,
                fragIncomeDetails.getId(),
                "fragIncomeDetails"
                );
    }

    private ConfirmationFragment confirmationFragment;

    private void showFragNavigation(){
        /*
        confirmationFragment = new ConfirmationFragment();
        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                confirmationFragment,
                fragNavigation.getId()
        );

         */
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
        /*
        FragmentSwitcher.replaceFragment(
                new BillingsFragment(),
                getContext(),
                FragmentSwitcher.getContainerHome()
        );
            Дописати навігацію.
         */
    }

    private void handlerClickBtnSetUp(){
        Map<String, Object> incomeMap = incomeDetailsFragment.getDataMapIncome();
        if(incomeMap.size() != 0){
            FirebaseController firebaseController = FirebaseController.getFirebaseManager();
            firebaseController.addIncome( incomeMap, new InConclusionCompleteListener() {
                        @Override
                        public void onSuccess() {
                            /*
                            FragmentSwitcher.replaceFragment(
                                    new IncomeFragment(),
                                    getContext(),
                                    FragmentSwitcher.getContainerHome()
                            );
                            Дописати навігацію.
                             */
                            ToastController.showToastOnFailure(getContext(),R.string.textSuccessfulAddIncome);
                        }

                        @Override
                        public void onFailure(Exception exception) {

                        }
                    }
            );
        }
    }
}