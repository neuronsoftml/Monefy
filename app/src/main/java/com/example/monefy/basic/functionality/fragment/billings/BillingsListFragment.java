package com.example.monefy.basic.functionality.fragment.billings;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.billings.BillingsListAdapter;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.dialogModal.BillingDialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalReplenishment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBilling;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.Manager.billings.BillingsManager;
import com.example.monefy.Manager.message.ToastManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillingsListFragment extends Fragment {

    private ListView listItemBillings;
    private TextView  tvMessage;
    private Context context;
    private BillingsManager billingsManager = BillingsManager.getBillingsManager();
    private InfoBoardFragment infoBoardFragment;

    private void setupUIElements(View view){
        this.tvMessage = view.findViewById(R.id.tVMessage);
        this.listItemBillings = view.findViewById(R.id.list_item_billings);
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
           infoBoardFragment.onDataLoaded();
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
        billingsListAdapter = new BillingsListAdapter(
                getContext(),
                BillingsManager.sortingBillings(
                        billings,
                        Arrays.asList(TypeBillings.values()))
        );

        listItemBillings.setAdapter(billingsListAdapter);
    }

    private void handlerClickItemListBillings() {
        billingsListAdapter.setOnItemClickListener(billings -> {
            ModalBilling modalBilling = new ModalBilling(context, (Billings) billings);

            BillingDialogCallback billingDialogCallback = new BillingDialogCallback() {
                @Override
                public void onSuccessDelete() {
                   handlerDelete((Billings) billings);
                }

                @Override
                public void onClickEdit() {
                  handlerEdit((Billings) billings);
                }

                @Override
                public void onClickOperations() {

                }

                @Override
                public void onClickReplenishment() {
                    handlerReplenishment((Billings) billings);
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

    private void handlerDelete(Billings billings){
        billingsListAdapter.removeBillings(billings);
        billingsListAdapter.notifyDataSetChanged();
        billingsManager.loadBillings(() -> infoBoardFragment.updateInfoBord(billingsManager.getBillingsList()));
        ToastManager.showToastOnSuccessful(getContext(),R.string.toast_successful_delete_billings);
    }

    private void handlerEdit(Billings billings){
        Bundle bundle = new Bundle();
        bundle.putSerializable("billing", billings);
        FragmentSwitcher.replaceFragmentToDate(
                new EditBillingsFragment(),
                bundle,
                getContext(),
                FragmentSwitcher.getContainerHome()
        );
    }

    private void handlerReplenishment(Billings bill){
        ModalReplenishment modalReplenishment = new ModalReplenishment(
                getContext(),
                R.layout.modal_bottom_replenishment,
                bill);
        modalReplenishment.modalStart(new DialogCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

    public void setInfoBoardFragment(InfoBoardFragment infoBoardFragment) {
        this.infoBoardFragment = infoBoardFragment;
    }
}