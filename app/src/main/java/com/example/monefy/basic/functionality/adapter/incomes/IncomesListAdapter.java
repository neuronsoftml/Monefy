package com.example.monefy.basic.functionality.adapter.incomes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.OnItemClickListener;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.model.income.Income;

import java.util.List;

public class IncomesListAdapter extends BaseAdapter {
    private Context context;
    private List<Income> arrayList;
    private OnItemClickListener onItemClickListener;
    private ImageView icon;
    private TextView name, amount, typeCurrency, category, date;

    public IncomesListAdapter(Context context, List<Income> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    private void setupUIElements(View convertView){
        icon = convertView.findViewById(R.id.imageIncomes_list_item);
        name = convertView.findViewById(R.id.name);
        amount = convertView.findViewById(R.id.amount);
        typeCurrency = convertView.findViewById(R.id.typeCurrency);
        category = convertView.findViewById(R.id.category);
        date = convertView.findViewById(R.id.dateIncome);
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
            convertView = inflater.inflate(R.layout.incomes_list_aitem, null);
        }
        Income income = arrayList.get(position);

        setupUIElements(convertView);
        setValue(income);

        handlerClickItem(convertView, position);

        return convertView;
    }

    private void handlerClickItem(View convertView, int position){
        convertView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getIncome(position));
            }
        });
    }

    private void setValue(Income income){
        icon.setImageResource(income.getImageIncome());
        name.setText(income.getName());
        amount.setText(String.valueOf(income.getAmount()));
        typeCurrency.setText(income.getTypeCurrency());
        category.setText(income.getCategory());
        date.setText(String.valueOf(income.getDateReceived()));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private Income getIncome(int position) {
        return arrayList.get(position);
    }

    public void removeIncome(Income income){
        arrayList.remove(income);
    }
}