package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.billings.CreateBillingsFragment;
import com.example.monefy.basic.functionality.fragment.income.CreateIncomeFragment;

public class ModalCreate extends DialogMenu{

    private Button btnBillings, btnIncome, btnLiability;
    private Dialog dialog;
    private Context context;
    private FragmentManager fragmentManager;

    public ModalCreate(Context context, int contentView, FragmentManager fragmentManager) {
        super(context, contentView);
        this.context = context;
        this.dialog = getDialogModal();
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void modalStart(DialogCallback dialogCallback) {
        openModal();
        setupUIDialogModal();
        handlerButtonDialogModal(dialogCallback);
    }

    @Override
    public void setupUIDialogModal() {
        btnBillings = dialog.findViewById(R.id.btn_modal_billings_create);
        btnIncome = dialog.findViewById(R.id.btn_modal_asset_create);
        btnLiability = dialog.findViewById(R.id.btn_modal_liability_create);
    }

    @Override
    public void handlerButtonDialogModal(DialogCallback dialogCallback) {
        handlerBtnBillings();
        handlerBtnIncome();
    }

    public void handlerBtnBillings(){
        btnBillings.setOnClickListener(v->{
            ModalTypeBillings modalTypeBillings = new ModalTypeBillings(context);
            modalTypeBillings.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    Bundle bundle = new Bundle();
                    bundle.putString("TypeBillings", modalTypeBillings.getUpdateData());
                    FragmentSwitcher.replaceFragmentToDate(
                            new CreateBillingsFragment(),
                            bundle,
                            context,
                            FragmentSwitcher.getContainerHome()
                    );
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
            exitModal();
        });
    }

    public void handlerBtnIncome(){
        btnIncome.setOnClickListener(v->{
            FragmentSwitcher.replaceFragment(
                    new CreateIncomeFragment(),
                    context,
                    FragmentSwitcher.getContainerHome()
            );
            exitModal();
        });
    }
}
