package com.example.monefy.basic.functionality.adapter;

import android.content.Context;
import android.util.Log;
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

    public BillingsListAdapter(Context context, List<Billings> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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

        ImageView imageView = convertView.findViewById(R.id.imageBillings_list_item);
        TextView typeBillings = convertView.findViewById(R.id.title_name_billings_list_item);
        TextView accountBalance = convertView.findViewById(R.id.balance_billings_list_item);

        TextView typeCurrency = convertView.findViewById(R.id.type_currency_list_item_1);
        TextView creditLimit = convertView.findViewById(R.id.credit_limit_list_item);
        TextView typeCurrencyCreditLimit = convertView.findViewById(R.id.type_currency_list_item_2);

        Billings billings = arrayList.get(position);

        if (!billings.getName().isEmpty()) {
            typeBillings.setText(billings.getName());
        } else {
            typeBillings.setText(billings.getTypeBillings());
        }

        imageView.setImageResource(TypeBillings.getIdImageTypeBillings((billings.getTypeBillings())));
        accountBalance.setText(String.valueOf(billings.getBalance()));

        typeCurrency.setText(billings.getTypeCurrency());
        creditLimit.setText(String.valueOf(billings.getCreditLimit()));
        typeCurrencyCreditLimit.setText(billings.getTypeCurrency());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(getBillings(position));
                }
            }
        });

        return convertView;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public Billings getBillings(int position) {
        return arrayList.get(position);
    }

    public void removeBillings(Billings billing){
        arrayList.remove(billing);
    }
}