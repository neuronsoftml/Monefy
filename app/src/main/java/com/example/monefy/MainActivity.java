package com.example.monefy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monefy.authorization.SignInActivity;
import com.example.monefy.authorization.SignUpActivity;
import com.example.monefy.basic.functionality.HomeActivity;
import com.example.monefy.local.database.AppDatabase;
import com.example.monefy.local.database.model.User;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    private TextView signIn;
    private Button singUpEmailPassword;

    public void setupUIElements(){
        this.signIn = (TextView) findViewById(R.id.SignIn);
        this.singUpEmailPassword = (Button) findViewById(R.id.signUpEmailPassword);
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
        singUpEmailPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    //Обробник кліка по тексту увійти.
    private void clickTextSignIn(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    //Автоматична аторизація.
    private void autoLogin(){
        AppDatabase databaseManager = AppDatabase.getObInstance(getApplicationContext());
        int userCount = databaseManager.userDao().getUserCountById(0);

        if(userCount > 0){
            User user = databaseManager.userDao().getUserById(0);

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
                new AuthenticationManager.InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        navigationToHome();
                        Toast.makeText(MainActivity.this, "Авторизація успішна", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(MainActivity.this, "Помилка авторизації: " + exception.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    //Навігація переходу на сторінку Home.
    private void navigationToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}