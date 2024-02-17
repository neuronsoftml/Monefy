package com.example.monefy.basic.functionality.fragment.billings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;

import com.example.monefy.basic.functionality.Interface.bank.monoBank.CallbackMonoBank;
import com.example.monefy.basic.functionality.Interface.billings.OnBillingsCallback;
import com.example.monefy.basic.functionality.controller.bank.monoBank.MonoBankController;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Obligation;
import com.example.monefy.basic.functionality.model.billings.TotalAmount;
import com.example.monefy.basic.functionality.model.billings.TotalDebt;
import com.example.monefy.basic.functionality.model.billings.TotalSavings;
import com.example.monefy.basic.functionality.model.currency.CurrencyMonoBank;

import java.util.ArrayList;
import java.util.List;

public class InfoBoardBillingsFragment extends Fragment implements OnBillingsCallback {
    private TextView tvTotalSavings, tvTotalAmount, tVTotalDebtOweMe,tVTotalDebtImGuilty;

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

    private final List<Billings> billingsList = new ArrayList<>();
    private boolean isLoadBilling = false;

    private final List<CurrencyMonoBank> currencyMonoBankList = new ArrayList<>();
    private boolean isLoadCurrencyBank = false;

    private void checkDataReadiness(){
        if(isLoadBilling && isLoadCurrencyBank){
            updateInfoBord(billingsList, currencyMonoBankList);
        }
    }

    private void loadCurrencyMonoBank(){
        MonoBankController monoBankController = MonoBankController.getMonoBankManager();
        monoBankController.getCurrencyMonoBanksRates(new CallbackMonoBank() {
            @Override
            public void onResponse(List<CurrencyMonoBank> currencyMonoBankListCallBack) {
                isLoadCurrencyBank = true;
                currencyMonoBankList.addAll(currencyMonoBankListCallBack);
                checkDataReadiness();
            }

            @Override
            public void onFailure() {
                // залишили на майбутнє
            }
        });
    }

    @Override
    public void onBillingsDataReceived(List<Billings> billingsListCallBack) {
        if(billingsListCallBack != null){
            isLoadBilling = true;
            billingsList.addAll(billingsListCallBack);
            checkDataReadiness();
        }
    }

    @Override
    public void onDataNotFound() {

    }

    private void updateInfoBord(List<Billings> billings, List<CurrencyMonoBank> currencyMonoBankList){
        setValueTotalAmount(billings,currencyMonoBankList);
        setValueTotalSaving(billings, currencyMonoBankList);
        setValueTotalDEBT(billings, currencyMonoBankList);
    }

    private void setValueTotalAmount(List<Billings> billings , List<CurrencyMonoBank> currencyMonoBankList) {
        TotalAmount totalAmount = new TotalAmount(0, billings, currencyMonoBankList);
        tvTotalAmount.setText(String.valueOf(totalAmount.getAmount()));
    }

    private void setValueTotalSaving(List<Billings> billings, List<CurrencyMonoBank> currencyMonoBankList) {
        TotalSavings totalSavings = new TotalSavings(0, billings, currencyMonoBankList);
        tvTotalSavings.setText(String.valueOf(totalSavings.getAmount()));
    }

    private void setValueTotalDEBT(List<Billings> billings, List<CurrencyMonoBank> currencyMonoBankList){
        TotalDebt totalDebt = new TotalDebt(billings,currencyMonoBankList);
        tVTotalDebtOweMe.setText(String.valueOf(totalDebt.calculateTotalDebtAmountInUAHByDebtor(Obligation.DEBT_TO_ME.getTitle())));
        tVTotalDebtImGuilty.setText(String.valueOf(totalDebt.calculateTotalDebtAmountInUAHByDebtor(Obligation.DEBT_TO_ANOTHER.getTitle())));
    }

    public void mustBeUpdatedBillings(){
        isLoadBilling = false;
        billingsList.clear();
    }
}