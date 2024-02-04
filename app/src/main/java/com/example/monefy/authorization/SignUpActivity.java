package com.example.monefy.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.monefy.LobbyActivity;
import com.example.monefy.R;
import com.example.monefy.authorization.fragment.CreateNewUserFragment;
import com.example.monefy.basic.functionality.fragment.FragmentNavigation;

public class SignUpActivity extends AppCompatActivity{
    private FragmentContainerView fragmentContainerView;
    private Button btnClose;

    public void setupUIElements(){
        this.fragmentContainerView = findViewById(R.id.containerVerification);
        this.btnClose = findViewById(R.id.btnClose);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUIElements();
        showFragVerification();
        handlerClickBtnClose();
    }

    private void showFragVerification(){
        FragmentNavigation.replaceFragment(
                getSupportFragmentManager(),
                new CreateNewUserFragment(),
                fragmentContainerView.getId(),
                "CreateNewUserFragment");
    }

    private void handlerClickBtnClose(){
        btnClose.setOnClickListener(v->{
            Intent intent = new Intent(this, LobbyActivity.class);
            startActivity(intent);
        });
    }
}