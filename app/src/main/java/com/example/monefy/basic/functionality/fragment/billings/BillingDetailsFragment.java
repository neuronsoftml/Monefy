package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentNavigation;
import com.example.monefy.basic.functionality.fragment.billings.factory.CumulativeFragment;
import com.example.monefy.basic.functionality.fragment.billings.factory.DebtFragment;
import com.example.monefy.basic.functionality.fragment.billings.factory.OrdinaryFragment;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Cumulative;
import com.example.monefy.basic.functionality.model.billings.Debt;
import com.example.monefy.basic.functionality.model.billings.Ordinary;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import java.util.Map;

public class BillingDetailsFragment extends Fragment implements BillingDetailsListener {

    private String argTypeBillings;
    private FragmentContainerView fragContBillingsDetails;
    private final boolean isEditMode;

    public BillingDetailsFragment(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Billings billing = (Billings) bundle.getSerializable("billing");
            if (billing != null) {
                this.argTypeBillings = billing.getTypeBillings();
            }else {
                this.argTypeBillings = bundle.getString("TypeBillings");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billings_details, container, false);

        setupUIElements(view);
        checkTypeBillings(argTypeBillings);
        return view;
    }

    private void setupUIElements(View view) {
        fragContBillingsDetails = view.findViewById(R.id.fragContBillingsDetails);
    }

    private void checkTypeBillings(String argTypeBillings){
        if (argTypeBillings.equals(TypeBillings.ORDINARY.getCCY())){
            showOrdinaryDetails(argTypeBillings);
        }else if (argTypeBillings.equals(TypeBillings.DEBT.getCCY())) {
            showDebtDetails(argTypeBillings);
        }else if (argTypeBillings.equals(TypeBillings.CUMULATIVE.getCCY())) {
            showCumulativeDetails(argTypeBillings);
        }
    }
    private OrdinaryFragment ordinaryFragment;
    private void showOrdinaryDetails(String argTypeBillings){
        ordinaryFragment = new OrdinaryFragment(isEditMode);
        if(argTypeBillings != null){
            Bundle updateBundle = updateBundle(argTypeBillings);
            ordinaryFragment.setArguments(updateBundle);
        }else {
            ordinaryFragment.setArguments(getArguments());
        }

        FragmentNavigation.replaceFragment(
                getChildFragmentManager(),
                ordinaryFragment,
                fragContBillingsDetails.getId(),
                "fragContBillingsDetails"
        );
        ordinaryFragment.setBillingDetailsFragment(this);
    }

    private DebtFragment debtFragment;
    private void showDebtDetails(String argTypeBillings) {
        debtFragment = new DebtFragment(isEditMode);
        if(argTypeBillings != null){
            Bundle updateBundle = updateBundle(argTypeBillings);
            debtFragment.setArguments(updateBundle);
        }else {
            debtFragment.setArguments(getArguments());
        }
        FragmentNavigation.replaceFragment(
                getChildFragmentManager(),
                debtFragment,
                fragContBillingsDetails.getId(),
                "fragContBillingsDetails"
        );
        debtFragment.setBillingDetailsFragment(this);
    }

    private CumulativeFragment cumulativeFragment;
    private void showCumulativeDetails(String argTypeBillings) {
        cumulativeFragment = new CumulativeFragment(isEditMode);
        if(argTypeBillings != null){
            Bundle updateBundle = updateBundle(argTypeBillings);
            cumulativeFragment.setArguments(updateBundle);
        }else {
            cumulativeFragment.setArguments(getArguments());
        }
        FragmentNavigation.replaceFragment(
                getChildFragmentManager(),
                cumulativeFragment,
                fragContBillingsDetails.getId(),
                "cumulativeFragment"
        );
        cumulativeFragment.setBillingDetailsFragment(this);
    }

    public Map<String, Object> getBillingMapData(){
        if (argTypeBillings.equals(TypeBillings.ORDINARY.getCCY())){
            Ordinary ordinary = (Ordinary) ordinaryFragment.getBilling();
            return ordinary.getCreateMap();
        }else if (argTypeBillings.equals(TypeBillings.DEBT.getCCY())) {
            Debt debt = (Debt) debtFragment.getBilling();
            return debt.getCreateMap();
        }else if (argTypeBillings.equals(TypeBillings.CUMULATIVE.getCCY())) {
            Cumulative cumulative = (Cumulative) cumulativeFragment.getBilling();
            return cumulative.getCreateMap();
        }
        return  null;
    }
    public Billings getBillingObject(){
        if (argTypeBillings.equals(TypeBillings.ORDINARY.getCCY())){
            return  (Ordinary) ordinaryFragment.getBilling();
        }else if (argTypeBillings.equals(TypeBillings.DEBT.getCCY())) {
            return  (Debt) debtFragment.getBilling();
        }else if (argTypeBillings.equals(TypeBillings.CUMULATIVE.getCCY())) {
           return (Cumulative) cumulativeFragment.getBilling();
        }
        return  null;
    }

    private Bundle updateBundle(String argTypeBillings){
        Bundle bundle = getArguments();
        if(bundle != null){
            bundle.putString("TypeBillings", argTypeBillings);
        }
        return bundle;
    }
    @Override
    public void onBillingTypeChanged(String newBillingType) {
        checkTypeBillings(newBillingType);
    }

}