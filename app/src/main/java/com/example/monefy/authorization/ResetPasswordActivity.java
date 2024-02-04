package com.example.monefy.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.message.ToastManager;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button btnResetPass;
    private EditText inputEmail;
    private TextView errorTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setupUIElements();
        clickResetPassword();
    }

    private void setupUIElements(){
        this.btnResetPass = (Button) findViewById(R.id.btnNext);
        this.inputEmail = (EditText) findViewById(R.id.inputEmail);
        this.errorTextMessage = (TextView) findViewById(R.id.errorMessageText);
    }

    //Обробник кліка по кнопці скинути пароль.
    private void clickResetPassword(){
        btnResetPass.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            if(checkInputIsEmpty(email)){
                resetPassword(email);
            }
        });
    }

    //Перевірка чи поле не пусте.
    private boolean checkInputIsEmpty(String email){
        return !email.isEmpty();
    }

    //Скидання пароля.
    private void resetPassword(String email){
        FirebaseManager firebaseManager = FirebaseManager.getFirebaseManager();

        firebaseManager.resetPasswordWithEmail( email,
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        handlerSuccessfullySendMessageEmail();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        handlerErrorSendMessageEmail();
                    }
                }
        );
    }

    //Обробник успішного відпраленого повідомлення.
    private void handlerSuccessfullySendMessageEmail(){
        errorTextMessage.setText(R.string.textSuccessfullyMessageSendEmail);
        errorTextMessage.setTextColor(getResources().getColor(R.color.blue));
        errorTextMessage.setVisibility(View.VISIBLE);
        ToastManager.showToastOnSuccessful(getApplicationContext(), R.string.textSuccessfulMessageResetPass);
    }

    //Обробник не успішного відпраленого повідомлення.
    private void handlerErrorSendMessageEmail(){
        errorTextMessage.setText(R.string.textErrorMessageNotFoundEmail);
        errorTextMessage.setVisibility(View.VISIBLE);
        ToastManager.showToastOnFailure(getApplicationContext(),R.string.textFailureErrorTypeEmail);
    }
}