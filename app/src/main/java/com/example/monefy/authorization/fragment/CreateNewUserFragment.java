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
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;
import com.example.monefy.basic.functionality.Interface.firebase.InConclusionCompleteListener;
import com.example.monefy.basic.functionality.controller.firebase.FirebaseController;
import com.example.monefy.basic.functionality.Interface.input.EmailValidator;
import com.example.monefy.basic.functionality.Interface.input.PasswordValidator;


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
        this.inputEmail = (EditText) view.findViewById(R.id.inputEmail);
        this.inputPass = (EditText) view.findViewById(R.id.inputPassword);
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
        showErrorMessage(R.string.textErrorMessageEmail);
    }

    //Обробник помилки в input password.
    private void passwordErrorHandler(){
        inputPass.setBackgroundResource(R.drawable.selector_error_input);
        showErrorMessage(R.string.textErrorMessagePassword);
    }

    //Виводить повідомлення про помилку.
    private void showErrorMessage(int id){
        errorTextMessage.setVisibility(View.VISIBLE);
        errorTextMessage.setText(id);
    }

    //Створює нового користувача у FireBase
    private void createNewUser(String email, String password){
        FirebaseController firebaseController = FirebaseController.getFirebaseManager();
        firebaseController.createNewUserWithEmailPassword(requireActivity(), email, password,
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        Bundle data = new Bundle();
                        data.putString("email", email);
                        data.putString("password", password);

                        VerificationEmailFragment verificationEmailFragment = new VerificationEmailFragment();
                        verificationEmailFragment.setArguments(data);
                        FragmentNavigation.replaceFragment(
                                getActivity().getSupportFragmentManager(),
                                verificationEmailFragment,
                                FragmentNavigation.getContainerVerification(),
                                "VerificationEmailFragment"
                        );
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        showErrorMessage(R.string.textErrorMessageCreateNewUser);
                    }
                }
        );
    }
}