package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;
import com.example.monefy.basic.functionality.Interface.navigation.ClickListener;
import com.example.monefy.basic.functionality.fragment.navigation.ConfirmationFragment;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;
import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.controller.message.ToastController;

public class EditBillingsFragment extends Fragment {

    // Ініціалізація змінних класу
    private Billings billing;
    private FragmentContainerView fragNavigation;
    private FragmentContainerView fragBillingDetails;
    private final BillingDetailsFragment billingDetailsFragment = new BillingDetailsFragment(true);

    // Метод onCreate викликається при створенні фрагменту
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
        showFragNavigation();
        showFragBillingDetails();
        return view;
    }

    // Налаштування UI елементів фрагменту
    private void setupUIElements(View view) {
        // Ініціалізація елементів інтерфейсу та присвоєння їх відповідним змінним
        ImageView imgViewCreditCart = view.findViewById(R.id.imageViewCreditCartTypeBillings);
        ConstraintLayout constraintLayoutPanelTop = view.findViewById(R.id.constraintTop);
        fragNavigation = view.findViewById(R.id.fragNavigation);
        fragBillingDetails = view.findViewById(R.id.fragBillingDetails);
    }

    // Відображення фрагменту з деталями рахунку
    private void showFragBillingDetails(){

        billingDetailsFragment.setArguments(getArguments());
        //Відображення фрагменту деталей рахунку

        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                billingDetailsFragment,
                fragBillingDetails.getId(),
                "BillingDetailsFragment"
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

        // Загрузка фрагмента  верхньої навігації

        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                confirmationFragment,
                fragNavigation.getId(),
                "ConfirmationFragment"
        );
    }

    private void handlerClickImgBtnClose(){
        if(getActivity() != null){
            FragmentNavigation.goToReplaceBillingsFragment(getActivity().getSupportFragmentManager());
        }
    }


    private void handlerClickImgBtnSetUp() {
        Billings billingNew = billingDetailsFragment.getBillingObject();
        if(billingNew != null){
            billingNew.setId(billing.getId());
            if(!billingNew.equals(billing)){
                FirebaseController firebaseController = FirebaseController.getFirebaseManager();
                firebaseController.updatedBillings(
                        billing.getId(),
                        billingDetailsFragment.getBillingMapData(),
                        new InConclusionCompleteListener() {
                            @Override
                            public void onSuccess() {
                                ToastController.showToastOnSuccessful(getContext(),R.string.textSuccessfulEditBillings);
                                if(getActivity() != null){
                                    FragmentNavigation.goToReplaceBillingsFragment(getActivity().getSupportFragmentManager());
                                }
                            }

                            @Override
                            public void onFailure(Exception exception) throws Exception {
                                ToastController.showToastOnFailure(getContext(),R.string.textFailureEditBillings);
                                throw new Exception("Помилка зміни рахунка " + exception);
                            }
                        }

                );
            }
        }
    }
}