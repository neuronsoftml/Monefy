package com.example.monefy.basic.functionality.fragment.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.Interface.navigation.ClickListener;

public class ConfirmationFragment extends Fragment {
    private ImageButton imgBtnClose, imgBtnSetUp;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_confirmation, container, false);
        setupUIElements(view);
        handlerClickButton();
        return view;
    }

    private void handlerClickButton() {
        imgBtnClose.setOnClickListener(v->{
            clickListener.clickBtnClose();
        });

        imgBtnSetUp.setOnClickListener(v->{
            clickListener.clickBtnSetUp();
        });
    }

    private void setupUIElements(View view){
        imgBtnClose = view.findViewById(R.id.imgBtnClose);
        imgBtnSetUp = view.findViewById(R.id.imgBtnSetUp);
    }


}