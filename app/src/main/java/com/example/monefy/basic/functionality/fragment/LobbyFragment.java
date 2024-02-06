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
import com.example.monefy.basic.functionality.fragment.bank.CurrencyBankFragment;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;


public class LobbyFragment extends Fragment {
    private TextView textNameUser;
    private TextView textMessage;
    private TextView textCollMessage;
    private Button btnCollBills;
    private Button btnCollIncomes;
    private ImageButton imgBtnAllMessage;
    private ConstraintLayout cardBillings, cardIncomes;
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
        this.cardIncomes = view.findViewById(R.id.cardIncomes);
        this.imgBtnAllMessage = view.findViewById(R.id.imgBtnAllMessage);
        this.fragContCurrencyBank = view.findViewById(R.id.fragCurrencyBank);
    }

    /** Цей метод встановлює значення UI елементів.*/
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

    /** Цей метод обробляє усі кліки по елементах.*/
    private void handlerClickEvent(){
        handlerClickBillingsCard();
        handlerClickIncomesCard();
        handlerClickMessageCard();
    }

    /** Цей метод обробляє клік по cardBillings ConstraintLayout.*/
    private void handlerClickBillingsCard(){
        cardBillings.setOnClickListener(v->{
            FragmentNavigation.goToReplaceBillingsFragment(getSupportFragmentManager());
        });
    }

    /** Цей метод обробляє клік по cardIncomes ConstraintLayout*/
    private void handlerClickIncomesCard(){
        cardIncomes.setOnClickListener(v->{
            FragmentNavigation.gotToReplaceIncomesFragment(getSupportFragmentManager());
        });
    }

    /** Цей метод оброляє клікл imgBtnAllMessage */
    private void handlerClickMessageCard(){
        imgBtnAllMessage.setOnClickListener(v->{
            FragmentNavigation.gotToReplaceMessageFragment(getSupportFragmentManager());
        });
    }

    /** Цей метод загружає та відображає Fragment курса валют */
    private void showFragmentCurrencyBank(){
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                new CurrencyBankFragment(),
                fragContCurrencyBank.getId(),
                "CurrencyBankFragment"
        );
    }

    private FragmentManager getSupportFragmentManager(){
        if(getActivity() != null){
            return getActivity().getSupportFragmentManager();
        }
        return  getParentFragmentManager();
    }

}