package com.example.monefy.basic.functionality.adapter.billings;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monefy.Manager.date.ManagerDate;
import com.example.monefy.Manager.progress.ManagerProgress;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Debt;

import java.text.ParseException;

public class ViewHolderDebt extends RecyclerView.ViewHolder {
    private TextView name, debtor, balance, typeCurrency, returnDate, dateCreate;
    private ProgressBar progressBar;

    public ViewHolderDebt(@NonNull View itemView) {
        super(itemView);
        setupUIElements(itemView);
    }

    private void setupUIElements(View itemView){
        name = itemView.findViewById(R.id.txtName);
        balance = itemView.findViewById(R.id.txtBalance);
        typeCurrency = itemView.findViewById(R.id.txtTypeCurrencyV1);
        debtor = itemView.findViewById(R.id.txtDebtor);
        returnDate = itemView.findViewById(R.id.returnDate);
        progressBar = itemView.findViewById(R.id.progressBar);
        dateCreate = itemView.findViewById(R.id.textDateCreate);
    }

    public void setValueUIElement(Billings billings) throws ParseException {
        Debt debt = (Debt) billings;
        name.setText(debt.getName());
        balance.setText(String.valueOf(debt.getBalance()));
        typeCurrency.setText(debt.getTypeCurrency());
        debtor.setText(debt.getDebtor());
        returnDate.setText(ManagerDate.convertFirebaseDateToLocalDate(
                ManagerDate.convertFirebaseDateToString(
                        debt.getReturnDate()
                )
        ));

        progressBar.setMax(100);
        progressBar.setProgress(ManagerProgress.calculatePercentage(
                ManagerDate.convertFirebaseDateToLocalDate(
                        ManagerDate.convertFirebaseDateToString(
                                debt.getDateReceived()
                        )),
                ManagerDate.convertFirebaseDateToLocalDate(
                        ManagerDate.convertFirebaseDateToString(
                                debt.getReturnDate()) )
        ));


        String date = ManagerDate.convertFirebaseDateToString(debt.getDateReceived());
        dateCreate.setText(ManagerDate.convertFirebaseDateToLocalDate(date));
    }
}
