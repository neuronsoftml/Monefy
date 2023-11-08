package com.example.monefy.basic.functionality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.billings.BillingsFragment;
import com.example.monefy.basic.functionality.fragment.CategoriesFragment;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.OperationsFragment;
import com.example.monefy.basic.functionality.fragment.billings.CreateBillingsFragment;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalCreate;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.fragment.profile.ProfileFragment;
import com.example.monefy.basic.functionality.fragment.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView btmNavView;

    private ImageButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentSwitcher.replaceFragment(
                new BillingsFragment(),
                this,
                FragmentSwitcher.getContainerHome());
        setupUIElements();
        handlerClickButton();
    }

    private void setupUIElements(){
        this.btmNavView = (BottomNavigationView) findViewById(R.id.btmNavView);
        this.btnAdd = findViewById(R.id.btnAdd);
    }

    private void handlerClickButton(){
        clickButtonBottomNavigationView();
        clickButtonAddBillings();
    }

    private void clickButtonBottomNavigationView(){
        btmNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Context context =  this;
            if (id == R.id.btnNavBillings) {
                FragmentSwitcher.replaceFragment(
                        new BillingsFragment(),
                        context,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavCategories) {
                FragmentSwitcher.replaceFragment(
                        new CategoriesFragment(),
                        context,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavOperations) {
                FragmentSwitcher.replaceFragment(
                        new OperationsFragment(),
                        context,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavReview) {
                FragmentSwitcher.replaceFragment(
                        new ReviewFragment(),
                        context,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavProfile) {
                FragmentSwitcher.replaceFragment(
                        new ProfileFragment(),
                        context,
                        FragmentSwitcher.getContainerHome());
            return true;
        }
            return false;
        });
    }

    private void clickButtonAddBillings(){
        Context context = this;
        btnAdd.setOnClickListener(v -> {
            ModalCreate modalCreate = new ModalCreate(context,R.layout.modal_bottom_create, getSupportFragmentManager());
            modalCreate.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }
}