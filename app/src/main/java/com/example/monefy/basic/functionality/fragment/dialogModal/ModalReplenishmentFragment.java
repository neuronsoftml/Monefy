package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

public class ModalReplenishmentFragment extends DialogFragment {
    private Billings theBillToWhichWeTransfer; //Рахунок на який переказуємо.
    private Billings theBillFromWhichWeDebit; //Рахунок з якого списуєм.
    private FragmentContainerView fragContainerViewCalculator;
    private TextView nameBillingFromWhichDebit, nameBillingToWhichWeTransfer;
    private TextView balanceSpearFromWhichDebit, valueTypeBillingsFromWhichWeDebit, typeCurrencySpearFromWhichWeDebit;
    private TextView balanceEnrollToWhichWeTransfer,valueTypeBillingsEnrollToWhichWeTransfer, typeCurrencyEnrollToWhichWeTransfer;
    private ImageView imageCartFromBillWhichWeDebit;
    private ImageView imageCartBillEnrollToWhichWeTransfer;

    private final int contentView = R.layout.modal_bottom_replenishment;

    public ModalReplenishmentFragment(Billings theBillToWhichWeTransfer, Billings theBillFromWhichWeDebit) {
        this.theBillToWhichWeTransfer = theBillToWhichWeTransfer;
        this.theBillFromWhichWeDebit = theBillFromWhichWeDebit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(contentView);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        setupUIDialogModal(dialog);
        setValueObjectModal();
        showCalculator();

        return dialog;
    }

    public void startDialogModal(DialogCallback dialogCallback){

    }

    private void setupUIDialogModal(Dialog dialog){
        fragContainerViewCalculator = dialog.findViewById(R.id.fragContainerViewCalculator);

        nameBillingToWhichWeTransfer = dialog.findViewById(R.id.txtNameBillingToWhichWeTransfer);
        balanceEnrollToWhichWeTransfer = dialog.findViewById(R.id.txtBalanceEnrollToWhichWeTransfer);
        typeCurrencyEnrollToWhichWeTransfer = dialog.findViewById(R.id.txtTypeCurrencyEnrollToWhichWeTransfer);
        imageCartBillEnrollToWhichWeTransfer = dialog.findViewById(R.id.imageCartBillToWhichWeTransfer);
        valueTypeBillingsEnrollToWhichWeTransfer = dialog.findViewById(R.id.textValueTypeBillingsToWhichWeTransfer);

        nameBillingFromWhichDebit = dialog.findViewById(R.id.txtNameBillingFromWhichWeDebit);
        balanceSpearFromWhichDebit = dialog.findViewById(R.id.txtBalanceSpearFromWhichWeDebit);
        typeCurrencySpearFromWhichWeDebit = dialog.findViewById(R.id.txtTypeCurrencySpearFromWhichWeDebit);
        valueTypeBillingsFromWhichWeDebit = dialog.findViewById(R.id.textValueTypeBillingsFromWhichWeDebit);
        imageCartFromBillWhichWeDebit = dialog.findViewById(R.id.imageCartFromBillWhichWeDebit);

    }

    /**
     * Цей метот записує значення у UI елементів, з переданих обєктів.
     */
    private void setValueObjectModal(){
        //Для елементів що відповідає з рахунку
        nameBillingFromWhichDebit.setText(theBillFromWhichWeDebit.getName());
        balanceSpearFromWhichDebit.setText(String.valueOf(theBillFromWhichWeDebit.getBalance()));
        typeCurrencySpearFromWhichWeDebit.setText(theBillFromWhichWeDebit.getTypeCurrency());
        valueTypeBillingsFromWhichWeDebit.setText(theBillFromWhichWeDebit.getTypeBillings());
        imageCartFromBillWhichWeDebit.setImageResource(
                TypeBillings.getIdImageTypeBillings(theBillFromWhichWeDebit.getTypeBillings())
        );

        //Для елементів що відповідає на рахунок
        nameBillingToWhichWeTransfer.setText(theBillToWhichWeTransfer.getName());
        balanceEnrollToWhichWeTransfer.setText(String.valueOf(theBillToWhichWeTransfer.getBalance()));
        typeCurrencyEnrollToWhichWeTransfer.setText(theBillToWhichWeTransfer.getTypeCurrency());
        valueTypeBillingsEnrollToWhichWeTransfer.setText(theBillToWhichWeTransfer.getTypeBillings());
        imageCartBillEnrollToWhichWeTransfer.setImageResource(
                TypeBillings.getIdImageTypeBillings(theBillToWhichWeTransfer.getTypeBillings())
        );

    }

    private void showCalculator(){
        CalculatorFragment calculatorFragment = new CalculatorFragment("0", theBillFromWhichWeDebit.getTypeCurrency());

        if (getChildFragmentManager().findFragmentById(fragContainerViewCalculator.getId()) == null) {
            getChildFragmentManager().beginTransaction()
                    .add(fragContainerViewCalculator.getId(),calculatorFragment)
                    .commit();

        }

        calculatorFragment.checkDialogCallback(new DialogCallback() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }
}
