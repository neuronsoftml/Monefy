package com.example.monefy.basic.functionality.controller.firebase;

import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationController {
    private static AuthenticationController authenticationController;
    private boolean isUserLogIn = false;
    public static AuthenticationController getAuthenticationManager(){
        if(authenticationController == null){
            authenticationController = new AuthenticationController();
        }
        return authenticationController;
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
