package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.monefy.Manager.billings.BillingsManager;
import com.example.monefy.Manager.date.ManagerDate;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.message.ToastManager;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.controller.TransactionController;
import com.example.monefy.basic.functionality.fragment.bank.MonoBank.MonoBankManager;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.HistoryBilling;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;

import java.lang.reflect.InvocationTargetException;

public class ModalTransferFragment extends DialogFragment {
    private Billings theBillToWhichWeTransfer;
    private Billings theBillFromWhichWeDebit;
    private FragmentContainerView fragContainerViewCalculator;
    private TextView nameBillingFromWhichDebit, nameBillingToWhichWeTransfer;
    private TextView balanceSpearFromWhichDebit, valueTypeBillingsFromWhichWeDebit, typeCurrencySpearFromWhichWeDebit;
    private TextView balanceEnrollToWhichWeTransfer,valueTypeBillingsEnrollToWhichWeTransfer, typeCurrencyEnrollToWhichWeTransfer;
    private TextView valueSpearFromWhichWeDebit,valueEnrollToWhichWeTransfer;
    private ImageView imageCartFromBillWhichWeDebit;
    private ImageView imageCartBillEnrollToWhichWeTransfer;
    private ImageButton btnBillSwitcher;
    private Button btnApprove;
    private double writeOffAmount = 0;
    private double setUpAmount = 0;
    private final int contentView = R.layout.modal_bottom_replenishment;

    /**
     *
     * @param theBillToWhichWeTransfer Рахунок на який переказуємо.
     * @param theBillFromWhichWeDebit Рахунок з якого списуєм.
     */
    public ModalTransferFragment(Billings theBillToWhichWeTransfer, Billings theBillFromWhichWeDebit) {
        this.theBillToWhichWeTransfer = theBillToWhichWeTransfer;
        this.theBillFromWhichWeDebit = theBillFromWhichWeDebit;
    }

    /**
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return
     */
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
        handlerButton();

