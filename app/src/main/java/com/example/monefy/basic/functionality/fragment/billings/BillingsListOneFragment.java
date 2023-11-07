package com.example.monefy.basic.functionality.fragment.billings;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.BillingsListAdapter;
import com.example.monefy.basic.functionality.fragment.FragmentSwitcher;
import com.example.monefy.basic.functionality.fragment.dialogModal.BillingDialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalReplenishment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalBilling;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.Manager.BillingsManager;
import com.example.monefy.Manager.message.ToastManager;

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
    private BillingsManager billingsManager = BillingsManager.getBillingsManager();

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

       billingsManager.loadBillings(() -> {
           billings.clear();
           billings = billingsManager.getBillingsList();
           showBillingsList();
           handlerClickItemListBillings();
           totalAmountFragment.setBillings(billings);
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
                        TypeBillings.getListTypeBillingsOD())
        );

        listItemBillings.setAdapter(billingsListAdapter);
    }

    private void handlerClickItemListBillings() {
        billingsListAdapter.setOnItemClickListener(billings -> {
            ModalBilling modalBilling = new ModalBilling(context,billings);

            BillingDialogCallback billingDialogCallback = new BillingDialogCallback() {
                @Override
                public void onSuccessDelete() {
                   handlerDelete(billings);
                }

                @Override
                public void onClickEdit() {
                  handlerEdit(billings);
                }

                @Override
                public void onClickOperations() {

                }

                @Override
                public void onClickReplenishment() {
                    handlerReplenishment(billings);
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

    private void handlerDelete(Billings billings){
        billingsListAdapter.removeBillings(billings);
        billingsListAdapter.notifyDataSetChanged();
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
}