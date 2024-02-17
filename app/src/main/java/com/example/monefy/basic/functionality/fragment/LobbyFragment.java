package com.example.monefy.basic.functionality.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.monefy.basic.functionality.controller.billings.BillingsController;
import com.example.monefy.basic.functionality.controller.incomes.IncomeController;
import com.example.monefy.basic.functionality.controller.message.MessageController;
import com.example.monefy.basic.functionality.controller.user.UserController;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.accounting.AccountingFragment;
import com.example.monefy.basic.functionality.fragment.bank.CurrencyBankFragment;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;


public class LobbyFragment extends Fragment {
    /**
     * Об'єкт текстового поля для відображення імені користувача.
     */
    private TextView textNameUser;

    /**
     * Об'єкт текстового поля для відображення повідомлення.
     */
    private TextView textMessage;

    /**
     * Об'єкт текстового поля для відображення кількості повідомлень.
     */
    private TextView textCollMessage;

    /**
     * Об'єкт кнопки для перегляду рахунків користувача.
     */
    private Button btnCollBills;

    /**
     * Об'єкт кнопки для перегляду доходів користувача.
     */
    private Button btnCollIncomes;

    /**
     * Об'єкт кнопки для перегляду всіх повідомлень.
     */
    private ImageButton imgBtnAllMessage;

    /**
     * Об'єкт контейнера для карточки з інформацією про рахунки.
     */
    private ConstraintLayout cardBillings;

    /**
     * Об'єкт контейнера для карточки з інформацією про облік.
     */
    private ConstraintLayout cardAccounting;

    /**
     * Об'єкт контейнера фрагмента для відображення валюти та банківських даних.
     */
    private FragmentContainerView fragContCurrencyBank;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lobby, container, false);
        setupUIElements(view);
        showFragmentCurrencyBank();
        setValueUIElement();
        handlerClickEvent();
        return view;
    }

    /** Цей метод проводить ініціалізацію UI елементів вкладки.
     * @param view -  базовий клас для віджетів.
     */
    private void setupUIElements(View view){
        this.textNameUser = view.findViewById(R.id.textNameUser);
        this.textMessage = view.findViewById(R.id.textMessage);
        this.textCollMessage =view.findViewById(R.id.textCollMessage);
        this.btnCollBills = view.findViewById(R.id.btnCollBills);
        this.btnCollIncomes = view.findViewById(R.id.btnCollIncomes);
        this.cardBillings = view.findViewById(R.id.cardBillings);
        this.cardAccounting = view.findViewById(R.id.cardIncomes);
        this.imgBtnAllMessage = view.findViewById(R.id.imgBtnAllMessage);
        this.fragContCurrencyBank = view.findViewById(R.id.fragCurrencyBank);
    }

    /**
     * Цей метод встановлює значення для UI елементів.
     * Встановлює текст кнопки "Рахунки" на основі кількості рахунків, завантажує доходи
     * та встановлює текст кнопки "Доходи" на основі їх кількості, завантажує дані користувача
     * та встановлює ім'я користувача, завантажує повідомлення та встановлює текст
     * останнього повідомлення та загальну кількість повідомлень.
     */
    private void setValueUIElement(){
        BillingsController.getBillingsSize(size -> btnCollBills.setText(String.valueOf(size)));

        IncomeController.getIncomeManager().loadIncomes(()->{
            btnCollIncomes.setText(String.valueOf(IncomeController.getIncomeSize()));
        });

        UserController.getUserManager().loadGetUserData(()->{
            textNameUser.setText(UserController.getUser().getName());
        });

        MessageController.getMessageManager().loadMessage(()->{
            textCollMessage.setText(String.valueOf(MessageController.getMessageManager().getCollMessage()));
            textMessage.setText(MessageController.getMessageManager().getLastMessageList().getMessage());
        });
    }

    /**
     * Цей метод обробляє всі кліки по елементах.
     * Обробляє кліки по карточці "Рахунки", карточці "Облік" та кнопці "Всі повідомлення".
     */
    private void handlerClickEvent(){
        handlerClickBillingsCard();
        handlerClickAccountingCard();
        handlerClickMessageCard();
    }

    /**
     * Цей метод обробляє клік по карточці "Рахунки" ConstraintLayout.
     */
    private void handlerClickBillingsCard(){
        cardBillings.setOnClickListener(v->{
            FragmentNavigation.goToReplaceBillingsFragment(getSupportFragmentManager());
        });
    }

    /**
     * Цей метод обробляє клік по карточці "Облік" ConstraintLayout.
     */
    private void handlerClickAccountingCard(){
        Bundle bundle = new Bundle();
        bundle.putString("typeAccounting","REVENUES");

        AccountingFragment accountingFragment = new AccountingFragment();
        accountingFragment.setArguments(bundle);

        cardAccounting.setOnClickListener(v->{
            FragmentNavigation.replaceFragment(
                    getSupportFragmentManager(),
                    accountingFragment,
                    FragmentNavigation.getContainerHome(),
                    "AccountingFragment"
            );
        });
    }

    /**
     * Цей метод обробляє клік по кнопці "Всі повідомлення".
     */
    private void handlerClickMessageCard(){
        imgBtnAllMessage.setOnClickListener(v->{
            FragmentNavigation.gotToReplaceMessageFragment(getSupportFragmentManager());
        });
    }

    /**
     * Цей метод завантажує та відображає Fragment курсу валют.
     */
    private void showFragmentCurrencyBank(){
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                new CurrencyBankFragment(),
                fragContCurrencyBank.getId(),
                "CurrencyBankFragment"
        );
    }

    /**
     * Отримати FragmentManager для керування фрагментами.
     * @return Об'єкт FragmentManager для керування фрагментами.
     */
    private FragmentManager getSupportFragmentManager(){
        if(getActivity() != null){
            return getActivity().getSupportFragmentManager();
        }
        return  getParentFragmentManager();
    }

}