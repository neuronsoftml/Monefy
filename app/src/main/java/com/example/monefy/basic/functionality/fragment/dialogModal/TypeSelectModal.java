package com.example.monefy.basic.functionality.fragment.dialogModal;

import com.example.monefy.basic.functionality.model.currency.TypeCurrency;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.model.billings.TypeDebtorSide;
import com.example.monefy.basic.functionality.model.income.TypeFrequency;
import com.example.monefy.basic.functionality.model.income.TypeIncomes;

import java.util.Arrays;
import java.util.List;

public interface TypeSelectModal{
    default List<TypeDebtorSide> getTypeDebtorSide(){
        return Arrays.asList(TypeDebtorSide.values());
    }

    default List<TypeCurrency> getTypeCurrency(){
        return Arrays.asList(TypeCurrency.values());
    }

    default List<TypeFrequency> getTypeFrequency(){
        return Arrays.asList(TypeFrequency.values());
    }

    default List<TypeIncomes> getTypeIncomes(){
        return Arrays.asList(TypeIncomes.values());
    }

    default List<TypeBillings> getTypeBillings(){return Arrays.asList(TypeBillings.values());}
}
