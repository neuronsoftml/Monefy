package com.example.monefy.Manager.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.monefy.Manager.billings.OnBillingsCallback;
import com.example.monefy.Manager.incomes.OnIncomesCallback;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.income.Income;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseManager {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FirebaseAuth  mAuth = FirebaseAuth.getInstance();
    private static final String userId = AuthenticationManager.getAuthenticationManager().getUserId();

    //Створює нового користувача.
    public static void createNewUserWithEmailPassword(Activity activity, String email, String password, final InConclusionCompleteListener listener){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        if (listener != null) {
                            listener.onSuccess();
                        }
                    } else {
                        if (listener != null) {
                            listener.onFailure(task.getException());
                        }
                    }
                });
    }

    //Скидує пароль.
    public static void resetPasswordWithEmail(String email, final InConclusionCompleteListener listener) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (listener != null) {
                            listener.onSuccess();
                        }
                    } else {
                        if (listener != null) {
                            listener.onFailure(task.getException());
                        }
                    }
                });
    }

    //Дістає данні користувача.
    public static void getUserPersonalData(final OnUserDataCallback callback) {
        // Виконуємо запит до Firestore для отримання даних користувача за ідентифікатором userId
        db.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Обробка отриманих даних та передача їх через callback
                            callback.onUserDataReceived(documentSnapshot);
                        } else {
                            // Документ не існує, обробка цього випадку
                            callback.onUserDataNotFound();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Обробка помилки
                        callback.onUserDataError(e);
                    }
                });
    }

    //Дістає усі рахунки
    public static void getBillingsData(final OnBillingsCallback callback) {
        // Виконуємо запит до Firestore для отримання даних користувача за ідентифікатором userId
        db.collection("Users")
                .document(userId)
                .collection("billings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Отримано список документів "billings"
                            List<Billings> billingsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                // Отримуємо дані з кожного документу і додаємо їх до списку
                                Billings billings = document.toObject(Billings.class);
                                billings.setId(document.getId());
                                billingsList.add(billings);
                            }

                            // Обробка списку рахунків через callback
                            callback.onBillingsDataReceived(billingsList);
                        } else {
                            // Підколекція "billings" порожня
                            callback.onBillingsDataNotFound();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Обробка помилки
                        callback.onBillingsDataError(e);
                    }
                });
    }

    //Добавляє рахунок
    public static void addBilling(Map<String,Object> billingData, final  InConclusionCompleteListener listener) {
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
                        listener.onFailure(e);
                    }
                });
    }

    //Видаляє разунок по ID.
    public static void deleteBillings(String billingsId, final InConclusionCompleteListener listener) {

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
                        listener.onFailure(e);
                    }
                });
    }

    //Обновляє дані рахунка, по ID.
    public static void updatedBillings(String billId, Map<String, Object> updatedBill, final InConclusionCompleteListener listener) {
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
                        listener.onFailure(e);
                    }
                });
    }

    //Добавляє актив.
    public static void addIncome(Map<String,Object> incomeData, final  InConclusionCompleteListener listener) {
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
                        listener.onFailure(e);
                    }
                });
    }

    //Дістає весь список активів
    public static void getIncomesData(final OnIncomesCallback incomesCallback) {
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
                        incomesCallback.onBillingsDataReceived(incomesList);
                    } else {
                        // Підколекція "incomes" порожня
                        incomesCallback.onBillingsDataNotFound();
                    }
                })
                .addOnFailureListener(incomesCallback::onBillingsDataError);
    }
}
