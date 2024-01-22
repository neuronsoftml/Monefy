package com.example.monefy.basic.functionality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.billings.BillingsFragment;
import com.example.monefy.basic.functionality.fragment.CategoriesFragment;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.OperationsFragment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelectCreate;
import com.example.monefy.basic.functionality.fragment.profile.ProfileFragment;
import com.example.monefy.basic.functionality.fragment.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private final int containerHome = R.id.containerHome;

    private BottomNavigationView btmNavView;

    private ImageButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentSwitcher.replaceFragment(getSupportFragmentManager(),new BillingsFragment(),containerHome);
        setupUIElements();
        handlerClickButton();
    }

    private void setupUIElements(){
        this.btmNavView = (BottomNavigationView) findViewById(R.id.btnNavView);
        this.btnAdd = findViewById(R.id.btnAdd);
    }

    private void handlerClickButton(){
        clickButtonBottomNavigationView();
        clickButtonAddBillings();
    }

    private void clickButtonBottomNavigationView(){
        btmNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.btnNavBillings) {
                FragmentSwitcher.replaceFragment(getSupportFragmentManager(),new BillingsFragment(),containerHome);
                return true;
            } else if (id == R.id.btnNavCategories) {
                FragmentSwitcher.replaceFragment(getSupportFragmentManager(),new CategoriesFragment(),containerHome);
                return true;
            } else if (id == R.id.btnNavOperations) {
                FragmentSwitcher.replaceFragment(getSupportFragmentManager(),new OperationsFragment(),containerHome);
                return true;
            } else if (id == R.id.btnNavReview) {
                FragmentSwitcher.replaceFragment(getSupportFragmentManager(),new ReviewFragment(),containerHome);
                return true;
            } else if (id == R.id.btnNavProfile) {
                FragmentSwitcher.replaceFragment(getSupportFragmentManager(),new ProfileFragment(),containerHome);
            return true;
        }
            return false;
        });
    }

    private void clickButtonAddBillings(){
        btnAdd.setOnClickListener(v -> {
            ModalSelectCreate modalCreate = new ModalSelectCreate(this,R.layout.modal_bottom_create, getSupportFragmentManager(), null);
            modalCreate.modalStart();
        });
    }
}