package com.example.monefy.Manager.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationManager {
    private static AuthenticationManager authenticationManager;
    private boolean isUserLogIn = false;
    public static AuthenticationManager getAuthenticationManager(){
        if(authenticationManager == null){
            authenticationManager = new AuthenticationManager();
        }
        return authenticationManager;
    }

    private FirebaseUser currentUser;

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String getUserId(){
        return currentUser.getUid();
    }

    public String getEmail(){
        return currentUser.getEmail();
    }

    //Здійснює авторизацію.
    public void signInWithEmailAndPasswordCallback(
            FirebaseAuth mAuth,
            String email,
            String password,
            InConclusionCompleteListener listener)
    {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser = mAuth.getCurrentUser(); // Отримання поточного користувача після успішного входу
                        isUserLogIn = true;
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

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public boolean getIsUserLogIn() {
        return isUserLogIn;
    }
}
