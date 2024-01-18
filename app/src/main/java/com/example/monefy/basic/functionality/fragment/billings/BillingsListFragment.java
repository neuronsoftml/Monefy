package com.example.monefy.basic.functionality.fragment.billings;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.billings.BillingsListAdapter;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.dialogModal.BillingDialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.CallbackForReplenishmentOfSelectedBillings;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalReplenishment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelectReplenishment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelectBilling;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.Manager.billings.BillingsManager;
import com.example.monefy.Manager.message.ToastManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BillingsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView  tvMessage;
    private Context context;
    private final BillingsManager billingsManager = BillingsManager.getBillingsManager();
    private InfoBoardBillingsFragment infoBoardBillingsFragment;

    private void setupUIElements(View view){
        this.tvMessage = view.findViewById(R.id.tVMessage);
        this.recyclerView = view.findViewById(R.id.list_item_billings);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_billings_list, container, false);

       setupUIElements(view);

       billingsManager.loadBillings(() -> {
           billings.clear();
           billings = billingsManager.getBillingsList();
           infoBoardBillingsFragment.onDataLoaded();
           showBillingsList();
           handlerClickItemListBillings();

           if(billings.size() == 0){
               tvMessage.setVisibility(View.VISIBLE);
           }
       });


       this.context = getContext();
       return view;
    }

    private List<Billings> billings = new ArrayList<>();
    private BillingsListAdapter billingsListAdapter;

    private void showBillingsList(){
        billingsListAdapter = new BillingsListAdapter(billings);

        recyclerView.setAdapter(billingsListAdapter);
    }


    private void handlerClickItemListBillings() {
        billingsListAdapter.setOnItemClickListener(billings -> {
            ModalSelectBilling modalBilling = new ModalSelectBilling(
                    context,
                    (Billings) billings,
                    billingDialogCallback
            );

            handlerBillingDialogCallback();
            modalBilling.modalStart();
        });
    }

    private BillingDialogCallback billingDialogCallback;
    private void handlerBillingDialogCallback(){
        billingDialogCallback = new BillingDialogCallback() {
            @Override
            public void onSuccessDelete(Billings billing) {
                handlerDelete(billing);
            }

            @Override
            public void onClickEdit(Billings billing) {
                handlerEdit(billing);
            }

            @Override
            public void onClickOperations(Billings billing) {

            }

            @Override
            public void onClickReplenishment(Billings billing) {
                handlerSelectReplenishment(billing);
            }

            @Override
            public void onClickWriteOff(Billings billing) {

            }

            @Override
            public void onClickTransfer(Billings billing) {

            }

            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        };
    }

    private void handlerDelete(Billings billings){
        billingsListAdapter.removeBillings(billings);
        billingsListAdapter.notifyDataSetChanged();
        billingsManager.loadBillings(() -> infoBoardBillingsFragment.updateInfoBord(billingsManager.getBillingsList()));
        ToastManager.showToastOnSuccessful(getContext(),R.string.toast_successful_delete_billings);
    }

    private void handlerEdit(Billings billings){
        Bundle bundle = new Bundle();
        bundle.putSerializable("billing", (Serializable) billings);

        EditBillingsFragment editBillingsFragment = new EditBillingsFragment();
        editBillingsFragment.setArguments(bundle);

        FragmentSwitcher.replaceTransactionFragment(
                getActivity().getSupportFragmentManager(),
                editBillingsFragment,
                FragmentSwitcher.getContainerHome()
        );
    }

    private void handlerSelectReplenishment(Billings bill){
        ModalSelectReplenishment modalSelectReplenishment = new ModalSelectReplenishment(
                getContext(),
                R.layout.modal_bottom_select_replenishment,
                bill,
                (theBillToWhichWeTransfer, theBillFromWhichWeDebit) -> {
                    ModalReplenishment modalReplenishment  = new ModalReplenishment(
                            getContext(),
                            R.layout.modal_bottom_replenishment,
                            theBillToWhichWeTransfer,
                            theBillToWhichWeTransfer
                    );
                    handlerReplenishment(modalReplenishment);
                }
        );
        modalSelectReplenishment.modalStart();
    }

    private void handlerReplenishment(ModalReplenishment modalReplenishment){
        modalReplenishment.modalStart();
    }

    public void setInfoBoardFragment(InfoBoardBillingsFragment infoBoardBillingsFragment) {
        this.infoBoardBillingsFragment = infoBoardBillingsFragment;
    }
    public List<Billings> getBillings() {
        return  billings;
    }
}