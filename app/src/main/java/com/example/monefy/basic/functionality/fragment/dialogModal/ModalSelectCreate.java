package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.monefy.basic.functionality.Interface.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.controller.enumType.EnumTypeController;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;
import com.example.monefy.basic.functionality.fragment.billings.CreateBillingsFragment;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

public class ModalSelectCreate extends DialogModal {

    private Button btnBillings, btnIncome, btnLiability;
    private final Context context;
    private final Dialog dialog;
    private final FragmentManager fragmentManager;

    public ModalSelectCreate(Context context, int contentView, FragmentManager fragmentManager, DialogCallback dialogCallback) {
        super(context, contentView);
        this.context = context;
        this.dialog = getDialogModal();
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void modalStart() {
        openModal();
        setupUIDialogModal();
        handlerButtonDialogModal();
    }

    @Override
    public void setupUIDialogModal() {
        btnBillings = dialog.findViewById(R.id.btn_modal_billings_create);
        btnIncome = dialog.findViewById(R.id.btn_modal_asset_create);
        btnLiability = dialog.findViewById(R.id.btn_modal_liability_create);
    }

    @Override
    public void handlerButtonDialogModal() {
        handlerBtnBillings();
        handlerBtnIncome();
    }

    public void handlerBtnBillings(){
        btnBillings.setOnClickListener(v->{

            ModalFunctionalSelect modalFunctionalSelect = new ModalFunctionalSelect(
                    context,
                    R.string.textSelectTypeBillings,
                    EnumTypeController.getTypesBillings(),
                    TypeBillings.class,
                    new DialogCallback() {
                        @Override
                        public void onSuccess(String data) {
                            Bundle bundle = new Bundle();
                            bundle.putString("TypeBillings", data);

                            CreateBillingsFragment createBillingsFragment = new CreateBillingsFragment();
                            createBillingsFragment.setArguments(bundle);

                            FragmentNavigation.replaceFragment(
                                    fragmentManager,
                                    createBillingsFragment,
                                    FragmentNavigation.getContainerHome(),
                                    "CreateBillingsFragment"
                            );
                            exitModal();
                        }

                        @Override
                        public void onFailure(Exception exception) {

                        }
                    }
            );
            modalFunctionalSelect.modalStart();
        });
    }

    public void handlerBtnIncome(){
        btnIncome.setOnClickListener(v->{
            /*
            FragmentSwitcher.replaceFragment(
                    new CreateIncomeFragment(),
                    context,
                    FragmentSwitcher.getContainerHome()
            );
            Необхідно дописати переход
             */
            exitModal();
        });
    }
}
