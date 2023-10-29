package com.example.monefy.basic.functionality.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.example.monefy.tools.message.ToastManager;

import java.util.ArrayList;
import java.util.List;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class CreateBillingsFragment extends Fragment {
    private String argumentTypeBillings;
    private TextView textViewTypeBillings, textViewTypeCurrency, textViewModalBalance, textViewBalanceBillings;
    private LinearLayout linerLayoutTypeBillings, linerLayoutTypeCurrency, linerLayout_balance_billings;
    private Dialog dialogModal;
    private LinearLayout elementTypeCurrencies, elementKeyboard;
    private List<Button> buttonListModalTypeCurrency = new ArrayList<>();
    private Button buttonOrdinary, buttonDebt, buttonCumulative;
    private List<Button> buttonListModalNumber =  new ArrayList<>();
    private Button buttonNumberZero, buttonNumberOne, buttonNumberTwo, buttonNumberThree,
            buttonNumberFour, buttonNumberFive, buttonNumberSix,
            buttonNumberSeven, buttonNumberEight, buttonNumberNine;

    private ImageButton buttonDivision, buttonMultiplication, buttonSubtraction,
            buttonAddition, buttonSetUp, buttonDelete;

    private Drawable DRAWABLE_CHECK;
    private Drawable DRAWABLE_HANDLE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            argumentTypeBillings = getArguments().getString("TypeBillings");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_billings, container, false);

        DRAWABLE_CHECK = getResources().getDrawable(R.drawable.icon_baseline_check_34);
        DRAWABLE_HANDLE = getResources().getDrawable(R.drawable.icon_baseline_drag_handle_34);

        setupUIElements(view);
        textViewTypeBillings.setText(argumentTypeBillings);
        setupClickListeners();
        return view;
    }

    private void setupUIElements(View view) {
        textViewTypeBillings = view.findViewById(R.id.type_billings);
        textViewTypeCurrency = view.findViewById(R.id.text_type_currency);
        textViewBalanceBillings = view.findViewById(R.id.balance_billings);
        linerLayoutTypeBillings = view.findViewById(R.id.linerLayout_type_billings);
        linerLayoutTypeCurrency = view.findViewById(R.id.linerLayout_type_currency);
        linerLayout_balance_billings = view.findViewById(R.id.linerLayout_balance_billings);
    }

    private void setupClickListeners() {
        linerLayoutTypeBillings.setOnClickListener(v -> {
            showDialogModal(R.layout.model_bootom_type_billings);
            setupUIDialogModalTypeBillings(dialogModal);
        });

        linerLayoutTypeCurrency.setOnClickListener(v -> {
            showDialogModal(R.layout.model_bootom_type_currencies);
            createButtonDialogModalTypeCurrency(dialogModal);
            setupUIDialogModalTypeCurrency();
            handlerButtonDialogModalTypeCurrency();
        });

        linerLayout_balance_billings.setOnClickListener(v -> {
            showDialogModal(R.layout.modal_bootom_billings_balance);
            sheetFillerButtonListModalNumber(dialogModal);
            setupUIDialogModalBalance(dialogModal);
            handlerButtonDialogModalBalance();
        });
    }

    private void showDialogModal(int idModel) {
        dialogModal = new Dialog(getContext());
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(idModel);
        dialogModal.show();
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void createButtonDialogModalTypeCurrency(Dialog dialog) {
        elementTypeCurrencies = dialog.findViewById(R.id.linearLayoutListButton);
        for (TypeCurrency typeCurrency : TypeCurrency.values()) {
            Button button = createCurrencyButton(typeCurrency);
            elementTypeCurrencies.addView(button);
            buttonListModalTypeCurrency.add(button);
        }
    }

    private Button createCurrencyButton(TypeCurrency typeCurrency) {
        Button button = new Button(getContext());
        button.setText(typeCurrency.getTypeCurrencyTitle());
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(typeCurrency.getIdIconTypeCurrency(), 0, 0, 0);
        button.setCompoundDrawablePadding(10);
        button.setTag(typeCurrency.toString());
        button.setGravity(Gravity.CENTER_VERTICAL);
        button.setAllCaps(false);
        button.setBackgroundColor(Color.WHITE);
        return button;
    }

    private void setupUIDialogModalTypeBillings(Dialog dialog) {
        buttonOrdinary = dialog.findViewById(R.id.button_ordinary);
        buttonDebt = dialog.findViewById(R.id.button_debt);
        buttonCumulative = dialog.findViewById(R.id.button_cumulative);
    }

    private void handlerButtonDialogModalTypeCurrency() {
        for (Button button : buttonListModalTypeCurrency) {
            button.setOnClickListener(v -> {
                for (TypeCurrency typeCurrency : TypeCurrency.values()) {
                    if (button.getText().toString().equals(typeCurrency.getTypeCurrencyTitle())) {
                        textViewTypeCurrency.setText(typeCurrency.getTypeCurrencyTitle());
                        dialogModal.cancel();
                        ToastManager.showToastOnSuccessfulChanges(getContext());
                    }
                }
            });
        }
    }

    private void setupUIDialogModalTypeCurrency(){
        for (int i = 0; i < elementTypeCurrencies.getChildCount(); i++) {
            View childView = elementTypeCurrencies.getChildAt(i);

            if (childView instanceof Button) {
                buttonListModalTypeCurrency.add((Button) childView);
            }
        }
    }

    private void setupUIDialogModalBalance(Dialog dialog){
        buttonNumberZero = dialog.findViewById(R.id.buttonZero);
        buttonNumberOne = dialog.findViewById(R.id.buttonOne);
        buttonNumberTwo = dialog.findViewById(R.id.buttonTwo);
        buttonNumberThree = dialog.findViewById(R.id.buttonThree);
        buttonNumberFour = dialog.findViewById(R.id.buttonFour);
        buttonNumberFive = dialog.findViewById(R.id.buttonFive);
        buttonNumberSix = dialog.findViewById(R.id.buttonSix);
        buttonNumberSeven = dialog.findViewById(R.id.buttonSeven);
        buttonNumberEight = dialog.findViewById(R.id.buttonEight);
        buttonNumberNine = dialog.findViewById(R.id.buttonNine);

        buttonDivision = dialog.findViewById(R.id.buttonDivision);
        buttonMultiplication = dialog.findViewById(R.id.buttonMultiplication);
        buttonSubtraction = dialog.findViewById(R.id.buttonSubtraction);
        buttonAddition = dialog.findViewById(R.id.buttonAddition);

        buttonDelete = dialog.findViewById(R.id.buttonDelete);
        buttonSetUp = dialog.findViewById(R.id.buttonSetUp);

        textViewModalBalance = dialog.findViewById(R.id.balance_billings);

        if(!textViewBalanceBillings.getText().toString().isEmpty()){
            textViewModalBalance.setText(textViewBalanceBillings.getText().toString());
        }
    }

    private void sheetFillerButtonListModalNumber(Dialog dialog){
        elementKeyboard = dialog.findViewById(R.id.elementKeyboard);

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

    private final char SHARE = '\u00F7';
    private final char MULTIPLY = '\u00D7';

    private final char SUBTRACT = '-';
    private final char ADD = '+';

    private void handlerButtonDialogModalBalance(){
        handlerButtonNumberModalBalance();
        handlerButtonMathSymbolsModalBalance();
        handlerButtonSpecialModalBalance();
    }

    private void handlerButtonNumberModalBalance(){

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

    private void handlerButtonMathSymbolsModalBalance(){

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

    private void handlerButtonSpecialModalBalance(){
        buttonDelete.setOnClickListener(v-> {
            String data = (String) textViewModalBalance.getText().toString();

            if (!data.isEmpty()) {
                data = data.substring(0, data.length() - 1);
                textViewModalBalance.setText(data);
            }
        });

        buttonSetUp.setOnClickListener(v ->{
            String data = (String) textViewModalBalance.getText().toString();

            if (!data.isEmpty() && checkMathSymbols(data)) {
                buttonSetUp.setImageDrawable(DRAWABLE_CHECK);
                textViewModalBalance.setText(mathCalculation(data));
                ToastManager.showToastOnSuccessfulChanges(getContext());
            } else if (!data.isEmpty() && !checkMathSymbols(data)) {
                textViewBalanceBillings.setText(data);
                dialogModal.cancel();
                ToastManager.showToastOnSuccessfulChanges(getContext());
            }
        });
    }

    private String getTextViewBalance(){
        String data = (String) textViewModalBalance.getText().toString();
        return data;
    }

    private String mathCalculation(String data){
        Context rhino = Context.enter();
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
            ToastManager.showToastOnFailureChanges(getContext());
            return "0";
        } finally {
            Context.exit();
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
}