package com.example.monefy.tools.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.tools.message.ToastManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {

    //Створює нового користувача.
    public static void createNewUserWithEmailPassword(
            FirebaseAuth mAuth,
            Activity activity,
            String email, String password,
            final InConclusionCompleteListener listener
    ){
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
    public static void resetPasswordWithEmail(
            FirebaseAuth mAuth,
            String email,
            final InConclusionCompleteListener listener)
    {
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
    public static void getUserPersonalData(
            FirebaseFirestore db,
            String userId,
            final OnUserDataCallback callback) {
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
    public static void getBillingsData(
            FirebaseFirestore db,
            String userId,
            final OnBillingsCallback callback) {
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
    public static void addBilling(
            FirebaseFirestore db,
            String userId,
            long balance,
            long creditLimit,
            String name,
            String typeBillings,
            String typeCurrency ,
            final  InConclusionCompleteListener listener) {
        CollectionReference billingsCollection = db
                .collection("Users")
                .document(userId)
                .collection("billings");

        Map<String, Object> billingData = new HashMap<>();
        billingData.put("balance", balance);
        billingData.put("creditLimit", creditLimit);
        billingData.put("name", name);
        billingData.put("typeBillings", typeBillings);
        billingData.put("typeCurrency", typeCurrency);

        billingsCollection.add(billingData)
                .addOnSuccessListener(documentReference -> {
                    // Успішно додано новий рахунок
                    if(listener != null){
                        listener.onSuccess();
                    }
                    String billingId = documentReference.getId();
                })
                .addOnFailureListener(e -> {
                    // Помилка додавання рахунку
                    if(listener != null){
                        listener.onFailure(e);
                    }
                });
    }

    //Видаляє разунок по ID.
    public static void deleteBillings(String userId, String billingsId, final InConclusionCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference billingsRef = db.collection("Users")
                .document(userId)
                .collection("billings")
                .document(billingsId);

        billingsRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Успішно видалено
                        if (listener != null) {
                            listener.onSuccess();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (listener != null) {
                            listener.onFailure(e);
                        }
                    }
                });
    }
}
