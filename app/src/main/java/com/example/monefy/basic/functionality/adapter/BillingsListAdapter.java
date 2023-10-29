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

import java.util.List;

public class BillingsListAdapter extends BaseAdapter {

    private Context context;
    private List<Billings> arrayList;

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

        ImageView imageView = convertView.findViewById(R.id.imageBillings);
        TextView typeBillings = convertView.findViewById(R.id.title_type_bills);
        TextView accountBalance = convertView.findViewById(R.id.account_balance);

        Billings billings = arrayList.get(position);

        if(!billings.getName().isEmpty()){
            typeBillings.setText(billings.getName());
        }else {
            typeBillings.setText(billings.getTypeBillings());
        }
        imageView.setImageResource(billings.getIdImageTypeBillings(billings.getTypeBillings()));
        accountBalance.setText(String.valueOf(billings.getBalance()));

        return convertView;
    }
}
