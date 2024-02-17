package com.example.monefy.basic.functionality.fragment.accounting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.UI.UpdateUI;
import com.example.monefy.basic.functionality.fragment.accounting.expenses.ExpensesFragment;
import com.example.monefy.basic.functionality.fragment.accounting.revenue.RevenueFragment;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;

/** Цей фрагмент відображає під фрагменти активів та пасивів.*/
public class AccountingFragment extends Fragment {

    /** Кнопка, яка повертає користувача до попереднього екрану */
    private TextView btnBack;

    /** Кнопка, яка відображає фрагмент доходів при натисканні */
    private TextView btnToggleRevenues;

    /** Кнопка, яка відображає фрагмент витрат при натисканні */
    private TextView btnToggleExpenses;

    /** Контейнер, на якому будемо загружати основний контет*/
    private FragmentContainerView fragContainerAccountingContent;

    /**
     * Тип обліку, який вказує на поточний режим відображення (доходи або витрати).
     * Використовується для визначення, який тип обліку відображається на даний момент.
     */
    private String typeAccounting;

    /**
     * Константа для позначення типу обліку "Доходи".
     * Використовується для порівняння з типом обліку в методі handlerCheckTypeAccounting().
     */
    private final String REVENUES = "REVENUES";

    /**
     * Константа для позначення типу обліку "Витрати".
     * Використовується для порівняння з типом обліку в методі handlerCheckTypeAccounting().
     */
    private final String EXPENSES = "EXPENSES";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.getString("typeAccounting").equals(REVENUES)){
                typeAccounting = REVENUES;
            } else if (bundle.getString("typeAccounting").equals(EXPENSES)) {
               typeAccounting = EXPENSES;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_accounting, container, false);

       setupUIElements(view);
       handlerClickButton();
       handlerCheckTypeAccounting();
       return view;
    }

    /**
     * Метод, який перевіряє тип обліку (доходи або витрати) та відповідно викликає метод
     * contentSwitcher з переданою кнопкою, щоб відобразити відповідний фрагмент.
     * Якщо тип обліку є "Доходи" (REVENUES), викликається метод contentSwitcher з кнопкою
     * для переключення доходів. Якщо тип обліку є "Витрати" (EXPENSES), викликається метод
     * contentSwitcher з кнопкою для переключення витрат.
     */
    private void handlerCheckTypeAccounting() {
        if(typeAccounting.equals(REVENUES)){
            contentSwitcher(btnToggleRevenues);
        } else if (typeAccounting.equals(EXPENSES)) {
            contentSwitcher(btnToggleExpenses);;
        }
    }

    /** Цей метод проводить ініціалізацію UI елементів вкладки.
     * @param view -  базовий клас для віджетів.
     */
    private void setupUIElements(View view){
        btnBack = view.findViewById(R.id.btnBack);
        btnToggleRevenues =  view.findViewById(R.id.btnToggleRevenues);
        btnToggleExpenses = view.findViewById(R.id.btnToggleExpenses);
        fragContainerAccountingContent = view.findViewById(R.id.fragContainerAccountingContent);
    }

    /**
     * Метод, який налаштовує обробники натискання кнопок.
     */
    private void handlerClickButton(){
        handlerClickBtnBack();
        handlerClickBtnToggleRevenues();
        handlerClickBtnToggleExpenses();
    }

    /**
     * Метод, який встановлює обробник натискання для кнопки повернутися назад.
     * При натисканні повертаємоль до попереднього фрагмента.
     */
    private void handlerClickBtnBack(){
        btnBack.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    /**
     * Метод, який встановлює обробник натискання для кнопки переключення доходів.
     * При натисканні викликає метод contentSwitcher для обробки переключення контенту на екрані доходів.
     Після цього відбувається заміна фрагменту на екрані на фрагмент доходів.
     */
    private void handlerClickBtnToggleRevenues(){
        btnToggleRevenues.setOnClickListener(v->{
            contentSwitcher(btnToggleRevenues);
        });
    }

    /**
     * Метод, який встановлює обробник натискання для кнопки переключення витрат.
     * При натисканні викликає метод contentSwitcher для обробки переключення контенту на екрані витрат.
     * Після цього відбувається заміна фрагменту на екрані на фрагмент витрат.
     */
    private void handlerClickBtnToggleExpenses(){
        btnToggleExpenses.setOnClickListener(v->{
            contentSwitcher(btnToggleExpenses);
        });
    }

    /**
     * Метод для перемикання між екранами доходів та витрат.
     * Якщо передана кнопка є кнопкою переключення доходів (btnToggleRevenues),
     * то вона стає активною, а кнопка переключення витрат (btnToggleExpenses) -
     * неактивною. Якщо передана кнопка є кнопкою переключення витрат (btnToggleExpenses),
     * то вона стає активною, а кнопка переключення доходів (btnToggleRevenues) -
     * неактивною.
     *
     * @param button Кнопка, яка викликала метод і яка потрібно зробити активною.
     */
    private void contentSwitcher(TextView button){
        if(button.getId() == R.id.btnToggleRevenues){
            UpdateUI.btnToggleActive(button, getResources());
            UpdateUI.btnToggleInactive(btnToggleExpenses, getResources());
            showFragmentRevenues();
        } else if (button.getId() == R.id.btnToggleExpenses) {
            UpdateUI.btnToggleActive(button, getResources());
            UpdateUI.btnToggleInactive(btnToggleRevenues, getResources());
            showFragmentExpenses();
        }
    }

    /**
     * Метод для відображення фрагменту доходів.
     * Замінює поточний фрагмент на фрагмент доходівБ, в контейнері  fragContainerAccountingContent.
     */
    private void showFragmentRevenues(){
        FragmentNavigation.replaceFragment(
                getChildFragmentManager(),
                new RevenueFragment(),
                fragContainerAccountingContent.getId(),
                "RevenueFragment"
        );
    }

    /**
     * Метод для відображення фрагменту витрат.
     * Замінює поточний фрагмент на фрагмен витрат, в контейнері  fragContainerAccountingContent.
     */
    private void  showFragmentExpenses(){
        FragmentNavigation.replaceFragment(
                getChildFragmentManager(),
                new ExpensesFragment(),
                fragContainerAccountingContent.getId(),
                "ExpensesFragment"
        );
    }
}