package com.example.monefy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.monefy.Manager.internet.ManagerNetwork;
import com.example.monefy.authorization.SignInActivity;
import com.example.monefy.authorization.SignUpActivity;
import com.example.monefy.basic.functionality.HomeActivity;
import com.example.monefy.local.database.AppDatabase;
import com.example.monefy.local.database.ManagerLocalDataBase;
import com.example.monefy.local.database.model.User;
import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.message.ToastManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    private TextView tVSignIn;
    private Button btnSingUp;

    public void setupUIElements(){
        this.tVSignIn = (TextView) findViewById(R.id.btnSignInMain);
        this.btnSingUp = (Button) findViewById(R.id.btnSignUpMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIElements();
        autoLogin();
        eventHandler();

    }

    //Обробник подій.
    public void eventHandler(){
        clickTextSignIn();
        clickButtonSingUp();
    }

    //Обробник кліка по кнопці реєстрації
    private void clickButtonSingUp(){
        btnSingUp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });
    }

    //Обробник кліка по тексту увійти.
    private void clickTextSignIn(){
        tVSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        });
    }

    //Автоматична аторизація.
    private void autoLogin(){
        User user = ManagerLocalDataBase.getUserToId(getApplicationContext(),0);

        if(user != null){
            String email = user.getEmail();
            String password = user.getPassword();
            handlerSignIn(email,password);
        }
    }

    //Механіка аторизацї на FireBase.
    private void handlerSignIn(String email, String password) {
        AuthenticationManager.signInWithEmailAndPasswordCallback(
                FirebaseAuth.getInstance(),
                email, password,
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        navigationToHome();
                        ToastManager.showToastOnSuccessful(getApplicationContext(),R.string.toast_success_signIn);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                       ToastManager.showToastOnFailure(getApplicationContext(),R.string.toast_failure_signIn);
                        if(!ManagerNetwork.isConnectedToInternet(getApplicationContext())){
                            ToastManager.showToastOnFailure(getApplicationContext(),R.string.toast_no_internet_connection);
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
}