package com.example.monefy.basic.functionality.adapter.billings;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monefy.Manager.date.ManagerDate;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Ordinary;

public class ViewHolderOrdinary extends RecyclerView.ViewHolder {
    private TextView name, typeBillings, balance, typeCurrency, creditLimit, typeCurrencyCL, dateCreate;

    public ViewHolderOrdinary(@NonNull View itemView) {
        super(itemView);
        setupUIElements(itemView);
    }

    private void setupUIElements(View itemView){
        name = itemView.findViewById(R.id.txtName);
        balance = itemView.findViewById(R.id.txtBalance);
        typeCurrency = itemView.findViewById(R.id.txtTypeCurrency);
        typeBillings = itemView.findViewById(R.id.txtTypeBillings);
        creditLimit = itemView.findViewById(R.id.txtCreditLimit);
        typeCurrencyCL = itemView.findViewById(R.id.txtType_currencyV2);
        dateCreate = itemView.findViewById(R.id.textDateCreate);
    }

    public void setValueUIElement(Billings billings){
        Ordinary ordinary = (Ordinary) billings;
        name.setText(ordinary.getName());
        balance.setText(String.valueOf(ordinary.getBalance()));
        typeCurrency.setText(ordinary.getTypeCurrency());
        typeBillings.setText(ordinary.getTypeBillings());
        creditLimit.setText(String.valueOf(ordinary.getCreditLimit()));
        typeCurrencyCL.setText(ordinary.getTypeCurrency());

        String date = ManagerDate.convertFirebaseDateToString(ordinary.getDateReceived());
        dateCreate.setText(ManagerDate.convertFirebaseDateToLocalDate(date));
    }
}
