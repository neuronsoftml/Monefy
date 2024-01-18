package com.example.monefy.basic.functionality.fragment.dialogModal;

import com.example.monefy.basic.functionality.model.billings.Billings;

public interface CallbackForReplenishmentOfSelectedBillings {
    //theBillToWhichWeTransfer Рахунок на який переказуємо.
    //theBillFromWhichWeDebit Рахунок з якого списуєм.
    void selectBillings(Billings theBillToWhichWeTransfer, Billings theBillFromWhichWeDebit);
}