        return dialog;
    }

    private DialogCallback dialogCallback;

    /** Вхідна точна.
     * @param dialogCallback
     */
    public void startDialogModal(DialogCallback dialogCallback){
        this.dialogCallback = dialogCallback;
    }

    /** Цей метод здійснює ініцілізацію обєктів які знаходяться в layout Fragments
     *
     * @param dialog - приймає об`єкт Dialog створеного dialogModal.
     */
    private void setupUIDialogModal(Dialog dialog){
        fragContainerViewCalculator = dialog.findViewById(R.id.fragContainerViewCalculator);

        nameBillingToWhichWeTransfer = dialog.findViewById(R.id.txtNameBillingToWhichWeTransfer);
        balanceEnrollToWhichWeTransfer = dialog.findViewById(R.id.txtBalanceEnrollToWhichWeTransfer);
        typeCurrencyEnrollToWhichWeTransfer = dialog.findViewById(R.id.txtTypeCurrencyEnrollToWhichWeTransfer);
        imageCartBillEnrollToWhichWeTransfer = dialog.findViewById(R.id.imageCartBillToWhichWeTransfer);
        valueTypeBillingsEnrollToWhichWeTransfer = dialog.findViewById(R.id.textValueTypeBillingsToWhichWeTransfer);
        valueEnrollToWhichWeTransfer = dialog.findViewById(R.id.textValueEnrollToWhichWeTransfer);

        nameBillingFromWhichDebit = dialog.findViewById(R.id.txtNameBillingFromWhichWeDebit);
        balanceSpearFromWhichDebit = dialog.findViewById(R.id.txtBalanceSpearFromWhichWeDebit);
        typeCurrencySpearFromWhichWeDebit = dialog.findViewById(R.id.txtTypeCurrencySpearFromWhichWeDebit);
        valueTypeBillingsFromWhichWeDebit = dialog.findViewById(R.id.textValueTypeBillingsFromWhichWeDebit);
        valueSpearFromWhichWeDebit = dialog.findViewById(R.id.textValueSpearFromWhichWeDebit);
        imageCartFromBillWhichWeDebit = dialog.findViewById(R.id.imageCartFromBillWhichWeDebit);

        btnBillSwitcher = dialog.findViewById(R.id.btnBillSwitcher);
        btnApprove = dialog.findViewById(R.id.btnApprove);
    }

    /**
     * Цей метод записує значення у UI елементи з переданих обєктів.
     */
    private void setValueObjectModal(){
        //Для елементів що відповідає з рахунку
        nameBillingFromWhichDebit.setText(theBillFromWhichWeDebit.getName());
        balanceSpearFromWhichDebit.setText(String.valueOf(theBillFromWhichWeDebit.getBalance()));
        typeCurrencySpearFromWhichWeDebit.setText(theBillFromWhichWeDebit.getTypeCurrency());
        valueTypeBillingsFromWhichWeDebit.setText(theBillFromWhichWeDebit.getTypeBillings());
        valueSpearFromWhichWeDebit.setText(String.valueOf(writeOffAmount));
        imageCartFromBillWhichWeDebit.setImageResource(
                TypeBillings.getIdImageTypeBillings(theBillFromWhichWeDebit.getTypeBillings())
        );

        //Для елементів що відповідає на рахунок
        nameBillingToWhichWeTransfer.setText(theBillToWhichWeTransfer.getName());
        balanceEnrollToWhichWeTransfer.setText(String.valueOf(theBillToWhichWeTransfer.getBalance()));
        typeCurrencyEnrollToWhichWeTransfer.setText(theBillToWhichWeTransfer.getTypeCurrency());
        valueTypeBillingsEnrollToWhichWeTransfer.setText(theBillToWhichWeTransfer.getTypeBillings());
        valueEnrollToWhichWeTransfer.setText(String.valueOf(setUpAmount));
        imageCartBillEnrollToWhichWeTransfer.setImageResource(
                TypeBillings.getIdImageTypeBillings(theBillToWhichWeTransfer.getTypeBillings())
        );
        btnApprove.setVisibility(View.GONE);
    }

    /** Цей метод відображає Fragment калькулятора.*/
    private CalculatorFragment calculatorFragment;
    private void showCalculator(){
        calculatorFragment = new CalculatorFragment("0", theBillFromWhichWeDebit.getTypeCurrency());

        if (getChildFragmentManager().findFragmentById(fragContainerViewCalculator.getId()) == null) {
            getChildFragmentManager().beginTransaction()
                    .add(fragContainerViewCalculator.getId(),calculatorFragment)
                    .commit();

        }

        calculatorFragment.checkDialogCallback(new DialogCallback() {
            @Override
            public void onSuccess(String data) {
                calculationTransferredSumBill(data);
                updateValueUI();
                btnApprove.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

    /**
     * Цей метод містить оброник подій натиску усіх кнопок.
     */
    private void handlerButton(){
        handlerBtnBillSwitcher();
        handlerBtnApprove();
    }

    /**
     * Цей метод змінює місцями рахунки а саме.
     * theBillToWhichWeTransfer <--> theBillFromWhichWeDebit.
     * проводить повторний розрахунок суми, та обновляє дані інтерфейсу.
     */
    private void handlerBtnBillSwitcher(){
        btnBillSwitcher.setOnClickListener(v->{
            Billings oldTheBillToWhichWeTransfer = theBillToWhichWeTransfer;
            Billings oldTheBillFromWhichWeDebit = theBillFromWhichWeDebit;

            theBillToWhichWeTransfer = oldTheBillFromWhichWeDebit;
            theBillFromWhichWeDebit = oldTheBillToWhichWeTransfer;

            calculationTransferredSumBill(String.valueOf(writeOffAmount));
            updateValueUI();
            setValueObjectModal();

        });
    }

    /** Цей метод проводить розрахунок веденої суми переводу враховуючи тип валюти та конвертує.
     *
     * @param data
     */
    private void calculationTransferredSumBill(String data){
        //Як що тип валюти одинаковий здійснюємо операцію без врахування курса валют.
        if(theBillToWhichWeTransfer.getTypeCurrency().equals(theBillFromWhichWeDebit.getTypeCurrency())){
            writeOffAmount = Long.parseLong(String.valueOf(data));
            setUpAmount = Long.parseLong(String.valueOf(data));
        }
        // Як що типи валют різні робемо конвертацію валюти за курсом.
        else {
            MonoBankManager monoBankManager = MonoBankManager.getMonoBankManager();
            double sum = (double) monoBankManager.currencyConversionAtTheExchangeRate(
                    Double.parseDouble(data),
                    theBillFromWhichWeDebit.getTypeCurrency()
            );
            writeOffAmount = Long.parseLong(data);
            setUpAmount = sum;

        }
    }

    /** Обновляє значення у UI елементів.
     *  Після розрахунку вибраної суми.
     */
    private void updateValueUI(){
        balanceSpearFromWhichDebit.setText(
                String.valueOf(theBillFromWhichWeDebit.getBalance() - writeOffAmount));

        balanceEnrollToWhichWeTransfer.setText(
                String.valueOf(theBillToWhichWeTransfer.getBalance() + setUpAmount)
        );

        valueSpearFromWhichWeDebit.setText(String.valueOf(writeOffAmount));
        valueEnrollToWhichWeTransfer.setText(String.valueOf(setUpAmount));

        calculatorFragment.setValueTextViewTypeMoney(theBillFromWhichWeDebit.getTypeCurrency());
    }

    /** Цей метод обробляє подію натискання на кнопку btnApprove яка відповідає за затвердження транзакції між рахунками.
     *
     */
    private void handlerBtnApprove(){
        btnApprove.setOnClickListener(v->{
            if(checkTransactionValidation()){
                try {
                    TransactionController transactionController = new TransactionController();
                    transactionController.transferBetweenBillings(
                            theBillFromWhichWeDebit,
                            theBillToWhichWeTransfer,
                            writeOffAmount,
                            setUpAmount,
                            this
                    );
                } catch (InvocationTargetException | IllegalAccessException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /** Здійснює перевірку, здійснення транзакції.
     * boolean oneCheck - сума списання не повинна перевищувати кредитного ліміту.
     * boolean twoCheck - сума залишку не має бути меньшою нуля.
     * @return
     */
    private boolean checkTransactionValidation(){

        boolean oneCheck = writeOffAmount > theBillFromWhichWeDebit.getCreditLimit();
        boolean twoCheck = (theBillFromWhichWeDebit.getBalance() - writeOffAmount) < 0;

        if(oneCheck){
            ToastManager.showToastOnFailure(getContext(),R.string.textExceededCreditLimit);
            return false;
        } else if (twoCheck) {
            ToastManager.showToastOnFailure(getContext(),R.string.textExceededTheAvailableAmount);
            return false;
        }
        return true;
    }


    /** Метод перевіряє як що в усіх двух рахунках спішно внесено зміни.*/
    public boolean isEditBillFromWhichWeDebit;
    public boolean isEditBillToWhichWeTransfer;
    public void checkSuccessfulTransaction(){
        if(isEditBillFromWhichWeDebit && isEditBillToWhichWeTransfer){
            ToastManager.showToastOnSuccessful(getContext(), R.string.textTransactionIsSuccessful);
            if(dialogCallback != null){
                dialogCallback.onSuccess(null);
            }
        }
    }
}
