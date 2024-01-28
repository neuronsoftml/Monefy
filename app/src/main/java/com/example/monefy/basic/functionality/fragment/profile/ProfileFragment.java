package com.example.monefy.basic.functionality.fragment.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.monefy.MainActivity;
import com.example.monefy.R;
import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.local.database.ManagerLocalDataBase;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setupUIElements(view);
        loadGetUserData();
        handlerBtnClick();
        return view;
    }

    private TextView textFullUserName, textEmail;
    private ImageButton imgBtnLogout;
    private void setupUIElements(View view){
        this.textFullUserName = view.findViewById(R.id.textViewFullUserName);
        this.textEmail = view.findViewById(R.id.textViewEmail);
        this.imgBtnLogout = view.findViewById(R.id.imgBtnLogout);
    }

    private void displaysUserData(String Name, String lastName){
        textFullUserName.setText(Name + " " + lastName);
        textEmail.setText(AuthenticationManager.getAuthenticationManager().getEmail());
    }


    private void loadGetUserData(){

    }

    private void handlerBtnClick() {
        handlerBtnLogout();
    }

    private void handlerBtnLogout() {
        imgBtnLogout.setOnClickListener(v->{
            AuthenticationManager.signOut();

            ManagerLocalDataBase.deleteUserToId(getContext(),0);

            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}