package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.Manager.message.ToastManager;
import com.example.monefy.R;

import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;
import java.util.List;

public class CalculatorFragment extends Fragment {

    public CalculatorFragment(String balance, String typeCurrencies) {
        this.balance = balance;
        this.typeCurrencies = typeCurrencies;
    }

    private TextView textViewModalBalance, textViewTypeMoney;
    private String balance, typeCurrencies;
    private final List<Button> buttonListModalNumber =  new ArrayList<>();
    private Button buttonNumberZero, buttonNumberOne, buttonNumberTwo, buttonNumberThree,
            buttonNumberFour, buttonNumberFive, buttonNumberSix,
            buttonNumberSeven, buttonNumberEight, buttonNumberNine;

    private ImageButton buttonDivision, buttonMultiplication, buttonSubtraction,
            buttonAddition, buttonSetUp, buttonDelete;

    private final char SHARE = '\u00F7';
    private final char MULTIPLY = '\u00D7';
    private final char SUBTRACT = '-';
    private final char ADD = '+';
    private Drawable DRAWABLE_CHECK;
    private Drawable DRAWABLE_HANDLE;

    private DialogCallback dialogCallback;

    public void checkDialogCallback(DialogCallback dialogCallback){
        this.dialogCallback = dialogCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_calculator, container, false);
       getAppDrawable(view);
       setupUIDialogModal(view);
       sheetFillerButtonListModalNumber(view);
       setValueObject();
       handlerButton();
       return view;
    }

    private void setValueObject() {
        if(!balance.equals("0")){
            textViewModalBalance.setText(balance);
        }else{
            textViewModalBalance.setText("0");
        }

        textViewTypeMoney.setText(typeCurrencies);
    }

    public void setupUIDialogModal(View view){
        buttonNumberZero = view.findViewById(R.id.buttonZero);
        buttonNumberOne = view.findViewById(R.id.buttonOne);
        buttonNumberTwo = view.findViewById(R.id.buttonTwo);
        buttonNumberThree = view.findViewById(R.id.buttonThree);
        buttonNumberFour = view.findViewById(R.id.buttonFour);
        buttonNumberFive = view.findViewById(R.id.buttonFive);
        buttonNumberSix = view.findViewById(R.id.buttonSix);
        buttonNumberSeven = view.findViewById(R.id.buttonSeven);
        buttonNumberEight = view.findViewById(R.id.buttonEight);
        buttonNumberNine = view.findViewById(R.id.buttonNine);
        buttonDivision = view.findViewById(R.id.buttonDivision);
        buttonMultiplication = view.findViewById(R.id.buttonMultiplication);
        buttonSubtraction = view.findViewById(R.id.buttonSubtraction);
        buttonAddition = view.findViewById(R.id.buttonAddition);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonSetUp = view.findViewById(R.id.buttonSetUp);
        textViewModalBalance = view.findViewById(R.id.balance);
        textViewTypeMoney = view.findViewById(R.id.typeCurrency);
    }

    private void sheetFillerButtonListModalNumber(View view){
        LinearLayout elementKeyboard = view.findViewById(R.id.elementKeyboard);

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

    private void handlerButton(){
        handlerButtonNumber();
        handlerButtonMathSymbols();
        handlerButtonSpecial();
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
            ToastManager.showToastOnFailure(getContext(), R.string.textFailureEnteredTheData);
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

    private void handlerButtonNumber(){
        for(Button button : buttonListModalNumber){
            button.setOnClickListener(v->{
                String data = textViewModalBalance.getText().toString();
                if(!data.isEmpty() && !checkMathSymbols(data) && Long.parseLong(data) == 0){
                    textViewModalBalance.setText(button.getText().toString());
                    buttonSetUp.setImageDrawable(DRAWABLE_CHECK);
                }else{
                    textViewModalBalance.setText(data + button.getText().toString());
                }
            });
        }
    }

    private void handlerButtonMathSymbols(){

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

    private void handlerButtonSpecial(){
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
                ToastManager.showToastOnFailure(getContext(),R.string.textSuccessfulEnteredTheData);
                Log.e("Calculator","Розразунок");
            } else if (!balance.isEmpty() && !checkMathSymbols(balance)) {
                if(dialogCallback != null){
                    dialogCallback.onSuccess(getTextViewBalance());
                }
                Log.e("Calculator","Вносимо зміни" + balance);
                ToastManager.showToastOnFailure(getContext(),R.string.textSuccessfulEnteredTheData);
            }
        });
    }

    private String getTextViewBalance(){
        return (String) textViewModalBalance.getText().toString();
    }

    private void getAppDrawable(View view){
        this.DRAWABLE_CHECK = view.getResources().getDrawable(R.drawable.icon_baseline_check_34);
        this.DRAWABLE_HANDLE = view.getResources().getDrawable(R.drawable.icon_baseline_drag_handle_34);
    }


}