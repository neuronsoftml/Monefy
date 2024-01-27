package com.example.monefy.Manager.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationManager {
    private static AuthenticationManager authenticationManager;

    public static AuthenticationManager getAuthenticationManager(){
        if(authenticationManager == null){
            authenticationManager = new AuthenticationManager();
        }
        return authenticationManager;
    }

    private static FirebaseUser currentUser;

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
    public static void signInWithEmailAndPasswordCallback(
            FirebaseAuth mAuth,
            String email,
            String password,
            InConclusionCompleteListener listener)
    {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (listener != null) {
                            currentUser = FirebaseAuth.getInstance().getCurrentUser();
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

    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}
