package com.example.monefy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.monefy.basic.functionality.controller.network.NetworkController;
import com.example.monefy.basic.functionality.HomeActivity;
import com.example.monefy.local.database.ManagerLocalDataBase;
import com.example.monefy.local.database.model.User;
import com.example.monefy.basic.functionality.controller.firebase.AuthenticationController;
import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.controller.message.ToastController;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoLogin();
    }

    //Автоматична аторизація.
    private void autoLogin(){
        User user = ManagerLocalDataBase.getUserToId(getApplicationContext(),0);

        if(user != null){
            String email = user.getEmail();
            String password = user.getPassword();
            handlerSignIn(email,password);
        }else {
            navigationLobby();
        }
    }

    //Механіка аторизації на FireBase.
    private void handlerSignIn(String email, String password) {
        AuthenticationController authenticationController = AuthenticationController.getAuthenticationManager();
        authenticationController.signInWithEmailAndPasswordCallback(
                FirebaseAuth.getInstance(),
                email, password,
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        navigationToHome();
                        ToastController.showToastOnSuccessful(getApplicationContext(),R.string.textSuccessSignIn);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                       ToastController.showToastOnFailure(getApplicationContext(),R.string.textFailureSignIn);
                        if(!NetworkController.isConnectedToInternet(getApplicationContext())){
                            ToastController.showToastOnFailure(getApplicationContext(),R.string.textNoInternetConnection);
                        }
                    }
                }
        );
    }

    //Навігація переходу на сторінку Home.
    private void navigationToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    //Навігація переходу на сторінку Loobby.
    private void navigationLobby(){
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
        finish();
    }
}