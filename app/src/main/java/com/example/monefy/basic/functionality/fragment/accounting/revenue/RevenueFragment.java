package com.example.monefy.basic.functionality.fragment.accounting.revenue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;

public class RevenueFragment extends Fragment {

    /**
     * Об'єкт контейнера фрагмента для відображення списка доходів.
     */
    private FragmentContainerView fragContViewRevenueList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        setupUIElements(view);
        showRevenueList();
        return  view;
    }


    /** Цей метод проводить ініціалізацію UI елементів вкладки.
     * @param view -  базовий клас для віджетів.
     */
    private void setupUIElements(View view){
        fragContViewRevenueList = view.findViewById(R.id.fragContViewRevenueList);
    }

    /** Цей метод завантажує та відображає Fragment списку доходів.
     *
     */
    private void showRevenueList(){
        FragmentNavigation.addFragmentInTheMiddleOfAnother(
                getChildFragmentManager(),
                new RevenueListFragment(),
                fragContViewRevenueList.getId(),
                "RevenueListFragment"
        );
    }
}