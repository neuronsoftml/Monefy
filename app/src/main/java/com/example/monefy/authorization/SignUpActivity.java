package com.example.monefy.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;

import com.example.monefy.R;
import com.example.monefy.authorization.fragment.CreateNewUserFragment;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;

public class SignUpActivity extends AppCompatActivity{

    private FragmentContainerView fragmentContainer;

    public void setupUIElements(){
        this.fragmentContainer = (FragmentContainerView) findViewById(R.id.containerVerification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupUIElements();

        FragmentSwitcher.replaceFragment(
                new CreateNewUserFragment(),
                getSupportFragmentManager(),
                FragmentSwitcher.getContainerVerification());
    }


}