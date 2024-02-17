package com.example.monefy.basic.functionality.adapter.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.billings.OnBillingCallback;
import com.example.monefy.basic.functionality.Interface.adapter.OnItemClickListener;
import com.example.monefy.basic.functionality.controller.billings.BillingsController;
import com.example.monefy.basic.functionality.controller.date.DateController;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.history.HistoryBilling;

import java.util.ArrayList;
import java.util.List;

public class HistoryBillingsAdapter extends BaseAdapter {
    private List<HistoryBilling> historyBillingList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private ImageView icon;
    private TextView  name, dateReceived, typeCurrency, typeTransfer, suma;
    private Context context;

    public HistoryBillingsAdapter(Context context,List<HistoryBilling> historyBillingList) {
        this.context = context;
        this.historyBillingList = historyBillingList;
    }


    /** Цей метод здійснює ініціалізацію UI елементів які знаходять в фрагменті
     * @param convertView віджети
     */
    private void setupUIElements(View convertView){
        icon = convertView.findViewById(R.id.imageIcon);
        name = convertView.findViewById(R.id.textName);
        dateReceived = convertView.findViewById(R.id.textDate);
        typeTransfer = convertView.findViewById(R.id.textTypeTransfer);
        suma = convertView.findViewById(R.id.textSuma);
        typeCurrency = convertView.findViewById(R.id.textTypeCurrency);
    }

    @Override
    public int getCount() {
        return historyBillingList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyBillingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.history_billing_aitem, null);
        }
        HistoryBilling historyBilling = historyBillingList.get(position);

        setupUIElements(convertView);
        setValueUIElements(historyBilling);

        //Треба ще добавити обробник кліку.
        return convertView;
    }

    private void setValueUIElements(HistoryBilling historyBilling){
        dateReceived.setText(DateController.convertFirebaseDateToString(historyBilling.getDateReceived()));
        typeTransfer.setText(historyBilling.getTypeTransfer());
        suma.setText(String.valueOf(historyBilling.getSuma()));
        typeCurrency.setText(historyBilling.getTypeCurrency());
        setName(historyBilling.getUIDObjectTransfer());
    }

    public void updateHistoryBillingsList(List<HistoryBilling> historyBillingList){
        this.historyBillingList.clear();
        this.historyBillingList.addAll(historyBillingList);
    }

   //Тимчасовий метод
    private void setName(String historyBilling){
        BillingsController.getBillingExceptOne(historyBilling, new OnBillingCallback() {
            @Override
            public void onBillingsDataReceived(Billings billing) {
                name.setText(billing.getName());
            }

            @Override
            public void onDataNotFound() {
                name.setText(R.string.textTheAccountIsNoLongerInExistence);
            }
        });
    }
}
