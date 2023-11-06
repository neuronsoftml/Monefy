package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.Obligation;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.tools.message.ToastManager;

import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;
import java.util.List;

public class ModalBalance implements  DialogModal{
    private LinearLayout elementKeyboard, linearLayoutToggleButtonDebt;
    private Dialog dialogModal;
    private TextView textViewModalBalance, textViewTitleModal;
    private List<Button> buttonListModalNumber =  new ArrayList<>();
    private Button buttonNumberZero, buttonNumberOne, buttonNumberTwo, buttonNumberThree,
            buttonNumberFour, buttonNumberFive, buttonNumberSix,
            buttonNumberSeven, buttonNumberEight, buttonNumberNine;
    private ImageButton buttonDivision, buttonMultiplication, buttonSubtraction,
            buttonAddition, buttonSetUp, buttonDelete;
    private TextView textViewTypeMoney;
    private ToggleButton toggleButtonDebtIMust, toggleButtonDebtIAmOwed;
    private final char SHARE = '\u00F7';
    private final char MULTIPLY = '\u00D7';
    private final char SUBTRACT = '-';
    private final char ADD = '+';
    private Drawable DRAWABLE_CHECK;
    private Drawable DRAWABLE_HANDLE;
    private Context context;
    private String updateBalance;
    private String updateToWhomHeOwes;
    private String titleModal;
    private String balance;
    private String typeCurrencies;
    private String typeBillings;

    public ModalBalance(Context context, View view, String title, String balance, String typeCurrencies, String typeBillings){
        this.context = context;
        this.DRAWABLE_CHECK = view.getResources().getDrawable(R.drawable.icon_baseline_check_34);
        this.DRAWABLE_HANDLE = view.getResources().getDrawable(R.drawable.icon_baseline_drag_handle_34);
        this.titleModal = title;
        this.balance = balance;
        this.typeCurrencies = typeCurrencies;
        this.typeBillings = typeBillings;
    }
    @Override
    public void modalStart(DialogCallback dialogCallback){
        showDialogModal();
        setupUIDialogModal();
        switcherStyleInterface(typeBillings);
        setValueObjectModal();
        if(!balance.isEmpty()){
            textViewModalBalance.setText(balance);
        }
        sheetFillerButtonListModalNumber();
        handlerButtonDialogModal(dialogCallback);
    }


