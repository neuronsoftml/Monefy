package com.example.monefy.authorization.frame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.tools.firebase.InConclusionCompleteListener;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.example.monefy.tools.input.EmailValidator;
import com.example.monefy.tools.input.PasswordValidator;
import com.google.firebase.auth.FirebaseAuth;


public class CreateNewUserFragment extends Fragment{

    private Button nextButton;
    private EditText inputEmail, inputPassword;
    private TextView errorTextMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_user, container, false);

        setupUIElements(view);

        clickButtonNext();

        return view;
    }

    private void setupUIElements(View view){
        this.nextButton = (Button) view.findViewById(R.id.buttonNext);
        this.inputEmail = (EditText) view.findViewById(R.id.inputEmail);
        this.inputPassword = (EditText) view.findViewById(R.id.inputPassword);
        this.errorTextMessage = (TextView) view.findViewById(R.id.errorMessageText);
    }

    //Обробник кліка по кнопці продовжити.
    private void clickButtonNext(){
        nextButton.setOnClickListener(view -> {

            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

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
        inputPassword.setBackgroundResource(R.drawable.selector_error_input);
        showErrorMessage(R.string.error_message_text_password);
    }

    //Виводить повідомлення про помилку.
    private void showErrorMessage(int id){
        errorTextMessage.setVisibility(View.VISIBLE);
        errorTextMessage.setText(id);
    }

    //Створює нового користувача у FireBase
    private void createNewUser(String email, String password){
        FirebaseManager.createNewUserWithEmailPassword(
                FirebaseAuth.getInstance(),
                getActivity(),
                email,
                password,
                new InConclusionCompleteListener() {
                    @Override
                    public void onSuccess() {
                        navigationFragmentToVerification(email,password);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        showErrorMessage(R.string.error_message_text_create_new_user);
                    }
                }
        );
    }

    //Навігація переходу Fragment на Verification Email.
    private void navigationFragmentToVerification(String email, String password){
        Fragment fragment = new VerificationEmailFragment();

        Bundle data = new Bundle();
        data.putString("email", email);
        data.putString("password", password);

        fragment.setArguments(data);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();
    }

}