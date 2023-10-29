package com.example.monefy.tools.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.monefy.basic.functionality.model.Billings;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    //Створює нового користувача.
    public static void createNewUserWithEmailPassword(
            FirebaseAuth mAuth,
            Activity activity,
            String email, String password,
            InConclusionCompleteListener listener
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
            InConclusionCompleteListener listener)
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



    public interface InConclusionCompleteListener {
        void onSuccess();
        void onFailure(Exception exception);
    }



    public static void getUserPersonalData(FirebaseFirestore db, String userId, final OnUserDataCallback callback) {
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

    public static void getBillingsData(FirebaseFirestore db, String userId, final OnBillingsCallback callback) {
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

    public interface OnUserDataCallback {
        void onUserDataReceived(DocumentSnapshot documentSnapshot);

        void onUserDataNotFound();

        void onUserDataError(Exception e);

    }
    public interface OnBillingsCallback{
        void onBillingsDataReceived(List<Billings> billingsList);

        void onBillingsDataNotFound();

        void onBillingsDataError(Exception e);
    }
}
