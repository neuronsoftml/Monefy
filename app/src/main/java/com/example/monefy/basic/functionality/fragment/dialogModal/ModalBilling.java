package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.billings.Billings;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.firebase.InConclusionCompleteListener;

public class ModalBilling implements DialogModal{
    private Dialog dialogModal;
    private Context context;
    private Billings billing;
    private ImageView imageViewBillingCard;
    private TextView tVNameBilling, tVABillingBalance, tVTypeCurrency, tVTypeBilling;
    private ImageButton imageBtnDelete, imageBtnEdit, imageBtnReplenishment;

    public ModalBilling(Context context, Billings billing){
        this.context = context;
        this.billing = billing;
    }

    private DialogCallback callback;

    @Override
    public void modalStart(DialogCallback dialogCallback) {
        this.callback = dialogCallback;
        showDialogModal();
        setupUIDialogModal();
        setValueObjectModal();
        handlerButtonDialogModal();
    }

    @Override
    public void showDialogModal() {
        dialogModal = new Dialog(context);
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(R.layout.modal_bottom_billing);
        dialogModal.show();
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void setupUIDialogModal() {
        imageViewBillingCard = dialogModal.findViewById(R.id.imageView_modal_account_card);
        tVNameBilling = dialogModal.findViewById(R.id.textView_modal_name_account);
        tVABillingBalance = dialogModal.findViewById(R.id.textView_modal_balance_account);
        tVTypeCurrency = dialogModal.findViewById(R.id.textView_modal_type_currency);
        tVTypeBilling = dialogModal.findViewById(R.id.textView_type_account);

        imageBtnDelete = dialogModal.findViewById(R.id.imageBtn_modal_delete_billing);
        imageBtnEdit = dialogModal.findViewById(R.id.imageBtn_modal_edit_billing);
        imageBtnReplenishment = dialogModal.findViewById(R.id.imageBtn_modal_replenish_billing);
    }

    @Override
    public void handlerButtonDialogModal() {
        handlerButtonDelete();
        handlerButtonEdit();
        handlerButtonReplenishment();
    }

    private void setValueObjectModal() {
        imageViewBillingCard.setImageResource(TypeBillings.getIdImageTypeBillings(billing.getTypeBillings()));
        tVNameBilling.setText(billing.getName());
        tVABillingBalance.setText(String.valueOf(billing.getBalance()));
        tVTypeCurrency.setText(billing.getTypeCurrency());
        tVTypeBilling.setText(billing.getTypeBillings());
    }

    private void handlerButtonDelete(){
            imageBtnDelete.setOnClickListener(v->{
            FirebaseManager.deleteBillings(
                    billing.getId(),
                    new InConclusionCompleteListener() {
                        @Override
                        public void onSuccess() {
                            if(callback != null){
                                dialogModal.cancel();
                                if(callback instanceof BillingDialogCallback){
                                    BillingDialogCallback billingDialogCallback = (BillingDialogCallback) callback;
                                    billingDialogCallback.onSuccessDelete();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            if(callback != null){
                                callback.onFailure(exception);
                            }
                        }
                    }
            );
        });
    }

    private void handlerButtonEdit(){
        imageBtnEdit.setOnClickListener(v->{
            dialogModal.cancel();
            if(callback instanceof BillingDialogCallback){
                BillingDialogCallback billingDialogCallback = (BillingDialogCallback) callback;
                billingDialogCallback.onClickEdit();
            }
        });
    }

    private void handlerButtonReplenishment(){
        imageBtnReplenishment.setOnClickListener(v->{
            dialogModal.cancel();
            if(callback instanceof  BillingDialogCallback){
                BillingDialogCallback billingDialogCallback = (BillingDialogCallback) callback;
                billingDialogCallback.onClickReplenishment();
             }
        });
    }
    
}
