package com.example.monefy.authorization.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.input.EmailValidator;
import com.example.monefy.Manager.input.PasswordValidator;
import com.google.firebase.auth.FirebaseAuth;


public class CreateNewUserFragment extends Fragment{

    private Button btnNext;
    private EditText inputEmail, inputPass;
    private TextView errorTextMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        setupUIElements(view);

        clickButtonNext();

        return view;
    }

    private void setupUIElements(View view){
        this.btnNext = (Button) view.findViewById(R.id.btnNext);
        this.inputEmail = (EditText) view.findViewById(R.id.inputEmailNewUser);
        this.inputPass = (EditText) view.findViewById(R.id.inputPassNewUser);
        this.errorTextMessage = (TextView) view.findViewById(R.id.errorMessageText);
    }

    //Обробник кліка по кнопці продовжити.
    private void clickButtonNext(){
        btnNext.setOnClickListener(view -> {

            String email = inputEmail.getText().toString();
            String password = inputPass.getText().toString();

            if(validateInputDate(email,password)){
                createNewUser(email,password);
            }

        });
    }

    //Перевірка веденних даних.
    private boolean validateInputDate(String email, String password){
        if(!EmailValidator.detectVerificationEmail(email)){
            emailErrorHandler();
            return false;
        }
        else if(!PasswordValidator.detectVerificationPassword(password)){
            passwordErrorHandler();
            return  false;
        }
        return true;
    }

    //Обробник помилки в input email.
    private void emailErrorHandler(){
        inputEmail.setBackgroundResource(R.drawable.selector_error_input);
        showErrorMessage(R.string.error_message_text_email);
    }

    //Обробник помилки в input password.
    private void passwordErrorHandler(){
        inputPass.setBackgroundResource(R.drawable.selector_error_input);
        showErrorMessage(R.string.error_message_text_password);
    }

    //Виводить повідомлення про помилку.
    private void showErrorMessage(int id){
        errorTextMessage.setVisibility(View.VISIBLE);
        errorTextMessage.setText(id);
    }

    //Створює нового користувача у FireBase
    private void createNewUser(String email, String password){
        FirebaseManager.createNewUserWithEmailPassword(getActivity(), email, password,
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        Bundle data = new Bundle();
                        data.putString("email", email);
                        data.putString("password", password);

                        FragmentSwitcher.replaceFragmentToDate(
                                new VerificationEmailFragment(),
                                data,
                                getContext(),
                                FragmentSwitcher.getContainerVerification()
                        );
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        showErrorMessage(R.string.error_message_text_create_new_user);
                    }
                }
        );
    }
}