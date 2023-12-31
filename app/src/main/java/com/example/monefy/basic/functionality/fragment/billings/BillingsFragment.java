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
import com.example.monefy.basic.functionality.fragment.income.IncomeFragment;

public class BillingsFragment extends Fragment {

    private FragmentContainerView fragBillings;
    private FragmentContainerView fragInformationBoard;
    private Button btnIncome;

    private InfoBoardBillingsFragment infoBoardBillingsFragment = new InfoBoardBillingsFragment();
    private BillingsListFragment billingsListFragment =  new BillingsListFragment();

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
        showFragBillings();
        handlerBtnIncome();
        return view;
    }

    private void showFragInformationBord() {
        infoBoardBillingsFragment.setBillingsListFragment(billingsListFragment);

        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                infoBoardBillingsFragment,
                fragInformationBoard.getId());
    }

    private void showFragBillings() {
        billingsListFragment.setInfoBoardFragment(infoBoardBillingsFragment);

        FragmentSwitcher.addTransactionFragment(
                getChildFragmentManager(),
                billingsListFragment,
                fragBillings.getId()
        );
    }

    private void setupUIElements(View view){
        this.fragBillings = view.findViewById(R.id.fragBillings);
        this.fragInformationBoard = view.findViewById(R.id.informationBoard);
        this.btnIncome = view.findViewById(R.id.btnIncome);
    }

    private void handlerBtnIncome(){
        btnIncome.setOnClickListener(v->{

        });
    }

}


