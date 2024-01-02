package com.example.monefy.basic.functionality.fragment.billings;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.navigation.ClickListener;
import com.example.monefy.basic.functionality.fragment.navigation.ConfirmationFragment;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.message.ToastManager;

import java.util.HashMap;
import java.util.Map;

public class EditBillingsFragment extends Fragment {

    // Ініціалізація змінних класу
    private Billings billing;
    private String argTypeBillings;
    private FragmentContainerView fragNavigation;
    private FragmentContainerView fragBillingDetails;
    private final BillingDetailsFragment billingDetailsFragment = new BillingDetailsFragment();
    private Context context;

    // Метод onCreate викликається при створенні фрагменту
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
        View view = inflater.inflate(R.layout.fragment_edit_billings, container, false);
        setupUIElements(view);
        showFragNavigation();
        showFragBillingDetails();
        return view;
    }

    // Налаштування UI елементів фрагменту
    private void setupUIElements(View view) {
        // Ініціалізація елементів інтерфейсу та присвоєння їх відповідним змінним
        ImageView imgViewCreditCart = view.findViewById(R.id.imageViewCreditCartTypeBillings);
        billingDetailsFragment.setImgViewCreditCart(imgViewCreditCart);
        ConstraintLayout constraintLayoutPanelTop = view.findViewById(R.id.constraintTop);
        billingDetailsFragment.setConsLatPanelTop(constraintLayoutPanelTop);
        fragNavigation = view.findViewById(R.id.fragNavigation);
        fragBillingDetails = view.findViewById(R.id.fragBillingDetails);
    }

    // Відображення фрагменту з деталями рахунку
    private void showFragBillingDetails(){
        // Створення пакету аргументів для передачі в фрагмент
        Bundle bundle = new Bundle();
        bundle.putSerializable("TypeBillings", argTypeBillings);
        bundle.putSerializable("billing",billing);
        billingDetailsFragment.setArguments(bundle);

        //Відображення фрагменту деталей рахунку
        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                billingDetailsFragment,
                fragBillingDetails.getId()
        );
    }

    // Відображення фрагменту навігації
    private void showFragNavigation(){
        // Ініціалізація фрагменту підтвердження та встановлення обробників кліків
        ConfirmationFragment confirmationFragment = new ConfirmationFragment();
        confirmationFragment.setClickListener(new ClickListener() {
            @Override
            public void clickBtnClose() {
                handlerClickImgBtnClose();
            }

            @Override
            public void clickBtnSetUp() {
                handlerClickImgBtnSetUp();
            }
        });

        // Загрузка фрагмента підтвердження
        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                confirmationFragment,
                fragNavigation.getId()
        );
    }

    private void handlerClickImgBtnClose(){
        FragmentSwitcher.replaceFragmentBack(getContext());
    }


    private void handlerClickImgBtnSetUp() {
        context = getContext();

        Map<String, Object> mapBilling = billingDetailsFragment.getBillingData();
        if(mapBilling.size() != 0){

            if(!createEditableBill(mapBilling).equals(billing)){
                FirebaseManager.updatedBillings(
                        billing.getId(),
                        mapBilling,
                        new InConclusionCompleteListener() {
                            @Override
                            public void onSuccess() {
                                ToastManager.showToastOnSuccessful(context,R.string.toast_successful_edit_billings);
                               /* FragmentSwitcher.replaceFragment(
                                        new BillingsFragment(),
                                        context,
                                        FragmentSwitcher.getContainerHome()
                                );

                                */

                                //Необхідно дописати переход.
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                ToastManager.showToastOnFailure(context,R.string.toast_failure_edit_billings);
                                FragmentSwitcher.replaceFragmentBack(context);
                            }
                        }

                );
            }
        }

        FragmentSwitcher.replaceFragmentBack(context);
    }


    public Billings createEditableBill(Map<String, Object> bill){
        Billings editBilling = new Billings(
                Long.parseLong(String.valueOf(bill.get("balance"))),
                Long.parseLong(String.valueOf(bill.get("creditLimit"))),
                String.valueOf(bill.get("name")),
                String.valueOf(bill.get("typeBillings")),
                String.valueOf(bill.get("typeCurrency")),
                String.valueOf(bill.get("obligation"))
        );

        editBilling.setId(billing.getId());
        return editBilling;
    }
}