package com.example.monefy.basic.functionality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.BillingsFragment;
import com.example.monefy.basic.functionality.fragment.CategoriesFragment;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.OperationsFragment;
import com.example.monefy.basic.functionality.fragment.ProfileFragment;
import com.example.monefy.basic.functionality.fragment.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView btmNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentSwitcher.replaceFragment(
                new BillingsFragment(),
                getSupportFragmentManager(),
                FragmentSwitcher.getContainerHome());
        setupUIElements();
        handlerClickButton();
    }

    private void setupUIElements(){
        this.btmNavView = (BottomNavigationView) findViewById(R.id.btmNavView);
    }

    private void handlerClickButton(){
        clickButtonBottomNavigationView();
    }

    private void clickButtonBottomNavigationView(){
        btmNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            FragmentManager support = getSupportFragmentManager();
            if (id == R.id.btnNavBillings) {
                FragmentSwitcher.replaceFragment(
                        new BillingsFragment(),
                        support,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavCategories) {
                FragmentSwitcher.replaceFragment(
                        new CategoriesFragment(),
                        support,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavOperations) {
                FragmentSwitcher.replaceFragment(
                        new OperationsFragment(),
                        support,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavReview) {
                FragmentSwitcher.replaceFragment(
                        new ReviewFragment(),
                        support,
                        FragmentSwitcher.getContainerHome());
                return true;
            } else if (id == R.id.btnNavProfile) {
                FragmentSwitcher.replaceFragment(
                        new ProfileFragment(),
                        support,
                        FragmentSwitcher.getContainerHome());
            return true;
        }
            return false;
        });
    }


}