package com.example.monefy.basic.functionality.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.NBU.CallbackNbu;
import com.example.monefy.basic.functionality.NBU.NbuManager;
import com.example.monefy.basic.functionality.adapter.BillingsListAdapter;
import com.example.monefy.basic.functionality.fragment.dialogModal.AccountDialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalAccount;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTypeBillings;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.CurrencyNbu;
import com.example.monefy.basic.functionality.model.TotalAmount;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.example.monefy.tools.firebase.OnBillingsCallback;
import com.example.monefy.tools.message.ToastManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BillingsFragment extends Fragment {

    private ListView listView;
    private Button btnAddBillings;
    private TextView tVTitleUSD, tVTitleEUR, tVTitleRON;
    private TextView tVRateUSD, tVRateEUR, tVRateRON;
    private TextView tVTotalBill;
    private List<CurrencyNbu> currencyNbuRates = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billings, container, false);

        setupUIElements(view);
        loadBillings();
        loadCurrencyNBU();
        handlerButtonClick();
        return view;
    }

    private void setupUIElements(View view){
        this.listView = (ListView) view.findViewById(R.id.list_item_billings);
        this.btnAddBillings = (Button) view.findViewById(R.id.button_add_billings);

        this.tVTitleUSD = view.findViewById(R.id.tVTitleUSD);
        this.tVTitleEUR = view.findViewById(R.id.tVTitleEUR);
        this.tVTitleRON = view.findViewById(R.id.tVTitleRON);
        this.tVRateUSD = view.findViewById(R.id.tVRateUSD);
        this.tVRateEUR = view.findViewById(R.id.tvRateEUR);
        this.tVRateRON = view.findViewById(R.id.tVRateRON);

        this.tVTotalBill = view.findViewById(R.id.tVtotalBill);
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
                new OnBillingsCallback() {
                    @Override
                    public void onBillingsDataReceived(List<Billings> billingsList) {
                        billings.clear();
                        billings.addAll(billingsList);
                        isBillingsLoaded = true;
                        checkDataLoadingStatus();
                    }

                    @Override
                    public void onBillingsDataNotFound() {
                        Log.d("error","Відсутні дані");
                        isBillingsLoaded = true;
                        checkDataLoadingStatus();
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
        billingsListAdapter.setOnItemClickListener(billings -> {
            ModalAccount modalAccount = new ModalAccount(getContext(),billings);

            AccountDialogCallback accountDialogCallback = new AccountDialogCallback() {
                @Override
                public void onSuccessDelete() {
                    billingsListAdapter.removeBillings(billings);
                    billingsListAdapter.notifyDataSetChanged();
                    ToastManager.showToastOnSuccessful(getContext(),R.string.toast_successful_delete_billings);
                    checkDataLoadingStatus();
                }

                @Override
                public void onClickEdit() {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("billing", billings);
                    FragmentSwitcher.replaceFragmentToDate(
                            new EditBillingsFragment(),
                            bundle,
                            getContext(),
                            FragmentSwitcher.getContainerHome()
                    );
                    checkDataLoadingStatus();
                }

                @Override
                public void onClickOperations() {

                }

                @Override
                public void onClickDeposit() {

                }

                @Override
                public void onClickWriteOff() {

                }

                @Override
                public void onClickTransfer() {

                }

                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception exception) {

                }
            };
            modalAccount.modalStart(accountDialogCallback);
        });
    }

    private void clickButtonAddBillings(){
        btnAddBillings.setOnClickListener(v -> {
            ModalTypeBillings modalTypeBillings = new ModalTypeBillings(getContext());
            modalTypeBillings.modalStart(new DialogCallback() {
                @Override
                public void onSuccess() {
                    Bundle bundle = new Bundle();
                    bundle.putString("TypeBillings", modalTypeBillings.getUpdateData());

                    FragmentSwitcher.replaceFragmentToDate(
                            new CreateBillingsFragment(),
                            bundle,
                            getContext(),
                            FragmentSwitcher.getContainerHome()
                    );
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        });
    }

    private void loadCurrencyNBU() {
        NbuManager.currencyNbuParse(new CallbackNbu() {
            @Override
            public void onResponse() {
                currencyNbuRates.addAll(NbuManager.getCurrencyRates());
                isCurrencyLoaded = true;
                checkDataLoadingStatus();
            }

            @Override
            public void onFailure() {
                Log.v("NBU","ПОМИЛКА ЗЄДНАННЯ НБУ");
            }
        });
    }

    private void updateDataUINbu(){
        for(CurrencyNbu currencyNbu : currencyNbuRates){
            if(currencyNbu.getCc().equals("USD")){
                tVTitleUSD.setText(currencyNbu.getCc());
                tVRateUSD.setText(String.valueOf(currencyNbu.getRate()));
            } else if (currencyNbu.getCc().equals("EUR")) {
                tVTitleEUR.setText(currencyNbu.getCc());
                tVRateEUR.setText(String.valueOf(currencyNbu.getRate()));
            } else if (currencyNbu.getCc().equals("RON")) {
                tVTitleRON.setText(currencyNbu.getCc());
                tVRateRON.setText(String.valueOf(currencyNbu.getRate()));
            }
        }
    }

    private boolean isBillingsLoaded = false;
    private boolean isCurrencyLoaded = false;

    private void checkDataLoadingStatus() {
        if (isBillingsLoaded && isCurrencyLoaded) {
            handleDataLoaded(billings, currencyNbuRates);
        }
    }

    private void handleDataLoaded(List<Billings> billings, List<CurrencyNbu> currency) {
        TotalAmount totalAmount = new TotalAmount(0,billings, currency);
        tVTotalBill.setText(String.valueOf(totalAmount.getAmount()));
        updateDataUINbu();
        showBillingsList();
        handlerClickItemListBillings();
    }
}