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
    private void showBillingsList(){
        BillingsListAdapter billingsListAdapter = new BillingsListAdapter(getContext(),billings);
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

    private void clickButtonAddBillings(){
        buttonAddBillings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogModal();
            }
        });
    }

    private Dialog dialogModalAddBillings;
    private void showDialogModal(){
        this.dialogModalAddBillings = new Dialog(getContext());
       dialogModalAddBillings.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialogModalAddBillings.setContentView(R.layout.model_bootom_type_billings);
       dialogModalAddBillings.show();
       dialogModalAddBillings.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
       dialogModalAddBillings.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       dialogModalAddBillings.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
       dialogModalAddBillings.getWindow().setGravity(Gravity.BOTTOM);

       setupUIDialogModal(dialogModalAddBillings);
       handlerButtonDialogModal();
    }

    private Button buttonOrdinary, buttonDebt,buttonCumulative;
    private void setupUIDialogModal(Dialog dialog){
        this.buttonOrdinary = (Button) dialog.findViewById(R.id.button_ordinary);
        this.buttonDebt = (Button) dialog.findViewById(R.id.button_debt);
        this.buttonCumulative = (Button) dialog.findViewById(R.id.button_cumulative);
    }

    private void handlerButtonDialogModal(){
        clickButtonOrdinary();
        clickButtonDebt();
        clickButtonCumulative();
    }

    private void clickButtonOrdinary(){
        buttonOrdinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogModalAddBillings.cancel();
                replaceFragmentToDate(
                        TypeBillings.ORDINARY.getTypeBillingsTitle()
                );
            }
        });
    };

    private void clickButtonDebt(){
        buttonDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogModalAddBillings.cancel();
                replaceFragmentToDate(
                        TypeBillings.DEBT.getTypeBillingsTitle()
                );
            }
        });
    };

    private void clickButtonCumulative(){
        buttonCumulative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogModalAddBillings.cancel();
               replaceFragmentToDate(
                       TypeBillings.CUMULATIVE.getTypeBillingsTitle());
            }
        });
    };

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