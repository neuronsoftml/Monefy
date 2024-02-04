package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monefy.Manager.ManagerActivity;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentNavigation;
import com.example.monefy.basic.functionality.fragment.navigation.ClickListener;
import com.example.monefy.basic.functionality.fragment.navigation.ConfirmationFragment;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.message.ToastManager;

import java.util.Map;


public class CreateBillingsFragment extends Fragment {

    // Ініціалізація змінних класу
    private String argTypeBillings;
    private FragmentContainerView fragNavigation;
    private FragmentContainerView fragBillingDetails;
    private final BillingDetailsFragment billingDetailsFragment = new BillingDetailsFragment(false);

    // Метод onCreate викликається при створенні фрагменту
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Отримання аргументів, переданих фрагменту
        if (getArguments() != null) {
            argTypeBillings = getArguments().getString("TypeBillings");
        }
    }

    // Метод onCreateView викликається для створення вигляду фрагменту
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Завантаження макету для цього фрагменту
        View view = inflater.inflate(R.layout.fragment_create_billings, container, false);
        // Налаштування елементів інтерфейсу користувача
        setupUIElements(view);
        // Показ фрагментів навігації та деталей рахунку
        showFragNavigation();
        showFragBillingDetails();
        return view;
    }

    // Налаштування UI елементів фрагменту
    private void setupUIElements(View view) {
        // Ініціалізація елементів інтерфейсу та присвоєння їх відповідним змінним
        fragNavigation = view.findViewById(R.id.fragNavigation);
        fragBillingDetails = view.findViewById(R.id.fragBillingDetails);
    }

    // Відображення фрагменту з деталями рахунк
    private void showFragBillingDetails(){
        // Створення пакету аргументів для передачі в фрагмент
        Bundle bundle = new Bundle();
        bundle.putSerializable("TypeBillings", argTypeBillings);
        billingDetailsFragment.setArguments(bundle);


        // Завантаження фрагмента деталей рахунку
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                billingDetailsFragment,
                fragBillingDetails.getId(),
                "BillingDetailsFragment");
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

        // Відображення фрагменту навігаціїї
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                confirmationFragment,
                fragNavigation.getId(),"ConfirmationFragment");

    }

    // Обробник кліку на кнопку закриття
    private void handlerClickImgBtnClose() {
        if(getActivity() != null){
            FragmentNavigation.goToReplaceBillingsFragment(getActivity().getSupportFragmentManager());
        }else {
            throw new NullPointerException("Відсутня активність");
        }
    }

    //Обробник кліку на кнопку затвердження
    private void handlerClickImgBtnSetUp() {
        Map<String, Object> billing = billingDetailsFragment.getBillingMapData();

        if(billing.size() != 0){
            FirebaseManager firebaseManager = FirebaseManager.getFirebaseManager();
            firebaseManager.addBilling(billing, new InConclusionCompleteListener() {
                        @Override
                        public void onSuccess() {
                            ToastManager.showToastOnSuccessful(getContext(),R.string.textSuccessfulEnteredTheData);
                            ManagerActivity.resetActivity(getActivity());
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            ToastManager.showToastOnFailure(getContext(),R.string.textFailureEnteredTheData);
                        }
                    }
            );
        }
    }
}