package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.monefy.basic.functionality.controller.message.MessageController;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;
import com.example.monefy.basic.functionality.fragment.history.HistoryBillingsFragment;

/** Цей класс містить сукумність інших фрагментів, для відображення:
 *  1) спиcок рахунків.
 *  2) список історії рахунку.
 *  3) інформаційна панель
 *  4) лічильник кількості рахунків.
 *  5) лічильник кількості повідомлень.
 */
public class BillingsFragment extends Fragment {

    private FragmentContainerView fragBillings;
    private FragmentContainerView fragInformationBoard;
    private FragmentContainerView fragHistoryBill;
    private TextView btnBack;
    private TextView counterBillings, counterMessage;
    private final InfoBoardBillingsFragment infoBoardBillingsFragment = new InfoBoardBillingsFragment();
    private final HistoryBillingsFragment historyBillingsFragment = new HistoryBillingsFragment();
    private final BillingsListFragment billingsListFragment =  new BillingsListFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billings, container, false);

        setupUIElements(view);
        showFragInformationBord();
        showFragBillingsList();
        showFragHistoryBilling();
        handlerClickBtnBack();
        setUIDataElement();
        return view;
    }

    /** Цей метод здійснює ініціалізацію UI елементів які знаходять в фрагменті
     * @param view віджети
     */
    private void setupUIElements(View view){
        this.fragBillings = view.findViewById(R.id.fragBillings);
        this.fragInformationBoard = view.findViewById(R.id.informationBoard);
        this.fragHistoryBill = view.findViewById(R.id.fragCotHistoryBill);
        this.btnBack = view.findViewById(R.id.btnBack);
        this.counterBillings = view.findViewById(R.id.textCollBillings);
        this.counterMessage = view.findViewById(R.id.textCollMessage);
    }

    /**Цей метод відображає фрагмент який містить інформацію загального розрахунку, а саме:
     * 1) Загальна сума збережених коштів накопичувальних рахунків.
     * 2) Загальна сума боргів усіх типів.
     * 3) Загальна сума коштів на рахунках типу "звичайний"
     * */
    private void showFragInformationBord() {
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                infoBoardBillingsFragment,
                fragInformationBoard.getId(),
                "InfoBoardBillingsFragment"
        );
    }

    /**Цей метод відображає фрагмент який містить список рахунків*/
    private void showFragBillingsList() {
        billingsListFragment.setInfoBoardFragment(infoBoardBillingsFragment);
        billingsListFragment.setBillingsFragment(this);
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                billingsListFragment,
                fragBillings.getId(),
                "BillingsListFragment"
        );
    }

    /** Цей метод відображає фрагмент який містить список історії рахунку*/
    private void showFragHistoryBilling(){
        billingsListFragment.setHistoryBillingsFragment(historyBillingsFragment);
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                historyBillingsFragment,
                fragHistoryBill.getId(),
                "HistoryBillingsFragment"
        );
    }

    /** Цей метод обробляє подію кліку по кнопці назад*/
    private void handlerClickBtnBack(){
        btnBack.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    /** Цей метод встановлює значення для UI елементів які знаходяться у фрагменті.*/
    private void setUIDataElement(){
        setDataCounterMessage();
    }

    /** Цей метод встановлює значення UI лічильнику кількості рахунків*/
    public void setDataCounterBillings(int number){
        counterBillings.setText(String.valueOf(number));
    }

    /** Цей метод встановлює значення UI лічильнику кількості повідомлень*/
    private void setDataCounterMessage(){
        int number = MessageController.getMessageManager().getCollMessage();
        counterMessage.setText(String.valueOf(number));
    }
}


