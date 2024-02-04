package com.example.monefy.authorization.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.monefy.basic.functionality.HomeActivity;
import com.example.monefy.R;
import com.example.monefy.Manager.firebase.AuthenticationManager;

import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.message.ToastManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationEmailFragment extends Fragment{

    private TextView errorTextMessage;
    private String email, pass;
    private Button btnNext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            this.email = args.getString("email");
            this.pass = args.getString("password");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verification_email, container, false);
        setupUIElements(view);
        eventHandler();
        return view;
    }

    public void setupUIElements(View view) {
        this.errorTextMessage = (TextView) view.findViewById(R.id.errorMessageText);
        this.btnNext = (Button) view.findViewById(R.id.btnNext);
    }

    //обробник кнопки.
    public void eventHandler() {
        btnNext.setOnClickListener(v -> checkVerification());
    }

    //Переверяє чи пройшов користувач перевірку електроної скриньки.
    private void checkVerification(){
        AuthenticationManager authenticationManager = AuthenticationManager.getAuthenticationManager();
        authenticationManager.signInWithEmailAndPasswordCallback(
                FirebaseAuth.getInstance(),
                email, pass,
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if(user != null){
                            if(user.isEmailVerified()){
                                navigationToHome();
                            }else{
                                if(isSendMessage){
                                    showErrorMessage();
                                }else{
                                    sendVerificationEmailAndSetUI(user);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                }
        );
    }

    //Індекатор чи було відправлено повідомлення.
    private boolean isSendMessage;

    //Навігація переходу на іншу сторінку Home.
    private void navigationToHome(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    //Відправляє повідомлення.
    private void sendMessageVerificationEmail(FirebaseUser user){
        if (user != null) {
            user.sendEmailVerification().addOnSuccessListener(unused -> {
                isSendMessage = true;
                ToastManager.showToastOnSuccessful(getContext(),R.string.textSuccessfulSendMessageEmail);
            });
        }
    }

    //Виводить помилку.
    private void showErrorMessage(){
        errorTextMessage.setVisibility(View.VISIBLE);
    }

    //Обновляє інтерфейс користувача.
    private void sendVerificationEmailAndSetUI(FirebaseUser user) {
        sendMessageVerificationEmail(user);
        btnNext.setText(R.string.textNext);
    }

}