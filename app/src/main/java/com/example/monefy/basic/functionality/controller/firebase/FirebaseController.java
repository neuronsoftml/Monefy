package com.example.monefy.basic.functionality.controller.firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.monefy.basic.functionality.Interface.billings.OnBillingCallback;
import com.example.monefy.basic.functionality.Interface.billings.OnLoadBillingsCallback;
import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.Interface.history.OnLoadHistoryBillingsCallback;
import com.example.monefy.basic.functionality.Interface.incomes.OnIncomesCallback;
import com.example.monefy.basic.functionality.Interface.message.OnMessageCallback;
import com.example.monefy.basic.functionality.Interface.user.OnUserDataCallback;
import com.example.monefy.basic.functionality.model.history.HistoryBilling;
import com.example.monefy.basic.functionality.model.user.User;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.income.Income;
import com.example.monefy.basic.functionality.model.message.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseController {

    private static FirebaseController firebaseController;
    private FirebaseFirestore db;
    private FirebaseAuth  mAuth;
    private String userId;

    public static FirebaseController getFirebaseManager(){
        if(firebaseController == null){
            firebaseController = new FirebaseController();
        }
        return firebaseController;
    }

    private void init(){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        AuthenticationController authenticationController = AuthenticationController.getAuthenticationManager();
        if(authenticationController.getIsUserLogIn()){
            userId = AuthenticationController.getAuthenticationManager().getUserId();
        }
    }

    //Створює нового користувача.
    public void createNewUserWithEmailPassword(Activity activity, String email, String password, final InConclusionCompleteListener listener){
        init();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        if (listener != null) {
                            listener.onSuccess();
                        }
                    } else {
                        if (listener != null) {
                            try {
                                listener.onFailure(task.getException());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
    }

    //Скидує пароль.
    public void resetPasswordWithEmail(String email, final InConclusionCompleteListener listener) {
        init();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (listener != null) {
                            listener.onSuccess();
                        }
                    } else {
                        if (listener != null) {
                            try {
                                listener.onFailure(task.getException());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
    }

    //Дістає данні користувача.
    public void getUserPersonalData(final OnUserDataCallback callback) {
        // Виконуємо запит до Firestore для отримання даних користувача за ідентифікатором userId
        init();
        db.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        callback.onUserDataReceived(user);
                    } else {
                        // Документ не існує, обробка цього випадку
                        callback.onDataNotFound();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Обробка помилки
                        callback.onDataError(e);
                    }
                });
    }

    //Дістає усі рахунки
    public void getBillingsAll(final OnLoadBillingsCallback callback) {
        // Виконуємо запит до Firestore для отримання даних користувача за ідентифікатором userId
        init();
        db.collection("Users")
                .document(userId)
                .collection("billings")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Отримано список документів "billings"
                        List<Billings> billingsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Отримуємо дані з кожного документу і додаємо їх до списку
                            Billings billings = Billings.fromDocumentSnapshot(document);
                            billings.setId(document.getId());
                            billingsList.add(billings);
                        }

                        // Обробка списку рахунків через callback
                        callback.onBillingsDataReceived(billingsList);
                    } else {
                        // Підколекція "billings" порожня
                        callback.onDataNotFound();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Обробка помилки
                        callback.onDataError(e);
                    }
                });
    }

    public void getBillingsByUID(String UIDBillings, final OnBillingCallback onBillingCallback) {
        init();
        db.collection("Users")
                .document(userId)
                .collection("billings")
                .document(UIDBillings)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Отримуємо дані з документа і передаємо їх через callback
                        Billings billings = Billings.fromDocumentSnapshot(documentSnapshot);
                        if (billings != null) {
                            billings.setId(documentSnapshot.getId());
                            onBillingCallback.onBillingsDataReceived(billings);
                        }
                    } else {
                        // Документ з вказаним ID не існує
                        onBillingCallback.onDataNotFound();
                    }
                })
                .addOnFailureListener(e -> {
                    // Обробка помилки
                    Log.e("FirebaseController","getBillingsByUID addOnFailureListener " + e);
                });
    }

    //Добавляє рахунок
    public void addBilling(Map<String,Object> billingData, final  InConclusionCompleteListener listener) {
        init();
        CollectionReference billingsCollection = db
                .collection("Users")
                .document(userId)
                .collection("billings");

        billingsCollection.add(billingData)
                .addOnSuccessListener(documentReference -> {
                    // Успішно додано новий рахунок
                    if(listener != null){
                        listener.onSuccess();
                    }

                })
                .addOnFailureListener(e -> {
                    // Помилка додавання рахунку
                    if(listener != null){
                        try {
                            listener.onFailure(e);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }

    //Видаляє разунок по ID.
    public void deleteBillings(String billingsId, final InConclusionCompleteListener listener) {
        init();
        DocumentReference billingsRef = db.collection("Users")
                .document(userId)
                .collection("billings")
                .document(billingsId);

        billingsRef.delete()
                .addOnSuccessListener(aVoid -> {
                    // Успішно видалено
                    if (listener != null) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        try {
                            listener.onFailure(e);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }

    //Обновляє дані рахунка, по ID.
    public void updatedBillings(String billId, Map<String, Object> updatedBill, final InConclusionCompleteListener listener) {
        init();
        db.collection("Users")
                .document(userId)
                .collection("billings")
                .document(billId)
                .update(updatedBill)
                .addOnSuccessListener(aVoid -> {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        try {
                            listener.onFailure(e);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }

    //Добавляє актив.
    public void addIncome(Map<String,Object> incomeData, final  InConclusionCompleteListener listener) {
        init();
        CollectionReference incomeCollection = db
                .collection("Users")
                .document(userId)
                .collection("Income");

        incomeCollection.add(incomeData)
                .addOnSuccessListener(documentReference -> {
                    // Успішно додано новий рахунок
                    if(listener != null){
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    // Помилка додавання рахунку
                    if(listener != null){
                        try {
                            listener.onFailure(e);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }

    //Дістає весь список активів
    public void getIncomesData(final OnIncomesCallback incomesCallback) {
        init();
        db.collection("Users")
                .document(userId)
                .collection("Income")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Отримано список документів "income"
                        List<Income> incomesList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Отримуємо дані з кожного документу і додаємо їх до списку
                            Income incomes = document.toObject(Income.class);
                            incomes.setId(document.getId());
                            incomesList.add(incomes);
                        }

                        // Обробка списку активів через callback
                        incomesCallback.onIncomesDataReceived(incomesList);
                    } else {
                        // Підколекція "incomes" порожня
                        incomesCallback.onDataNotFound();
                    }
                })
                .addOnFailureListener(incomesCallback::onDataError);
    }


    public void getMessageData(final OnMessageCallback onMessageCallback){
        init();
        db.collection("Users")
                .document(userId)
                .collection("message")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()){
                        List<Message> messageList = new ArrayList<>();
                        for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                            Message message = document.toObject(Message.class);
                            message.setId(document.getId());
                            messageList.add(message);
                        }
                        onMessageCallback.onMessageDataReceived(messageList);
                    }else{
                        onMessageCallback.onDataNotFound();
                    }
                });
    }

    public void addHistoryBilling(Map<String, Object> historyBillingData, final InConclusionCompleteListener listener){
        init();
        CollectionReference historyBilling = db
                .collection("Users")
                .document(userId)
                .collection("historyBilling");

        historyBilling.add(historyBillingData)
                .addOnSuccessListener(documentReference -> {
                   if(listener != null){
                       listener.onSuccess();
                   }
                })
                .addOnFailureListener(e->{
                   if(listener != null){
                       try {
                           listener.onFailure(e);
                       }catch (Exception ex){
                           throw new RuntimeException(ex);
                       }
                   }
                });
    }

    public void getHistoryBillingsByUID(String UIDBilling, final OnLoadHistoryBillingsCallback historyBillingsCallback){
        init();
        db.collection("Users")
                .document(userId)
                .collection("historyBilling")
                .whereEqualTo("UIDBilling", UIDBilling)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<HistoryBilling> historyBillingsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            HistoryBilling historyBilling = document.toObject(HistoryBilling.class);
                            historyBilling.setId(document.getId());
                            historyBillingsList.add(historyBilling);
                        }
                        historyBillingsCallback.onHistoryBillingsReceived(historyBillingsList);
                    } else {
                        historyBillingsCallback.onDataNotFound();
                    }
                });
    }
}
