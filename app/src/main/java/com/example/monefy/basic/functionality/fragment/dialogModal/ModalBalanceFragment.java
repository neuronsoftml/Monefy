package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Obligation;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

public class ModalBalanceFragment extends DialogFragment{
    private TextView textViewTitleModal;
    private String titleModal, balance, typeCurrencies;
    private DialogCallback dialogCallback;
    public void checkDialogCallback(DialogCallback dialogCallback){
        this.dialogCallback = dialogCallback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        getInputArgument(getArguments());

        // Створення нового діалогу
        Dialog dialog = new Dialog(getContext());

        // Встановлення властивостей діалогу
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modal_bottom_billings_balance);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        setupUIDialogModal(dialog);
        startDialogModal();
        showCalculator();

        // Повернення налаштованого діалогу
        return dialog;
    }
    private void getInputArgument(Bundle arguments){
        titleModal = arguments.getString("titleModal");
        balance = arguments.getString("balance");
        typeCurrencies = arguments.getString("typeCurrency");
    }

    private void startDialogModal(){
        setValueObjectModal();
    }

    private FragmentContainerView fragContainerViewCalculator;

    // Ініціалізація UI
    private void setupUIDialogModal(Dialog dialog){
        textViewTitleModal = dialog.findViewById(R.id.title_modal_view);
        fragContainerViewCalculator = dialog.findViewById(R.id.fragContCalculator);
    }

    // додавання CalculatorFragment
    private void showCalculator(){
        CalculatorFragment calculatorFragment = new CalculatorFragment(balance, typeCurrencies);

        if (getChildFragmentManager().findFragmentById(fragContainerViewCalculator.getId()) == null) {
            getChildFragmentManager().beginTransaction()
                    .add(fragContainerViewCalculator.getId(),calculatorFragment)
                    .commit();

        }

        calculatorFragment.checkDialogCallback(new DialogCallback() {
            @Override
            public void onSuccess(String data) {
                balance = data;
                dialogCallback.onSuccess(balance);
            }

            @Override
            public void onFailure(Exception exception) {
                dialogCallback.onFailure(exception);
            }
        });
    }

    private void setValueObjectModal(){
        textViewTitleModal.setText(titleModal);
    }
    public String getBalance(){
        return balance;
    }
}
