package com.example.monefy.basic.functionality.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.billings.BillingsFragment;
import com.example.monefy.basic.functionality.fragment.billings.EditBillingsFragment;

public class FragmentNavigation {
    private static final int containerHome = R.id.containerHome;
    private static final int containerVerification = R.id.containerVerification;

    /** Цей метод змінює в контейнері фрагменти.
     * @param fragmentManager
     * @param fragment Новий фрагмент для відображення
     * @param container Id Контейнера де необхідно відобразити fragment
     * @param tagToBackStack Тег фрагмента
     */
    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int container, String tagToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(tagToBackStack);
        fragmentTransaction.replace(container,fragment,tagToBackStack).commit();
    }

    /** Цей метод додає Fragment в середині іншого Fragment - та.
     * @param fragmentManager
     * @param fragment Новий фрагмент який додаємо.
     * @param container Id Контейнера де необхідно відобразити fragment.
     */
    public static void addFragmentInTheMiddleOfAnother(FragmentManager fragmentManager, Fragment fragment, int container, String tagToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragment,tagToBackStack);
        fragmentTransaction.addToBackStack(tagToBackStack);
        fragmentTransaction.commit();
    }

    /** Цей метод здійснює перехід до BillingsFragment
     * @param fragmentManager
     */
    public static void goToReplaceBillingsFragment(FragmentManager fragmentManager){
        replaceFragment(fragmentManager,new BillingsFragment(),containerHome,"BillingsFragment");
    }

    /** Цей метод здійснює перехід до EditBillingsFragment
     * @param fragmentManager
     */
    public static void gotToReplaceEditBillingsFragment(FragmentManager fragmentManager, Bundle bundle){
        EditBillingsFragment editBillingsFragment = new EditBillingsFragment();
        editBillingsFragment.setArguments(bundle);
        replaceFragment(fragmentManager, editBillingsFragment, containerHome, "EditBillingsFragment");
    }

    public static int getContainerHome() {
        return containerHome;
    }

    public static int getContainerVerification(){
        return containerVerification;
    }
}
