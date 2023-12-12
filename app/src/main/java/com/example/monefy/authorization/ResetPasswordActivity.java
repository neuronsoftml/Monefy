package com.example.monefy.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.message.ToastManager;
import com.google.firebase.auth.FirebaseAuth;

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
        this.inputEmail = (EditText) findViewById(R.id.inputEmailNewUser);
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
        FirebaseManager.resetPasswordWithEmail( email,
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
        errorTextMessage.setText(R.string.successfully_message_text_send_email);
        errorTextMessage.setTextColor(getResources().getColor(R.color.blue));
        errorTextMessage.setVisibility(View.VISIBLE);
        ToastManager.showToastOnSuccessful(getApplicationContext(), R.string.toast_successful_message_reset_pass);
    }

    //Обробник не успішного відпраленого повідомлення.
    private void handlerErrorSendMessageEmail(){
        errorTextMessage.setText(R.string.error_message_text_not_found_email);
        errorTextMessage.setVisibility(View.VISIBLE);
        ToastManager.showToastOnFailure(getApplicationContext(),R.string.toast_failure_error_type_email);
    }
}