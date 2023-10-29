package com.example.monefy.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monefy.basic.functionality.HomeActivity;
import com.example.monefy.R;
import com.example.monefy.local.database.AppDatabase;
import com.example.monefy.local.database.model.User;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button buttonSignIn;
    private Switch aSwitch;
    private TextView recoverPassword, errorTextMessage;

    //Визначення обєктів активності.
    private void setupUIElements() {
        this.buttonSignIn = (Button) findViewById(R.id.buttonSingIn);

        this.inputEmail = (EditText) findViewById(R.id.inputEmail);
        this.inputPassword = (EditText) findViewById(R.id.inputPassword);

        this.recoverPassword = (TextView) findViewById(R.id.recoverPassword);
        this.errorTextMessage = (TextView) findViewById(R.id.errorMessageText);

        this.aSwitch = (Switch) findViewById(R.id.switchSaveData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setupUIElements();

        handlerClickButton();

    }

    //Обронник кліків по кнопках.
    private void handlerClickButton(){
        clickButtonSignIn();
        clickTextRecoverPassword();
    }

    //Обронник кліка по реєстрації.
    private void clickButtonSignIn(){
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if(checkInputIsEmpty(email,password)){
                    handlerSignIn(email,password);
                }else{
                    errorSignInHandler();
                }
            }
        });
    }

    //Механіка аторизацї на FireBase.
    private void handlerSignIn(String email, String password) {
        AuthenticationManager.signInWithEmailAndPasswordCallback(
                FirebaseAuth.getInstance(),
                email,
                password, new AuthenticationManager.InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        if (aSwitch.isChecked()) {
                            autoSaveLocalDate(email, password);
                        }
                        navigationToHome();
                        Toast.makeText(SignInActivity.this, "Авторизація успішна", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        errorSignInHandler();
                        Toast.makeText(SignInActivity.this, "Помилка авторизації: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Навігація переходу на сторінку Home.
    private void navigationToHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    //Навігаці переходу на сторінку відновлення пароля.
    private void navigationToResetPassword(){
        Intent intent = new Intent(this,ResetPasswordActivity.class);
        startActivity(intent);
    }

    //Оброник кліка по тексту відновлення пароля.
    private void clickTextRecoverPassword(){
        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationToResetPassword();
            }
        });
    }

    //Виводить повідомлення помилки
    private void showErrorMessage(int id){
        errorTextMessage.setVisibility(View.VISIBLE);
        errorTextMessage.setText(id);
    }

    //Обробник помилки авторизації.
    private void errorSignInHandler(){
        showErrorMessage(R.string.error_message_text_password_or_email);
        inputEmail.setBackgroundResource(R.drawable.selector_error_input);
        inputPassword.setBackgroundResource(R.drawable.selector_error_input);
    }

    //Перевірка чи поле не пусте.
    private boolean checkInputIsEmpty(String email, String password){
        if(email.isEmpty() || password.isEmpty()){
            return false;
        }
        return true;
    }

    //Збереження данних про автоматичний вхід
    private void autoSaveLocalDate(String email, String password){
        AppDatabase databaseManager = AppDatabase.getObInstance(getApplicationContext());

        int userCount = databaseManager.userDao().getUserCountById(0);
        if (userCount > 0) {
            // Користувач із заданим email існує
            databaseManager.userDao().deleteUserById(0);
            databaseManager.userDao().insert(new User(email,password,true));
        } else {
            // Користувач із заданим email відсутній в базі даних
            databaseManager.userDao().insert(new User(email,password,true));
        }
    }
}