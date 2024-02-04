package com.example.monefy.basic.functionality.adapter.billings;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.OnItemClickListener;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.Cumulative;
import com.example.monefy.basic.functionality.model.billings.Debt;
import com.example.monefy.basic.functionality.model.billings.Ordinary;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

import java.text.ParseException;
import java.util.List;

public class BillingsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Billings> billingsList;
    private OnItemClickListener onItemClickListener;

    private final int ORDINARY = 0;
    private final int DEBT = 1;
    private final int CUMULATIVE = 2;

    public BillingsListAdapter(List<Billings> billingsList) {
        this.billingsList = billingsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ORDINARY:
                return new ViewHolderOrdinary(inflater.inflate(R.layout.billings_list_aitem_ordinary, parent, false));
            case DEBT:
                return new ViewHolderDebt(inflater.inflate(R.layout.billings_list_aitem_debt, parent, false));
            case CUMULATIVE:
                return new ViewHolderCumulative(inflater.inflate(R.layout.billings_list_aitem_cumulative, parent, false));
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Отримати дані та встановити значення UI елементів для кожного типу
        Billings billing = billingsList.get(position);

        if (billing instanceof Ordinary) {
            ViewHolderOrdinary viewHolderOrdinary = (ViewHolderOrdinary) holder;
            viewHolderOrdinary.setValueUIElement(billing);
            handlerClickItem(holder.itemView, position);

        } else if (billing instanceof Debt) {
            ViewHolderDebt viewHolderDebt = (ViewHolderDebt) holder;
            try {
                viewHolderDebt.setValueUIElement(billing);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            handlerClickItem(holder.itemView, position);

        } else if (billing instanceof Cumulative) {
            ViewHolderCumulative viewHolderCumulative = (ViewHolderCumulative) holder;
            viewHolderCumulative.setValueUIElement(billing);
            handlerClickItem(holder.itemView, position);
        }
    }

    @Override
    public long getItemId(int position) {
        Billings billing = billingsList.get(position);

        if (billing instanceof Ordinary) {
            return ORDINARY;
        } else if (billing instanceof Debt) {
            return DEBT;
        } else if (billing instanceof Cumulative) {
            return CUMULATIVE;
        }

        return position;
    }

    @Override
    public int getItemCount() {
        return billingsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Billings billing = billingsList.get(position);

        if (billing instanceof Ordinary) {
            return ORDINARY;
        } else if (billing instanceof Debt) {
            return DEBT;
        } else if (billing instanceof Cumulative) {
            return CUMULATIVE;
        }else {
            return super.getItemViewType(position);
        }
    }

    private void handlerClickItem(View convertView, int position){
        convertView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getBillings(position));
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private Billings getBillings(int position) {
        return billingsList.get(position);
    }

    public void removeBillings(Billings billing){
        billingsList.remove(billing);
    }
}