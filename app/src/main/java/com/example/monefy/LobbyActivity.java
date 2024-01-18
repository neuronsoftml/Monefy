package com.example.monefy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.monefy.authorization.SignInActivity;
import com.example.monefy.authorization.SignUpActivity;

public class LobbyActivity extends AppCompatActivity {

    private Button btnSingUp, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setupUIElements();
        eventHandler();
    }

    private void setupUIElements(){
        this.btnSignIn = (Button) findViewById(R.id.btnSignIn);
        this.btnSingUp = (Button) findViewById(R.id.btnSignUp);
    }

    //Обробник подій.
    public void eventHandler(){
        clickButtonSignIn();
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
    private void clickButtonSignIn(){
        btnSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        });
    }
}