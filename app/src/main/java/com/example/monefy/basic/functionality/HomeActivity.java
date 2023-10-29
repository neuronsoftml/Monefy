package com.example.monefy.basic.functionality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.BillingsListAdapter;
import com.example.monefy.basic.functionality.fragment.BillingsFragment;
import com.example.monefy.basic.functionality.fragment.CategoriesFragment;
import com.example.monefy.basic.functionality.fragment.OperationsFragment;
import com.example.monefy.basic.functionality.fragment.ProfileFragment;
import com.example.monefy.basic.functionality.fragment.ReviewFragment;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.basic.functionality.model.TypeCurrency;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFragment(new BillingsFragment());

        setupUIElements();

        handlerClickButton();
    }


    private void setupUIElements(){
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
    }

    private void handlerClickButton(){
        clickButtonBottomNavigationView();
    }

    private void clickButtonBottomNavigationView(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.button_navigation_billings) {
                    replaceFragment(new BillingsFragment());
                    return true;
                } else if (id == R.id.button_navigation_categories) {
                    replaceFragment(new CategoriesFragment());
                    return true;
                } else if (id == R.id.button_navigation_operations) {
                    replaceFragment(new OperationsFragment());
                    return true;
                } else if (id == R.id.button_navigation_review) {
                    replaceFragment(new ReviewFragment());
                    return true;
                } else if (id == R.id.button_navigation_profile) {
                replaceFragment(new ProfileFragment());
                return true;
            }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerHome,fragment);
        fragmentTransaction.commit();
    }

}