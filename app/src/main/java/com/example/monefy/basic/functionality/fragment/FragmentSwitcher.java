package com.example.monefy.basic.functionality.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.billings.BillingsListOneFragment;

public class FragmentSwitcher {
    private static final int containerHome = R.id.containerHome;
    private static final int containerVerification = R.id.containerVerification;

    public static void replaceFragment(Fragment fragment, FragmentManager support, int container){
        FragmentTransaction fragmentTransaction = support.beginTransaction();
        fragmentTransaction.replace(container,fragment);
        fragmentTransaction.commit();
    }

    public static void replaceFragmentToDate(Fragment showFragment, Bundle bundle,Context context, int container){
        showFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, showFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
