package com.example.monefy.authorization.frame;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monefy.basic.functionality.HomeActivity;
import com.example.monefy.R;
import com.example.monefy.tools.firebase.AuthenticationManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationEmailFragment extends Fragment{

    private TextView errorTextMessage;
    private String email,password;
    private Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            this.email = args.getString("email");
            this.password = args.getString("password");
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
        this.nextButton = (Button) view.findViewById(R.id.buttonNext);
    }

    //обробник кнопки.
    public void eventHandler() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVerification();
            }
        });
    }

    //Переверяє чи пройшов користувач перевірку електроної скриньки.
    private void checkVerification(){
        AuthenticationManager.signInWithEmailAndPasswordCallback(
                FirebaseAuth.getInstance(),
                email, password,
                new AuthenticationManager.InConclusionCompleteListener() {
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
                Toast.makeText(getContext(), "повідомлення успішно було відправлено", Toast.LENGTH_SHORT).show();
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
        nextButton.setText(R.string.button_next);
    }

}