package com.example.monefy.basic.functionality.controller.history;

import android.util.Log;

import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.Interface.history.OnHistoryBillingsCallback;
import com.example.monefy.basic.functionality.Interface.history.OnLoadHistoryBillingsCallback;
import com.example.monefy.basic.functionality.controller.date.DateController;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.history.HistoryBilling;

import java.util.List;

public class HistoryBillingsController {
    private static final FirebaseController firebaseController = FirebaseController.getFirebaseManager();

    /**
     * Дійстає рахунок за пареметром.
     * @param historyBillingsByUID - UID рахунка
     * @param onHistoryBillingsCallback - Через інрфейс повертаємо лист рахунків
     */
    public static void loadHistoryBillingsExceptOne(String historyBillingsByUID, OnHistoryBillingsCallback onHistoryBillingsCallback){
        Log.w("historyBillingsByUID", historyBillingsByUID);
        firebaseController.getHistoryBillingsByUID(historyBillingsByUID, new OnLoadHistoryBillingsCallback() {
            @Override
            public void onHistoryBillingsReceived(List<HistoryBilling> historyBillingsList) {
                onHistoryBillingsCallback.onHistoryBillingsReceived(historyBillingsList);
            }

            @Override
            public void onDataNotFound() {
                onHistoryBillingsCallback.onDataNotFound();
            }

            @Override
            public void onDataError(Exception e) {
                Log.e("loadHistoryBillings", String.valueOf(e));
            }
        });
    }


    /** Цей метод здійсннює запис в історію рахуку.
     * @param theBillToWhichWeTransfer - Рахунок на який переводим.
     * @param theBillFromWhichWeDebit - Рахунок з якого списуєм.
     * @param suma - сума яку перевели.
     */
    public static void createHistoryTheBilling(Billings theBillToWhichWeTransfer, Billings theBillFromWhichWeDebit, String typeTransfer, double suma){
        HistoryBilling historyBilling = new HistoryBilling(
                theBillToWhichWeTransfer.getId(),
                theBillFromWhichWeDebit.getId(),
                DateController.getCurrentDateFormatFirebase(),
                theBillToWhichWeTransfer.getTypeCurrency(),
                typeTransfer,
                suma
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
