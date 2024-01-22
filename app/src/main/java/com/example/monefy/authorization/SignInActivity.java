package com.example.monefy.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.monefy.basic.functionality.HomeActivity;
import com.example.monefy.R;
import com.example.monefy.local.database.ManagerLocalDataBase;
import com.example.monefy.local.database.model.User;
import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.message.ToastManager;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail, inputPass;
    private Button btnSignIn;
    private Switch switchSaveMy;
    private TextView recoverPass, errorTextMessage;

    //Визначення обєктів активності.
    private void setupUIElements() {
        this.btnSignIn = (Button) findViewById(R.id.buttonSingIn);

        this.inputEmail = (EditText) findViewById(R.id.inputEmail);
        this.inputPass = (EditText) findViewById(R.id.inputPassword);

        this.recoverPass = (TextView) findViewById(R.id.recoverPassword);
        this.errorTextMessage = (TextView) findViewById(R.id.errorMessageText);

        this.switchSaveMy = (Switch) findViewById(R.id.switchSaveData);
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
        btnSignIn.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPass.getText().toString();

            if(checkInputIsEmpty(email,password)){
                handlerSignIn(email,password);
            }else{
                errorSignInHandler();
            }
        });
    }

    //Механіка аторизацї на FireBase.
    private void handlerSignIn(String email, String password) {
        AuthenticationManager.signInWithEmailAndPasswordCallback(
                FirebaseAuth.getInstance(),
                email,
                password, new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        if (switchSaveMy.isChecked()) {
                            autoSaveLocalDate(email, password);
                        }
                        navigationToHome();
                        ToastManager.showToastOnSuccessful(getApplicationContext(),R.string.textSuccessSignIn);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        errorSignInHandler();
                        ToastManager.showToastOnFailure(getApplicationContext(),R.string.textFailureSignIn);
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
        recoverPass.setOnClickListener(v -> navigationToResetPassword());
    }

    //Виводить повідомлення помилки
    private void showErrorMessage(int id){
        errorTextMessage.setVisibility(View.VISIBLE);
        errorTextMessage.setText(id);
    }

    //Обробник помилки авторизації.
    private void errorSignInHandler(){
        showErrorMessage(R.string.textErrorMessageTextPasswordOrEmail);
        inputEmail.setBackgroundResource(R.drawable.selector_error_input);
        inputPass.setBackgroundResource(R.drawable.selector_error_input);
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
        User user = ManagerLocalDataBase.getUserToId(getApplicationContext(),0);
        if (user != null) {
            // Користувач із заданим email існує
            ManagerLocalDataBase.deleteUserToId(getApplicationContext(),0);
        }
        ManagerLocalDataBase.createUserToId(getApplicationContext(),email,password, switchSaveMy.isChecked());
    }

}