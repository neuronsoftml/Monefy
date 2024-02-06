package com.example.monefy.basic.functionality.controller.billings;

import android.util.Log;

import com.example.monefy.basic.functionality.controller.date.DateController;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;
import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTransferFragment;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.HistoryBilling;

import java.lang.reflect.InvocationTargetException;

public class TransactionController {
    private final FirebaseController firebaseController = FirebaseController.getFirebaseManager();

    private ModalTransferFragment modalTransferFragment;

    /** Цей метод проводить логіку переводу коштів з рахунку на рахунок.
     * @param theBillFromWhichWeDebit - Рахунок з якого списуєм.
     * @param theBillToWhichWeTransfer - Рахунок на який переводим.
     * @param writeOffAmount - сума яку списуєм.
     * @param setUpAmount - сума яку переводим.
     * @param modalTransferFragment - силка для звязку з модальним вікном.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public void transferBetweenBillings(
            Billings theBillFromWhichWeDebit,
            Billings theBillToWhichWeTransfer,
            double writeOffAmount,
            double setUpAmount,
            ModalTransferFragment modalTransferFragment
    ) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        this.modalTransferFragment = modalTransferFragment;

        // Вносимо зміни в рахунки
        theBillFromWhichWeDebit.setBalance(
                theBillFromWhichWeDebit.getBalance() - writeOffAmount
        );

        theBillToWhichWeTransfer.setBalance(
                theBillToWhichWeTransfer.getBalance() + setUpAmount
        );

        // Оновлюємо рахунки в Firebase
        updateTheBillFromWhichWeDebit(theBillFromWhichWeDebit);
        updateTheBillToWhichWeTransfer(theBillToWhichWeTransfer);

        // Створюємо запис історії рахунків
        createHistoryTheBillFromWhichWeDebit(theBillFromWhichWeDebit, theBillToWhichWeTransfer,writeOffAmount);
        createHistoryTheBillToWhichWeTransfer(theBillToWhichWeTransfer, theBillFromWhichWeDebit, setUpAmount);

    }

    /** Цей метод здійсннює оновлення данних рахунку з якого списали.
     * @param theBillFromWhichWeDebit - Рахунок з якого списуєм.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void updateTheBillFromWhichWeDebit(Billings theBillFromWhichWeDebit) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        firebaseController.updatedBillings(
                theBillFromWhichWeDebit.getId(),
                BillingsController.getBillingMapData(theBillFromWhichWeDebit),
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        modalTransferFragment.isEditBillFromWhichWeDebit = true;
                        modalTransferFragment.checkSuccessfulTransaction();
                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                }
        );
    }

    /** Цей метод здійсннює оновлення данних рахунку до якого переводим кошти.
     * @param theBillToWhichWeTransfer Рахунок до якого переводим.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void updateTheBillToWhichWeTransfer(Billings theBillToWhichWeTransfer) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        firebaseController.updatedBillings(
                theBillToWhichWeTransfer.getId(),
                BillingsController.getBillingMapData(theBillToWhichWeTransfer),
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        modalTransferFragment.isEditBillToWhichWeTransfer = true;
                        modalTransferFragment.checkSuccessfulTransaction();
                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                });
    }

    /** Цей метод створює запис в історію рахуку, що з нього списали кошки.
     * @param theBillFromWhichWeDebit - Рахунок з якого списуєм.
     * @param theBillToWhichWeTransfer - Рахунок на який переводим.
     * @param writeOffAmount - сума яку списуєм.
     */
    private void createHistoryTheBillFromWhichWeDebit(Billings theBillFromWhichWeDebit, Billings theBillToWhichWeTransfer, double writeOffAmount){
        HistoryBilling historyBilling = new HistoryBilling(
                theBillFromWhichWeDebit.getId(),
                theBillToWhichWeTransfer.getId(),
                DateController.getCurrentDateFormatFirebase(),
                theBillFromWhichWeDebit.getTypeCurrency(),
                "-",
                writeOffAmount
        );
        firebaseController.addHistoryBilling(historyBilling.getCreateMap(), new InConclusionCompleteListener() {
            @Override
            public void onSuccess() {
                Log.d("Запис історії рахунку", "успішно створений");
            }

            @Override
            public void onFailure(Exception exception) throws Exception {
                Log.d("Запис історії рахунку", "не вдалося створити");
            }
        });
    }

    /** Цей метод здійсннює запис в історію рахуку, що до нього переводили кошти.
     * @param theBillToWhichWeTransfer - Рахунок на який переводим.
     * @param theBillFromWhichWeDebit - Рахунок з якого списуєм.
     * @param setUpAmount - сума яку перевели.
     */
    private void createHistoryTheBillToWhichWeTransfer(Billings theBillToWhichWeTransfer, Billings theBillFromWhichWeDebit, double setUpAmount){
        HistoryBilling historyBilling = new HistoryBilling(
                theBillToWhichWeTransfer.getId(),
                theBillFromWhichWeDebit.getId(),
                DateController.getCurrentDateFormatFirebase(),
                theBillToWhichWeTransfer.getTypeCurrency(),
                "+",
                setUpAmount
        );

        firebaseController.addHistoryBilling(historyBilling.getCreateMap(), new InConclusionCompleteListener() {
            @Override
            public void onSuccess() {
                Log.d("Запис історії рахунку", "успішно створений");
            }

            @Override
            public void onFailure(Exception exception) throws Exception {
                Log.d("Запис історії рахунку", "не вдалося створити");
            }
        });
    }
}
