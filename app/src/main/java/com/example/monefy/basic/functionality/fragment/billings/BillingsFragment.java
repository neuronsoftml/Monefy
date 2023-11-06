package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;

public class BillingsFragment extends Fragment {

    private Button btnAddBillings;
    private FragmentContainerView fragContainerBillingsOne;
    private FragmentContainerView fragContainerBillingsTwo;
    private FragmentContainerView fragContainerCurrencyNbu;
    private FragmentContainerView fragContainerTotalAmount;
    private FragmentContainerView fragContainerTotalSavings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billings, container, false);

        setupUIElements(view);
        showTotalAmount();
        showTotalSavings();
        showBillingsListOne();
        showBillingsListTwo();
        showCurrencyNbu();
        handlerButtonClick();
        return view;
    }

    private void setupUIElements(View view){
        this.btnAddBillings = (Button) view.findViewById(R.id.button_add_billings);

        this.fragContainerBillingsOne = view.findViewById(R.id.fragContainerBillingsOne);
        this.fragContainerBillingsTwo = view.findViewById(R.id.fragContainerBillingsTwo);
        this.fragContainerCurrencyNbu = view.findViewById(R.id.fragContainerCurrencyNbu);
        this.fragContainerTotalAmount = view.findViewById(R.id.fragContainerTotalAmount);
        this.fragContainerTotalSavings = view.findViewById(R.id.fragContainerTotalSavings);
    }

    private void handlerButtonClick(){
        clickButtonAddBillings();
    }

    private TotalAmountFragment totalAmountFragment;
    private CurrencyNbuFragment currencyNbuFragment;
    private BillingsListOneFragment billingsListOneFragment;
    private BillingsListTwoFragment billingsListTwoFragment;
    private TotalSavingsFragment totalSavingsFragment;

    private void showBillingsListOne() {
        billingsListOneFragment = new BillingsListOneFragment();
        billingsListOneFragment.setTotalAmountFragment(totalAmountFragment);
        FragmentSwitcher.replaceFragment(
                billingsListOneFragment,
                getActivity().getSupportFragmentManager(),
                R.id.fragContainerBillingsOne);

    }

    private void showBillingsListTwo() {
        billingsListTwoFragment = new BillingsListTwoFragment();
        billingsListTwoFragment.setTotalSavingsFragment(totalSavingsFragment);
        FragmentSwitcher.replaceFragment(
                billingsListTwoFragment,
                getActivity().getSupportFragmentManager(),
                R.id.fragContainerBillingsTwo);
    }

    private void showCurrencyNbu(){
        currencyNbuFragment = new CurrencyNbuFragment();
        currencyNbuFragment.setTotalAmountFragment(totalAmountFragment);
        currencyNbuFragment.setTotalSavingsFragment(totalSavingsFragment);
        FragmentSwitcher.replaceFragment(
                currencyNbuFragment,
                getActivity().getSupportFragmentManager(),
                R.id.fragContainerCurrencyNbu
        );
    }

    private void showTotalAmount(){
        totalAmountFragment = new TotalAmountFragment();
       FragmentSwitcher.replaceFragment(
               totalAmountFragment,
               getActivity().getSupportFragmentManager(),
               R.id.fragContainerTotalAmount
       );
    }

    private void showTotalSavings(){
        totalSavingsFragment = new TotalSavingsFragment();
        FragmentSwitcher.replaceFragment(
                totalSavingsFragment,
                getActivity().getSupportFragmentManager(),
                R.id.fragContainerTotalSavings
        );
    }

    private void clickButtonAddBillings(){
        btnAddBillings.setOnClickListener(v -> {
            ModalTypeBillings modalTypeBillings = new ModalTypeBillings(getContext());
            modalTypeBillings.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    Bundle bundle = new Bundle();
                    bundle.putString("TypeBillings", modalTypeBillings.getUpdateData());

                    FragmentSwitcher.replaceFragmentToDate(
                            new CreateBillingsFragment(),
                            bundle,
                            getContext(),
                            FragmentSwitcher.getContainerHome()
                    );
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }
}


