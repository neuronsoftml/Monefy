package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.model.billings.TypeDebtorSide;
import com.example.monefy.basic.functionality.model.income.TypeFrequency;
import com.example.monefy.basic.functionality.model.income.TypeIncomes;

import java.util.ArrayList;
import java.util.List;

public class ModalFunctionalSelect extends DialogModal implements TypeSelectModal {

    private String selectedData;
    private final List<Button> buttonListModal = new ArrayList<>();
    private final List<TypeSelectModal> typeItems;
    private final Dialog dialogModal;
    private final Context context;
    private final int idTitle;
    private TextView titleModalView;
    private final Object typeObject;
    private final DialogCallback dialogCallback;

    public <T extends Enum<T> & ModalTypeItem> ModalFunctionalSelect(Context context, int idTitle, List<TypeSelectModal> typeItems, Object typeObject, DialogCallback dialogCallback) {
            super(context,R.layout.model_bottom_type);
            this.context = context;
            this.idTitle = idTitle;
            this.typeItems = typeItems;
            this.dialogModal = getDialogModal();
            this.typeObject = typeObject;
            this.dialogCallback = dialogCallback;
        }


        @Override
        public void modalStart() {
            openModal();
            createButtonDialogModal();
            setupUIDialogModal();
            titleModalView.setText(idTitle);
            handlerButtonDialogModal();
        }


        @Override
        public void setupUIDialogModal() {
            titleModalView = dialogModal.findViewById(R.id.title_modal_view);
        }

        private void createButtonDialogModal() {
            LinearLayout elementType = dialogModal.findViewById(R.id.linearLayoutListButton);
                for (TypeSelectModal item : typeItems) {
                    if (TypeCurrency.class.equals(typeObject)) {
                        createButtonsForType(elementType, item.getTypeCurrency());
                    } else if (TypeDebtorSide.class.equals(typeObject)) {
                        createButtonsForType(elementType, item.getTypeDebtorSide());
                    } else if (TypeIncomes.class.equals(typeObject)) {
                        createButtonsForType(elementType, item.getTypeIncomes());
                    } else if (TypeFrequency.class.equals(typeObject)) {
                        createButtonsForType(elementType, item.getTypeFrequency());
                    }else if(TypeBillings.class.equals(typeObject)){
                        createButtonsForType(elementType, item.getTypeBillings());
                    }
                }
        }


        @Override
        public void handlerButtonDialogModal() {
            for (Button button : buttonListModal) {
                button.setOnClickListener(v -> {
                    selectedData = button.getText().toString();
                    if (dialogCallback != null) {
                        dialogCallback.onSuccess(selectedData);
                    }
                    dialogModal.cancel();
                });
            }
        }

        private void createButtonsForType(LinearLayout elementType, List<? extends ModalTypeItem> types) {
            for (ModalTypeItem type : types) {
                Button button = createButton(type);
                elementType.addView(button);
                buttonListModal.add(button);
            }
        }

        private Button createButton(ModalTypeItem type) {
            Button button = new Button(context);
            button.setText(type.getTitle());
            button.setTag(type.getIdentifier(type.getTitle()));
            button.setGravity(Gravity.CENTER_VERTICAL);
            button.setAllCaps(false);
            button.setBackgroundColor(Color.WHITE);
            return button;
        }
    }



