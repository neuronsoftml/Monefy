package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;

import com.example.monefy.basic.functionality.fragment.bank.MonoBank.CallbackMonoBank;
import com.example.monefy.basic.functionality.fragment.bank.MonoBank.MonoBankManager;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Obligation;
import com.example.monefy.basic.functionality.model.billings.TotalAmount;
import com.example.monefy.basic.functionality.model.billings.TotalDebt;
import com.example.monefy.basic.functionality.model.billings.TotalSavings;
import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;

import java.util.ArrayList;
import java.util.List;

public class InfoBoardBillingsFragment extends Fragment implements DataLoadListener {
    private BillingsListFragment billingsListFragment;
    private TextView tvTotalSavings, tvTotalAmount, tVTotalDebtOweMe,tVTotalDebtImGuilty;
    private final List<CurrencyMonoBank> currencyMonoBankList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_board_billings, container, false);
        setupUIElements(view);
        loadCurrencyMonoBank();
        return view;
    }

    private void setupUIElements(View view){
        this.tvTotalSavings = view.findViewById(R.id.tVTotalSavings);
        this.tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        this.tVTotalDebtOweMe = view.findViewById(R.id.tVTotalDebtOweMe);
        this.tVTotalDebtImGuilty =  view.findViewById(R.id.tVTotalDebtImGuilty);
    }

    public void setBillingsListFragment(BillingsListFragment billingsListFragment) {
        this.billingsListFragment = billingsListFragment;
    }

    @Override
    public void onDataLoaded() {
        if((billingsListFragment.getBillings() != null)){
            updateInfoBord(billingsListFragment.getBillings());
        }
    }

    private void setValueTotalAmount(List<Billings> billings) {
        TotalAmount totalAmount = new TotalAmount(0, billings, currencyMonoBankList);
        tvTotalAmount.setText(String.valueOf(totalAmount.getAmount()));
    }

    private void setValueTotalSaving(List<Billings> billings) {
       TotalSavings totalSavings = new TotalSavings(0, billings, currencyMonoBankList);
       tvTotalSavings.setText(String.valueOf(totalSavings.getAmount()));
    }

    private void setValueTotalDEBT(List<Billings> billings){
      TotalDebt totalDebt = new TotalDebt(billings,currencyMonoBankList);
      tVTotalDebtOweMe.setText(String.valueOf(totalDebt.calculateTotalDebtAmountInUAHByDebtor(Obligation.DEBT_TO_ME.getTitle())));
      tVTotalDebtImGuilty.setText(String.valueOf(totalDebt.calculateTotalDebtAmountInUAHByDebtor(Obligation.DEBT_TO_ANOTHER.getTitle())));
    }

    public void updateInfoBord(List<Billings> billings){
        setValueTotalAmount(billings);
        setValueTotalSaving(billings);
        setValueTotalDEBT(billings);
    }

    private void loadCurrencyMonoBank(){
        MonoBankManager monoBankManager = MonoBankManager.getMonoBankManager();
        monoBankManager.getCurrencyMonoBanksRates(new CallbackMonoBank() {
            @Override
            public void onResponse(List<CurrencyMonoBank> currencyMonoBankListCallBack) {
                currencyMonoBankList.addAll(currencyMonoBankListCallBack);
                updateInfoBord(billingsListFragment.getBillings());
            }

            @Override
            public void onFailure() {
                // залишили на майбутнє
            }
        });
    }

}