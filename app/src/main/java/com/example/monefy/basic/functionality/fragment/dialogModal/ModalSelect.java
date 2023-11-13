package com.example.monefy.basic.functionality.fragment.dialogModal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monefy.R;

import java.util.ArrayList;
import java.util.List;

public class ModalSelect extends DialogMenu {

    private String updateData;
    private LinearLayout elementType;
    private List<Button> buttonListModal = new ArrayList<>();
    private List<ModalTypeItem> typeItems = new ArrayList<>();
    private Dialog dialogModal;
    private Context context;
    private int idTitle;
    private TextView titleModalView;

    public ModalSelect(Context context, int idTitle, List<ModalTypeItem> typeItems) {
            super(context,R.layout.model_bottom_type);
            this.context = context;
            this.idTitle = idTitle;
            this.typeItems = typeItems;
            this.dialogModal = getDialogModal();
        }

        private DialogCallback dialogCallback;

        @Override
        public void modalStart(DialogCallback dialogCallback) {
            this.dialogCallback = dialogCallback;

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
            elementType = dialogModal.findViewById(R.id.linearLayoutListButton);
            for (ModalTypeItem item : typeItems) {
                Button button = new Button(context);
                button.setText(item.getTitle());
                button.setTag(item.getIdentifier(item.getTitle()));
                button.setGravity(Gravity.CENTER_VERTICAL);
                button.setAllCaps(false);
                button.setBackgroundColor(Color.WHITE);
                elementType.addView(button);
                buttonListModal.add(button);
            }
        }

        @Override
        public void handlerButtonDialogModal() {
            for (Button button : buttonListModal) {
                button.setOnClickListener(v -> {
                    updateData = button.getText().toString();
                    if (dialogCallback != null) {
                        dialogCallback.onSuccess();
                    }
                    dialogModal.cancel();
                });
            }
        }

        public String getUpdateData() {
            return updateData;
        }
    }

