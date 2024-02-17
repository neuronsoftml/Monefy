package com.example.monefy.basic.functionality.fragment.billings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.basic.functionality.Interface.billings.OnBillingsCallback;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.adapter.billings.BillingsListAdapter;
import com.example.monefy.basic.functionality.adapter.billings.HorizontalPageIndicator;
import com.example.monefy.basic.functionality.fragment.navigation.FragmentNavigation;
import com.example.monefy.basic.functionality.Interface.dialogModal.BillingDialogCallback;
import com.example.monefy.basic.functionality.Interface.dialogModal.DialogCallback;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalTransferFragment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelectReplenishment;
import com.example.monefy.basic.functionality.fragment.dialogModal.ModalSelectBilling;
import com.example.monefy.basic.functionality.fragment.history.HistoryBillingsFragment;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.controller.billings.BillingsController;
import com.example.monefy.basic.functionality.controller.message.ToastController;

import java.io.Serializable;
import java.util.List;

public class BillingsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView  tvMessage;
    private Context context;
    private InfoBoardBillingsFragment infoBoardBillingsFragment;
    private HorizontalPageIndicator horizontalBillingsIndicator;
    private BillingsFragment billingsFragment;

    private HistoryBillingsFragment historyBillingsFragment;

    /** Цей метод здійснює ініціалізацію UI елементів які знаходять в фрагменті
     * @param view віджети
     */
    private void setupUIElements(View view){
        this.tvMessage = view.findViewById(R.id.tVMessage);
        this.recyclerView = view.findViewById(R.id.listItemBillings);
        this.horizontalBillingsIndicator = view.findViewById(R.id.horizontalIndicator);
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
       loadListBillings();

       this.context = getContext();
       return view;
    }

    /**Адаптер для рахуків*/
    private BillingsListAdapter billingsListAdapter;

    /** Цей метод відображає адаптер рахунків*/
    private void showBillingsListAdapter(List<Billings> billingsList){
        if(billingsListAdapter == null){
            billingsListAdapter = new BillingsListAdapter(billingsList);
            recyclerView.setAdapter(billingsListAdapter);
        }else{
            billingsListAdapter.updateBillingsList(billingsList);
            billingsListAdapter.notifyDataSetChanged();
        }
    }

    /** Цей метод виконує обробку події кліку по вибраному рахунку*/
    private void handlerClickItemListBillings() {
        handlerBillingDialogCallback();

        billingsListAdapter.setOnItemClickListener(billings -> {
            ModalSelectBilling modalBilling = new ModalSelectBilling(
                    context,
                    (Billings) billings,
                    billingDialogCallback
            );
            modalBilling.modalStart();
        });
    }

    private BillingDialogCallback billingDialogCallback;

    /** Цей метод виконує обробку події кліку вибраної обпциї*/
    private void handlerBillingDialogCallback(){
        billingDialogCallback = new BillingDialogCallback() {
            @Override
            public void onSuccessDelete(Billings billing) {
                handlerDelete();
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
            public void onFailure(Exception exception) throws Exception {
                throw new Exception("Невідома помилка"+exception);
            }
        };
    }

    /** Цей метод виконує оброку видалити рахунок*/
    private void handlerDelete(){
        infoBoardBillingsFragment.mustBeUpdatedBillings();
        loadListBillings();
        ToastController.showToastOnSuccessful(getContext(),R.string.textSuccessfulDeleteBillings);
    }

    /** Цей метод виконує обробку редагування рахунку*/
    private void handlerEdit(Billings billings){
        Bundle bundle = new Bundle();
        bundle.putSerializable("billing", (Serializable) billings);

        if(getActivity() != null){
            FragmentNavigation.gotToReplaceEditBillingsFragment(getActivity().getSupportFragmentManager(), bundle);
        }
    }

    /** Цей метод виконує обробку вибору рахунку транзакції*/
    private void handlerSelectReplenishment(Billings bill){
        ModalSelectReplenishment modalSelectReplenishment = new ModalSelectReplenishment(
                getContext(),
                R.layout.modal_bottom_select_replenishment,
                bill,
                (theBillToWhichWeTransfer, theBillFromWhichWeDebit) -> {
                    ModalTransferFragment modalTransferFragment = new ModalTransferFragment(
                            theBillToWhichWeTransfer,
                            theBillFromWhichWeDebit
                    );
                    handlerReplenishment(modalTransferFragment);
                }
        );
        modalSelectReplenishment.modalStart();
    }

    /** Цей метод виконує обробку транзакції рахунку*/
    private void handlerReplenishment(ModalTransferFragment modalTransferFragment){
        modalTransferFragment.show(getChildFragmentManager(),"Modal");
        modalTransferFragment.startDialogModal(new DialogCallback() {
            @Override
            public void onSuccess(String data) {
                loadListBillings();
                modalTransferFragment.dismiss();
            }

            @Override
            public void onFailure(Exception exception) throws Exception {
                throw new Exception("Невідома помилка підчас виконання транзакції" + exception);
            }
        });
    }

    /** Цей метод виконує загрузку рахунків з Controllera*/
    private void loadListBillings(){
        BillingsController.getAllBillings(new OnBillingsCallback() {
            @Override
            public void onBillingsDataReceived(List<Billings> billingsList) {
                int sizeBillings =  billingsList.size();
                infoBoardBillingsFragment.onBillingsDataReceived(billingsList);
                billingsFragment.setDataCounterBillings(sizeBillings);
                showBillingsListAdapter(billingsList);
                handlerClickItemListBillings();
                showHorizontalBillingsIndicator(sizeBillings);
                recyclerViewScrollListener(billingsList);
            }

            @Override
            public void onDataNotFound() {
                tvMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    /** Цей метод виконує відображення нижнього індекатора кількості рахунків*/
    private void showHorizontalBillingsIndicator(int size){
        horizontalBillingsIndicator.setPageCount(size);
    }

    /** Цей метод моніторить скролінк  списка рахунків */
    private void recyclerViewScrollListener(List<Billings> billingsList){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Отримання LayoutManager
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                // Отримання індексу центрального елемента
                int centerIndex = layoutManager.findFirstCompletelyVisibleItemPosition() + (layoutManager.findLastCompletelyVisibleItemPosition() - layoutManager.findFirstCompletelyVisibleItemPosition()) / 2;

                // Оновлення індикатора
                horizontalBillingsIndicator.setCurrentPage(centerIndex);
                if(centerIndex != -1){
                    historyBillingsFragment.updateDataHistoryBillings(billingsList.get(centerIndex));
                }
            }
        });
    }

    public void setHistoryBillingsFragment(HistoryBillingsFragment historyBillingsFragment) {
        this.historyBillingsFragment = historyBillingsFragment;
    }

    public void setBillingsFragment(BillingsFragment billingsFragment) {
        this.billingsFragment = billingsFragment;
    }

    public void setInfoBoardFragment(InfoBoardBillingsFragment infoBoardBillingsFragment) {
        this.infoBoardBillingsFragment = infoBoardBillingsFragment;
    }

}
