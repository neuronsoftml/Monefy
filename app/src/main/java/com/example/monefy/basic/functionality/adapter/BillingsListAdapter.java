package com.example.monefy.basic.functionality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.TypeBillings;

import java.util.List;

public class BillingsListAdapter extends BaseAdapter {

    private Context context;
    private List<Billings> arrayList;
    private OnItemClickListener onItemClickListener;
    private ImageView imageView;
    private TextView typeBillings, accountBalance, typeCurrency, creditLimit, typeCurrencyObl;
    private TextView titleObligation;

    public BillingsListAdapter(Context context, List<Billings> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    private void setupUIElements(View convertView){
        imageView = convertView.findViewById(R.id.imageBillings_list_item);
        typeBillings = convertView.findViewById(R.id.title_name_billings_list_item);
        accountBalance = convertView.findViewById(R.id.balance_billings_list_item);

        typeCurrency = convertView.findViewById(R.id.type_currency_list_item_1);
        creditLimit = convertView.findViewById(R.id.credit_limit_list_item);
        typeCurrencyObl = convertView.findViewById(R.id.type_currency_list_item_2);

        titleObligation = convertView.findViewById(R.id.tV_item_title_obligation);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.billings_list_aitem, null);
        }
        Billings billings = arrayList.get(position);

        setupUIElements(convertView);
        setValue(billings);

        handlerClickItem(convertView, position);

        return convertView;
    }

    private void handlerClickItem(View convertView, int position){
        convertView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getBillings(position));
            }
        });
    }

    private void setValue(Billings billings){
        if (!billings.getName().isEmpty()) {
            typeBillings.setText(billings.getName());
        } else {
            typeBillings.setText(billings.getTypeBillings());
        }

        imageView.setImageResource(TypeBillings.getIdImageTypeBillings((billings.getTypeBillings())));
        accountBalance.setText(String.valueOf(billings.getBalance()));

        typeCurrency.setText(billings.getTypeCurrency());
        typeCurrencyObl.setText(billings.getTypeCurrency());
        creditLimit.setText(String.valueOf(billings.getCreditLimit()));

        titleObligation.setText(billings.getObligation());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private Billings getBillings(int position) {
        return arrayList.get(position);
    }

    public void removeBillings(Billings billing){
        arrayList.remove(billing);
    }
}