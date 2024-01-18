package com.example.monefy.basic.functionality.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.monefy.R;

public class FragmentSwitcher {
    private static final int containerHome = R.id.containerHome;
    private static final int containerVerification = R.id.containerVerification;

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int container){
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    public static void addTransactionFragment(FragmentManager fragmentManager, Fragment fragment, int containerView){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerView, fragment);
        transaction.commit();
    }
    public static void replaceTransactionFragment(FragmentManager fragmentManager, Fragment fragment, int containerView){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragmentBack(Context context){
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            ((FragmentActivity)context).finish();
        }
    }

    public static int getContainerHome() {
        return containerHome;
    }

    public static int getContainerVerification(){
        return containerVerification;
    }
}
