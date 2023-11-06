package com.example.monefy.basic.functionality.fragment.billings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.BillingsListAdapter;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.dialogModal.BillingDialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBilling;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.example.monefy.tools.firebase.OnBillingsCallback;
import com.example.monefy.tools.message.ToastManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/*
     Цей фрагмент відображає список рахунків категорії:
        - ORDINARY("Звичайний")
        - DEBT("Борговий")
 */
public class BillingsListOneFragment extends Fragment {

    private ListView listItemBillings;
    private Context context;

    private void setupUIElements(View view){
       this.listItemBillings = view.findViewById(R.id.list_item_billings);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_billings_list_one, container, false);
       setupUIElements(view);
       loadBillings();
       this.context = getContext();
       return view;
    }

    private List<Billings> billings = new ArrayList<>();
    private BillingsListAdapter billingsListAdapter;

    private void showBillingsList(){
        billingsListAdapter = new BillingsListAdapter(
                getContext(),
                sortingBillings(billings));
        listItemBillings.setAdapter(billingsListAdapter);
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
                        showBillingsList();
                        handlerClickItemListBillings();

                        totalAmountFragment.setBillings(billings);
                        totalAmountFragment.onDataLoaded();
                    }

                    @Override
                    public void onBillingsDataNotFound() {
                        Log.d("error","Відсутні дані");;
                    }

                    @Override
                    public void onBillingsDataError(Exception e) {
                        Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
                    }
                }
        );

    }

    private List<Billings> sortingBillings(List<Billings> billingList){
        String[] typeBillings = new String[]{
                TypeBillings.ORDINARY.getTitle(),
                TypeBillings.DEBT.getTitle()
        };
        List<Billings> billings = new ArrayList<>();
        for(Billings bill : billingList){
            for(int i = 0; i < typeBillings.length; i++){
                if(bill.getTypeBillings().equals(typeBillings[i])){
                    billings.add(bill);
                }
            }
        }
        return billings;
    }

    private void handlerClickItemListBillings() {
        billingsListAdapter.setOnItemClickListener(billings -> {
            ModalBilling modalBilling = new ModalBilling(context,billings);

            BillingDialogCallback billingDialogCallback = new BillingDialogCallback() {
                @Override
                public void onSuccessDelete() {
                    ToastManager.showToastOnSuccessful(context,R.string.toast_successful_entered_the_data);
                    FragmentSwitcher.replaceFragment(
                            new BillingsFragment(),
                            getActivity().getSupportFragmentManager(),
                            R.id.containerHome
                    );
                    ToastManager.showToastOnSuccessful(context,R.string.toast_successful_delete_billings);
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
                }

                @Override
                public void onClickOperations() {

                }

                @Override
                public void onClickReplenishment() {
                    handlerBtnReplenishment(billings);
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
            modalBilling.modalStart(billingDialogCallback);
        });
    }

    public List<Billings> getBillings() {
        return  billings;
    }

    private TotalAmountFragment totalAmountFragment;

    public void setTotalAmountFragment(TotalAmountFragment totalAmountFragment) {
        this.totalAmountFragment = totalAmountFragment;
    }

    private void handlerBtnReplenishment(Billings bill){

    }
}