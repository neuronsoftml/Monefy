package com.example.monefy.basic.functionality.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.BillingsListAdapter;
import com.example.monefy.basic.functionality.adapter.OnItemClickListener;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalAccount;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BillingsFragment extends Fragment {

    private ListView listView;
    private Button buttonAddBillings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_billings, container, false);

        setupUIElements(view);
        loadBillings();
        handlerButtonClick();
        return view;
    }

    private void setupUIElements(View view){
        this.listView = (ListView) view.findViewById(R.id.list_item_billings);
        this.buttonAddBillings = (Button) view.findViewById(R.id.button_add_billings);
    }

    private List<Billings> billings = new ArrayList<>();

    private BillingsListAdapter billingsListAdapter;

    private void showBillingsList(){
        billingsListAdapter = new BillingsListAdapter(getContext(),billings);
        listView.setAdapter(billingsListAdapter);
    }

    private void loadBillings(){
        String userId = AuthenticationManager.getAuthenticationManager().getUserId();
        FirebaseManager.getBillingsData(
                FirebaseFirestore.getInstance(),
                userId,
                new FirebaseManager.OnBillingsCallback() {
                    @Override
                    public void onBillingsDataReceived(List<Billings> billingsList) {
                        billings.clear();
                        billings.addAll(billingsList);
                        showBillingsList();
                        handlerClickItemListBillings();
                        Log.v("list", String.valueOf(billings.size()));
                    }

                    @Override
                    public void onBillingsDataNotFound() {
                        Log.d("error","Відсутні дані");
                    }

                    @Override
                    public void onBillingsDataError(Exception e) {
                        Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
                    }
                }
        );

    }

    private void handlerButtonClick(){
        clickButtonAddBillings();
    }

    private void handlerClickItemListBillings() {
        billingsListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Billings billings) {
                ModalAccount modalAccount = new ModalAccount(getContext(),billings);
                modalAccount.modalStart(new DialogCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                });
            }
        });
    }

    private void clickButtonAddBillings(){
        buttonAddBillings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalTypeBillings modalTypeBillings = new ModalTypeBillings(getContext());
                modalTypeBillings.modalStart(new DialogCallback() {
                    @Override
                    public void onSuccess() {
                        replaceFragmentToDate(modalTypeBillings.getUpdateData());
                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                });
            }
        });
    }

    private void replaceFragmentToDate(String value){
        Bundle bundle = new Bundle();
        bundle.putString("TypeBillings", value);
        CreateBillingsFragment fragment = new CreateBillingsFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerHome, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}