    @Override
    public void showDialogModal() {
        dialogModal = new Dialog(context);
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(R.layout.modal_bottom_billings_balance);
        dialogModal.show();
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void setupUIDialogModal(){
        buttonNumberZero = dialogModal.findViewById(R.id.buttonZero);
        buttonNumberOne = dialogModal.findViewById(R.id.buttonOne);
        buttonNumberTwo = dialogModal.findViewById(R.id.buttonTwo);
        buttonNumberThree = dialogModal.findViewById(R.id.buttonThree);
        buttonNumberFour = dialogModal.findViewById(R.id.buttonFour);
        buttonNumberFive = dialogModal.findViewById(R.id.buttonFive);
        buttonNumberSix = dialogModal.findViewById(R.id.buttonSix);
        buttonNumberSeven = dialogModal.findViewById(R.id.buttonSeven);
        buttonNumberEight = dialogModal.findViewById(R.id.buttonEight);
        buttonNumberNine = dialogModal.findViewById(R.id.buttonNine);
        buttonDivision = dialogModal.findViewById(R.id.buttonDivision);
        buttonMultiplication = dialogModal.findViewById(R.id.buttonMultiplication);
        buttonSubtraction = dialogModal.findViewById(R.id.buttonSubtraction);
        buttonAddition = dialogModal.findViewById(R.id.buttonAddition);
        buttonDelete = dialogModal.findViewById(R.id.buttonDelete);
        buttonSetUp = dialogModal.findViewById(R.id.buttonSetUp);
        textViewModalBalance = dialogModal.findViewById(R.id.balance_billings);
        textViewTitleModal = dialogModal.findViewById(R.id.title_modal_view);
        textViewTypeMoney = dialogModal.findViewById(R.id.icon_type_money);
        linearLayoutToggleButtonDebt = dialogModal.findViewById(R.id.linearLayoutToggleButton_debt);
        toggleButtonDebtIMust = dialogModal.findViewById(R.id.toggleButton_debt_i_must);
        toggleButtonDebtIAmOwed = dialogModal.findViewById(R.id.toggleButton_debt_i_am_owed);
    }

    private void sheetFillerButtonListModalNumber(){
        elementKeyboard = dialogModal.findViewById(R.id.elementKeyboard);

        for (int i = 0; i < elementKeyboard.getChildCount(); i++) {
            View innerLayout = elementKeyboard.getChildAt(i);

            if (innerLayout instanceof LinearLayout) {
                List<Button> buttonsInInnerLayout = new ArrayList<>();

                for (int j = 0; j < ((LinearLayout) innerLayout).getChildCount(); j++) {
                    View childView = ((LinearLayout) innerLayout).getChildAt(j);

                    if (childView instanceof Button) {
                        buttonsInInnerLayout.add((Button) childView);
                    }
                }

                buttonListModalNumber.addAll(buttonsInInnerLayout);
            }
        }

    }

    private void setValueObjectModal(){
        toggleButtonDebtIMust.setChecked(true);
        toggleButtonDebtIAmOwed.setChecked(false);

        textViewTitleModal.setText(titleModal);
        textViewTypeMoney.setText(typeCurrencies);
    }

    @Override
    public void handlerButtonDialogModal(DialogCallback dialogCallback){
        handlerButtonNumberModal();
        handlerButtonMathSymbolsModal();
        handlerButtonSpecialModal(dialogCallback);
    }

    private void handlerButtonNumberModal(){
        for(Button button : buttonListModalNumber){
            button.setOnClickListener(v->{
                String data = (String) textViewModalBalance.getText().toString();
                if(!data.isEmpty() && !checkMathSymbols(data) && Long.parseLong(data) == 0){
                    textViewModalBalance.setText(button.getText().toString());
                    buttonSetUp.setImageDrawable(DRAWABLE_CHECK);
                }else{
                    textViewModalBalance.setText(data + button.getText().toString());
                }
            });
        }
    }

    private void handlerButtonMathSymbolsModal(){

        buttonDivision.setOnClickListener(v -> {
            textViewModalBalance.setText(getTextViewBalance() + SHARE);
            buttonSetUp.setImageDrawable(DRAWABLE_HANDLE);
        });

        buttonMultiplication.setOnClickListener(v ->{
            textViewModalBalance.setText(getTextViewBalance() + MULTIPLY);
            buttonSetUp.setImageDrawable(DRAWABLE_HANDLE);
        });

        buttonSubtraction.setOnClickListener(v ->{
            textViewModalBalance.setText(getTextViewBalance() + SUBTRACT);
            buttonSetUp.setImageDrawable(DRAWABLE_HANDLE);
        });

        buttonAddition.setOnClickListener(v ->{
            textViewModalBalance.setText(getTextViewBalance() + ADD);
            buttonSetUp.setImageDrawable(DRAWABLE_HANDLE);
        });
    }

    private void handlerButtonSpecialModal(DialogCallback dialogCallback){
        buttonDelete.setOnClickListener(v-> {
            String data = (String) textViewModalBalance.getText().toString();

            if (!data.isEmpty()) {
                data = data.substring(0, data.length() - 1);
                textViewModalBalance.setText(data);
            }
        });

        buttonSetUp.setOnClickListener(v ->{
            String balance = (String) textViewModalBalance.getText().toString();

            if (!balance.isEmpty() && checkMathSymbols(balance)) {
                buttonSetUp.setImageDrawable(DRAWABLE_CHECK);
                textViewModalBalance.setText(mathCalculation(balance));
                ToastManager.showToastOnFailure(context,R.string.toast_successful_entered_the_data);
            } else if (!balance.isEmpty() && !checkMathSymbols(balance)) {
                if(dialogCallback != null){
                    updateData(balance);
                    dialogCallback.onSuccess();
                }
                dialogModal.cancel();
                ToastManager.showToastOnFailure(context,R.string.toast_successful_entered_the_data);
            }
        });
    }

    private String getTextViewBalance(){
        String data = (String) textViewModalBalance.getText().toString();
        return data;
    }

    private String mathCalculation(String data){
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);
        Scriptable scope = rhino.initStandardObjects();

        try {
            Object result = rhino.evaluateString(scope, data, "MathCalculation", 1, null);

            if (result instanceof Double) {
                long longResult = Math.round((Double) result);
                return String.valueOf(longResult);
            } else {
                return String.valueOf(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastManager.showToastOnFailure(context, R.string.toast_failure_entered_the_data);
            return "0";
        } finally {
            org.mozilla.javascript.Context.exit();
        }
    }

    private boolean checkMathSymbols(String data){
        char[] arrayCharData = data.toCharArray();

        for (char arrayCharDatum : arrayCharData) {
            if (arrayCharDatum == SHARE) {
                return true;
            } else if (arrayCharDatum == MULTIPLY) {
                return true;
            } else if (arrayCharDatum == SUBTRACT) {
                return true;
            } else if (arrayCharDatum == ADD) {
                return true;
            }
        }
        return false;
    }

    public String getUpdateBalance() {
        return updateBalance;
    }

    private void switcherStyleInterface(String argumentTypeBillings){
        if(argumentTypeBillings.equals(TypeBillings.DEBT.getTitle())){
            linearLayoutToggleButtonDebt.setVisibility(View.VISIBLE);
            handlerToggleButton();
        }
    }

    private void handlerToggleButton() {
        toggleButtonDebtIAmOwed.setOnClickListener(v->{
            switcherToggleButton(toggleButtonDebtIAmOwed);
        });
        toggleButtonDebtIMust.setOnClickListener(v->{
            switcherToggleButton(toggleButtonDebtIMust);
        });
    }

    private void switcherToggleButton(ToggleButton toggleButton) {
        if(toggleButtonDebtIAmOwed == toggleButton){
            toggleButtonDebtIMust.setChecked(false);
            toggleButtonDebtIAmOwed.setChecked(true);
            textViewTitleModal.setText(Obligation.DEBT_TO_ME.getTitle());
        }
        if(toggleButtonDebtIMust == toggleButton){
            toggleButtonDebtIAmOwed.setChecked(false);
            toggleButtonDebtIMust.setChecked(true);
            textViewTitleModal.setText(Obligation.DEBT_TO_ANOTHER.getTitle());
        }
    }

    private void updateData(String updateBalance){
        this.updateBalance = updateBalance;
        if(typeBillings.equals(TypeBillings.DEBT.getTitle())){
            this.updateToWhomHeOwes = textViewTitleModal.getText().toString();
        }
    }

    public String getUpdateToWhomHeOwes() {
        return updateToWhomHeOwes;
    }
}
