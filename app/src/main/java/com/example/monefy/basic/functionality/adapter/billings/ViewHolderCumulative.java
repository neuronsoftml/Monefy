package com.example.monefy.basic.functionality.adapter.billings;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monefy.Manager.progress.ManagerProgress;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Cumulative;

public class ViewHolderCumulative extends RecyclerView.ViewHolder {
    private TextView name, balance, typeCurrency, goal, typeCurrencyV2;
    private ProgressBar progressBar;

    public ViewHolderCumulative(@NonNull View itemView) {
        super(itemView);
        setupUIElements(itemView);
    }

    private void setupUIElements(View itemView){
        name = itemView.findViewById(R.id.txtName);
        balance = itemView.findViewById(R.id.txtBalance);
        typeCurrency = itemView.findViewById(R.id.txtTypeCurrency);
        typeCurrencyV2 = itemView.findViewById(R.id.textTypeCurrencyV2);
        goal = itemView.findViewById(R.id.txtGoal);
        progressBar = itemView.findViewById(R.id.progressBar);
    }

    public void setValueUIElement(Billings billings){
        Cumulative cumulative = (Cumulative) billings;
        name.setText(cumulative.getName());
        balance.setText(String.valueOf(cumulative.getBalance()));
        typeCurrency.setText(cumulative.getTypeCurrency());
        typeCurrencyV2.setText(cumulative.getTypeCurrency());
        goal.setText(String.valueOf(cumulative.getGoal()));
        progressBar.setMax(100);
        progressBar.setProgress(ManagerProgress.calculateAccumulativeAmount(
                (int) cumulative.getBalance(),
                (int) cumulative.getGoal()
        ));
    }
}
