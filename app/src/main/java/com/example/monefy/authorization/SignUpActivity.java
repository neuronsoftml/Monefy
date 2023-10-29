package com.example.monefy.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.monefy.R;
import com.example.monefy.authorization.frame.CreateNewUserFragment;

public class SignUpActivity extends AppCompatActivity{

    private FragmentContainerView fragmentContainer;

    public void setupUIElements(){
        this.fragmentContainer = (FragmentContainerView) findViewById(R.id.fragmentContainer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupUIElements();

        loadFrame(new CreateNewUserFragment());
    }

    private void loadFrame(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainer,fragment);

        fragmentTransaction.commit();

    }
}