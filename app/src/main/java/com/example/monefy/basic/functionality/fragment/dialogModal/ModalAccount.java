package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.fragment.CreateBillingsFragment;
import com.example.monefy.basic.functionality.model.Billings;
import com.example.monefy.basic.functionality.model.TypeBillings;
import com.example.monefy.tools.firebase.AuthenticationManager;
import com.example.monefy.tools.firebase.FirebaseManager;
import com.example.monefy.tools.firebase.InConclusionCompleteListener;
import com.example.monefy.tools.message.ToastManager;

public class ModalAccount implements DialogModal{
    private Dialog dialogModal;
    private Context context;
    private Billings billing;
    private ImageView imageViewAccountCard;
    private TextView textViewNameAccount, textViewAccountBalance, textViewTypeCurrency, textViewTypeAccount;
    private ImageButton imageButtonModalDelete, imageButtonModalEdit;

    public ModalAccount(Context context, Billings billing){
        this.context = context;
        this.billing = billing;
    }

    @Override
    public void modalStart(DialogCallback dialogCallback) {
        showDialogModal();
        setupUIDialogModal();
        setValueObjectModal();
        handlerButtonDialogModal(dialogCallback);
    }

    @Override
    public void showDialogModal() {
        dialogModal = new Dialog(context);
        dialogModal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setContentView(R.layout.modal_bottom_account);
        dialogModal.show();
        dialogModal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogModal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogModal.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void setupUIDialogModal() {
        imageViewAccountCard = dialogModal.findViewById(R.id.imageView_modal_account_card);
        textViewNameAccount = dialogModal.findViewById(R.id.textView_modal_name_account);
        textViewAccountBalance = dialogModal.findViewById(R.id.textView_modal_balance_account);
        textViewTypeCurrency = dialogModal.findViewById(R.id.textView_modal_type_currency);
        textViewTypeAccount = dialogModal.findViewById(R.id.textView_type_account);

        imageButtonModalDelete = dialogModal.findViewById(R.id.imageButton_modal_delete_account);
        imageButtonModalEdit = dialogModal.findViewById(R.id.imageButton_modal_edit_account);
    }

    @Override
    public void handlerButtonDialogModal(DialogCallback dialogCallback) {
        handlerButtonDelete(dialogCallback);
        handlerButtonEdit(dialogCallback);
    }

    private void setValueObjectModal() {
        imageViewAccountCard.setImageResource(TypeBillings.getIdImageTypeBillings(billing.getTypeBillings()));
        textViewNameAccount.setText(billing.getName());
        textViewAccountBalance.setText(String.valueOf(billing.getBalance()));
        textViewTypeCurrency.setText(billing.getTypeCurrency());
        textViewTypeAccount.setText(billing.getTypeBillings());
    }

    private void handlerButtonDelete(DialogCallback dialogCallback){
            imageButtonModalDelete.setOnClickListener(v->{
            FirebaseManager.deleteBillings(
                    AuthenticationManager.getAuthenticationManager().getUserId(),
                    billing.getId(),
                    new InConclusionCompleteListener() {
                        @Override
                        public void onSuccess() {
                            if(dialogCallback != null){
                                dialogModal.cancel();
                                if(dialogCallback instanceof AccountDialogCallback){
                                    AccountDialogCallback accountDialogCallback = (AccountDialogCallback) dialogCallback;
                                    accountDialogCallback.onSuccessDelete();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            if(dialogCallback != null){
                                dialogCallback.onFailure(exception);
                            }
                        }
                    }
            );
        });
    }

    private void handlerButtonEdit(DialogCallback dialogCallback){
        imageButtonModalEdit.setOnClickListener(v->{
            dialogModal.cancel();
            if(dialogCallback instanceof AccountDialogCallback){
                AccountDialogCallback accountDialogCallback = (AccountDialogCallback) dialogCallback;
                accountDialogCallback.onClickEdit();
            }
        });
    }

}
